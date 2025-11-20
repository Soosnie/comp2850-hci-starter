# axe DevTools Audit Report — Week 7

**Date**: [YYYY-MM-DD]
**Page scanned**: http://localhost:8080/tasks
**axe version**: [e.g., 4.8.2]

---

## Summary

- **Violations**: 2
- **Needs review**: 2
- **Passed**: [e.g., 43]

---

## Violations Found

### 1. Color Contrast (Serious)
**WCAG**: 1.4.3 Contrast (Minimum) - Level AA
**Issue**: Button text (#6c757d) on white background = 4.2:1 (needs 4.5:1)
**Element**: `<button>Delete</button>` in task list
**Impact**: Low vision, colour-blind people struggle to read
**Fix**: Change button colour to #5a6268 (meets 4.53:1)

---

### 2. Missing Form Labels (Critical)
**WCAG**: 3.3.2 Labels or Instructions - Level A
**Issue**: Input field has no associated `<label>`
**Element**: `<input id="title" name="title">`
**Impact**: People using screen readers don't know what the field is for
**Fix**: Add `<label for="title">Task title</label>`

---

[Add all violations found - typically 5-8]

---

## Needs Review (Manual Check Required)

### 1. Link Purpose (Best Practice)
**WCAG**: 2.4.4 Link Purpose (In Context) - Level A
**Issue**: Link text is "here" (not descriptive)
**Element**: `<a href="/docs">Click here</a>`
**Manual check**: Review if context makes purpose clear

---

## Passed Checks (Sample)

- ✅ HTML lang attribute
- ✅ Unique IDs
- ✅ Valid ARIA attributes
- ✅ Landmark roles
- ✅ Skip link present

---

## Priority Fixes (Week 7)

Based on severity + inclusion risk:
1. **Missing form labels** (Critical, WCAG A) - FIX NOW
2. **Color contrast** (Serious, WCAG AA) - FIX NOW
3. **Link purpose** (Best practice) - Defer to Week 10
