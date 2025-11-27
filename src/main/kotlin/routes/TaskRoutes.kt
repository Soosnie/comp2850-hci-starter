package routes

import data.TaskRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.pebbletemplates.pebble.PebbleEngine
import java.io.StringWriter

/**
 * NOTE FOR NON-INTELLIJ IDEs (VSCode, Eclipse, etc.):
 * IntelliJ IDEA automatically adds imports as you type. If using a different IDE,
 * you may need to manually add imports. The commented imports below show what you'll need
 * for future weeks. Uncomment them as needed when following the lab instructions.
 *
 * When using IntelliJ: You can ignore the commented imports below - your IDE will handle them.
 */

// Week 7+ imports (inline edit, toggle completion):
//import model.Task               // When Task becomes separate model class
//import model.ValidationResult   // For validation errors
import renderTemplate            // Extension function from Main.kt
import isHtmxRequest             // Extension function from Main.kt

// Week 8+ imports (pagination, search, URL encoding):
import io.ktor.http.encodeURLParameter  // For query parameter encoding
import data.Page                       // Pagination helper class

// Week 9+ imports (metrics logging, instrumentation):
import utils.*                        // Logging/timing helpers (Logger, timed, jsMode, newReqId, RequestIdKey)

// Note: Solution repo uses storage.TaskStore instead of data.TaskRepository
// You may refactor to this in Week 10 for production readiness

/**
 * Week 6 Lab 1: Simple task routes with HTMX progressive enhancement.
 *
 * **Teaching approach**: Start simple, evolve incrementally
 * - Week 6: Basic CRUD with Int IDs
 * - Week 7: Add toggle, inline edit
 * - Week 8: Add pagination, search
 */

fun Route.taskRoutes() {
    val pebble =
        PebbleEngine
            .Builder()
            .loader(
                io.pebbletemplates.pebble.loader.ClasspathLoader().apply {
                    prefix = "templates/"
                },
            ).build()

    /**
     * Helper: Check if request is from HTMX
     */
    fun ApplicationCall.isHtmx(): Boolean = request.headers["HX-Request"]?.equals("true", ignoreCase = true) == true

    /**
     * GET /tasks - List all tasks
     * Returns full page (no HTMX differentiation in Week 6)
     */
    // Fragment endpoint for HTMX updates
    // Fragment endpoint for HTMX updates
    // Fragment endpoint for HTMX updates
    get("/tasks/fragment") {
        val q = call.request.queryParameters["q"]?.trim().orEmpty()
        val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1

        // Instrument filter fragments as T1_filter when a query is present
        if (q.isNotBlank()) {
            val reqId = newReqId()
            call.attributes.put(RequestIdKey, reqId)
            val session = call.request.cookies["sid"] ?: "anon"
            val js = call.jsMode()

            call.timed(taskCode = "T1_filter", jsMode = js) {
                val pageData = TaskRepository.search(q, page, 10)

                val list = renderTemplate("tasks/_list.peb", mapOf("page" to pageData, "q" to q))
                val pager = renderTemplate("tasks/_pager.peb", mapOf("page" to pageData, "q" to q))
                val status = """<div id=\"status\" hx-swap-oob=\"true\">Updated: showing ${pageData.items.size} of ${pageData.total} tasks</div>"""

                respondText(list + pager + status, ContentType.Text.Html)
            }
        } else {
            val pageData = TaskRepository.search(q, page, 10)

            val list = call.renderTemplate("tasks/_list.peb", mapOf("page" to pageData, "q" to q))
            val pager = call.renderTemplate("tasks/_pager.peb", mapOf("page" to pageData, "q" to q))
            val status = """<div id=\"status\" hx-swap-oob=\"true\">Updated: showing ${pageData.items.size} of ${pageData.total} tasks</div>"""

            call.respondText(list + pager + status, ContentType.Text.Html)
        }
    }

    // Update existing GET /tasks to use pagination
    get("/tasks") {
        val q = call.request.queryParameters["q"]?.trim().orEmpty()
        val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1

        // If a filter q is present, instrument this request as T1_filter
        if (q.isNotBlank()) {
            val reqId = newReqId()
            call.attributes.put(RequestIdKey, reqId)
            val session = call.request.cookies["sid"] ?: "anon"
            val js = call.jsMode()

            call.timed(taskCode = "T1_filter", jsMode = js) {
                val pageData = TaskRepository.search(q, page, 10)
                val html = renderTemplate("tasks/index.peb", mapOf(
                    "page" to pageData,
                    "q" to q,
                    "title" to "Tasks"
                ))
                respondText(html, ContentType.Text.Html)
            }
        } else {
            val pageData = TaskRepository.search(q, page, 10)
            val html = call.renderTemplate("tasks/index.peb", mapOf(
                "page" to pageData,
                "q" to q,
                "title" to "Tasks"
            ))
            call.respondText(html, ContentType.Text.Html)
        }
    }






    /**
     * POST /tasks - Add new task
     * Dual-mode: HTMX fragment or PRG redirect
     */
    post("/tasks") {
        // Instrumented add-task handler: assign a request id, log timings and validation failures
        val reqId = newReqId()
        call.attributes.put(RequestIdKey, reqId)

        val session = call.request.cookies["sid"] ?: "anon"
        val jsMode = call.jsMode()

        call.timed(taskCode = "T3_add", jsMode = jsMode) {
            val title = receiveParameters()["title"].orEmpty().trim()

            // Validation
            if (title.isBlank()) {
                Logger.validationError(session, reqId, "T3_add", "blank_title", jsMode)
                if (isHtmx()) {
                    val status = """<div id=\"status\" hx-swap-oob=\"true\">Title is required.</div>"""
                    return@timed respondText(status, ContentType.Text.Html, HttpStatusCode.BadRequest)
                } else {
                    return@timed respondRedirect("/tasks?error=title")
                }
            }

            if (title.length > 200) {
                Logger.validationError(session, reqId, "T3_add", "max_length", jsMode)
                if (isHtmx()) {
                    val status = """<div id=\"status\" hx-swap-oob=\"true\">Title too long (max 200 chars).</div>"""
                    return@timed respondText(status, ContentType.Text.Html, HttpStatusCode.BadRequest)
                } else {
                    return@timed respondRedirect("/tasks?error=title&msg=too_long")
                }
            }

            // Success path: add task and return refreshed fragments for HTMX, PRG for no-JS
            val task = TaskRepository.add(title)

            if (isHtmx()) {
                val q = "" // preserve filter if you want: request.queryParameters["q"]?.trim().orEmpty()
                val page = 1
                val pageSize = request.queryParameters["pageSize"]?.toIntOrNull()?.coerceIn(1, 100) ?: 10
                val pageData = TaskRepository.search(q, page, pageSize)

                val list = renderTemplate("tasks/_list.peb", mapOf("page" to pageData, "q" to q))
                val pager = renderTemplate("tasks/_pager.peb", mapOf("page" to pageData, "q" to q))
                val status = """<div id=\"status\" hx-swap-oob=\"true\">Added \"${task.title}\".</div>"""

                return@timed respondText(list + pager + status, ContentType.Text.Html, HttpStatusCode.Created)
            }

            // No-JS: PRG
            respondRedirect("/tasks")
        }
    }

    /**
     * POST /tasks/{id}/delete - Delete task
     * Dual-mode: HTMX empty response or PRG redirect
     */
    post("/tasks/{id}/delete") {
        // Instrument delete as T4_delete
        val reqId = newReqId()
        call.attributes.put(RequestIdKey, reqId)
        val session = call.request.cookies["sid"] ?: "anon"
        val js = call.jsMode()

        call.timed(taskCode = "T4_delete", jsMode = js) {
            val id = call.parameters["id"]?.toIntOrNull()
            val removed = id?.let { TaskRepository.delete(it) } ?: false

            if (isHtmx()) {
                val message = if (removed) "Task deleted." else "Could not delete task."
                if (!removed) {
                    Logger.validationError(session, reqId, "T4_delete", "not_found", js)
                }
                val status = """<div id=\"status\" hx-swap-oob=\"true\">$message</div>"""
                return@timed respondText(status, ContentType.Text.Html)
            }

            // No-JS: PRG
            respondRedirect("/tasks")
        }
    }

    // TODO: Week 7 Lab 1 Activity 2 Steps 2-5
    // Add inline edit routes here
    // Follow instructions in mdbook to implement:
    // - GET /tasks/{id}/edit - Show edit form (dual-mode)
    // - POST /tasks/{id}/edit - Save edits with validation (dual-mode)
    // - GET /tasks/{id}/view - Cancel edit (HTMX only)

    get("/tasks/{id}/edit") {
        val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.NotFound)
        val task = TaskRepository.find(id) ?: return@get call.respond(HttpStatusCode.NotFound)
        val errorParam = call.request.queryParameters["error"]

        val errorMessage = when (errorParam) {
            "blank" -> "Title is required. Please enter at least one character."
            else -> null
        }

        if (call.isHtmx()) {
            val template = pebble.getTemplate("tasks/_edit.peb")
            val model = mapOf("task" to task, "error" to errorMessage)
            val writer = StringWriter()
            template.evaluate(writer, model)
            call.respondText(writer.toString(), ContentType.Text.Html)
        } else {
            val model = mapOf(
                "title" to "Tasks",
                "tasks" to TaskRepository.all(),
                "editingId" to id,
                "errorMessage" to errorMessage
            )
            val template = pebble.getTemplate("tasks/index.peb")
            val writer = StringWriter()
            template.evaluate(writer, model)
            call.respondText(writer.toString(), ContentType.Text.Html)
        }
    }

    post("/tasks/{id}/edit") {
        // Instrument inline edit as T2_edit
        val reqId = newReqId()
        call.attributes.put(RequestIdKey, reqId)
        val session = call.request.cookies["sid"] ?: "anon"
        val js = call.jsMode()

        call.timed(taskCode = "T2_edit", jsMode = js) {
            val id = parameters["id"]?.toIntOrNull() ?: return@timed respond(HttpStatusCode.NotFound)
            val task = TaskRepository.find(id) ?: return@timed respond(HttpStatusCode.NotFound)

            val newTitle = receiveParameters()["title"].orEmpty().trim()

            // Validation
            if (newTitle.isBlank()) {
                Logger.validationError(session, reqId, "T2_edit", "blank_title", js)
                if (isHtmx()) {
                    // HTMX path: return edit fragment with error
                    val template = pebble.getTemplate("tasks/_edit.peb")
                    val model = mapOf(
                        "task" to task,
                        "error" to "Title is required. Please enter at least one character."
                    )
                    val writer = StringWriter()
                    template.evaluate(writer, model)
                    return@timed respondText(writer.toString(), ContentType.Text.Html, HttpStatusCode.BadRequest)
                } else {
                    // No-JS path: redirect with error flag
                    return@timed respondRedirect("/tasks/${id}/edit?error=blank")
                }
            }

            // Update task
            task.title = newTitle
            TaskRepository.update(task)

            if (isHtmx()) {
                // HTMX path: return view fragment + OOB status
                val viewTemplate = pebble.getTemplate("tasks/_item.peb")
                val viewWriter = StringWriter()
                viewTemplate.evaluate(viewWriter, mapOf("task" to task))

                val status = """<div id=\"status\" hx-swap-oob=\"true\">Task "${task.title}" updated successfully.</div>"""

                return@timed respondText(viewWriter.toString() + status, ContentType.Text.Html)
            }

            // No-JS path: PRG redirect
            respondRedirect("/tasks")
        }
    }


    get("/tasks/{id}/view") {
        val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.NotFound)
        val task = TaskRepository.find(id) ?: return@get call.respond(HttpStatusCode.NotFound)

        // HTMX path only (cancel is just a link to /tasks in no-JS)
        val template = pebble.getTemplate("tasks/_item.peb")
        val model = mapOf("task" to task)
        val writer = StringWriter()
        template.evaluate(writer, model)
        call.respondText(writer.toString(), ContentType.Text.Html)
    }



}

