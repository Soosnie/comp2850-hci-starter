time | task | observation | issue_tag | severity










example:
Session: P1 (sid=7a9f2c)
Date: 2025-10-15
Variant: Keyboard-only, JS-on

| Time | Task | Observation | Tag |
|------|------|-------------|-----|
| 14:23 | T3 | Participant hesitated before submitting—unsure if 'Enter' or button | ux-feedback |
| 14:25 | T3 | Success message not noticed initially | a11y-status |
| 14:26 | T1 | Typed 'report' slowly, watching for instant results | ux-expectation |
| 14:27 | T1 | Screen reader announced "Showing 3 tasks" ✓ | a11y-pass |
| 14:29 | T2 | Clicked Edit, validation error triggered (blank submission) | error-handling |
| 14:30 | T2 | Recovered from error, completed successfully | resilience |

Debrief notes:
- "I liked that the filter worked without clicking a button"
- "I wasn't sure the edit saved—maybe make the message more obvious?"
- SR announced status messages correctly throughout



First pilot;

6.04, 4
7.12, 5
6.85, 4
1.04, 5

Debrief:
- "liked that when task was added it showed up straight away"
1- Edit
2- adding task
3- adding task

Second Pilot;

7.90, 4
36.88, 4 #Note - participant could not spell supplier
11.46, 5
3.84, 5

Debrief:
-"Easy to delete"
-"Liked being able to tab through everything"

1- Edit
2- No
3- No

Third Pilot;

6.78, 5
did not work
7.34, 5
1.23, 5


Completion rate:
    - T1: 100%
    - T2: 66.66%
    - T3: 100%
    - T4: 100%

Median Times:
    - T1: [15,9,17] ms   [6.04,7.90,6.78] s
    - T2: [12,12,?] ms   [7.12,36.88,?] s
    - T3: [20,18,3] ms   [6.85,11.46,7.34] s
    - T4: [3,4,15] ms    [1.04,3.84,1.23] s

Error Rate:
    -0%

# Pilot Summary Stats (n=3)

## Completion Rates
| Task | Completion | Notes |
|------|-----------|-------|
| T1 (Filter) | 3/3 (100%) | All participants successful |
| T2 (Edit) | 2/3 (66.66%) | The edit button did not work for P3 |
| T3 (Add) | 3/3 (100%) | All participants successful |
| T4 (Delete) | 3/3 (100%) | No issues |

## Median Times (success only)
| Task | Median (ms) | Median (s) | Range |
|------|------------|------------|-------|
| T1 | 15 | 6.78s | 6.04s–7.90s |
| T2 | 12 | 20s | 7.12s–36.88s |
| T3 | 18 | 14s | 6.85s–11.46s |
| T4 | 4 | 8s | 1.04s–3.84s |


## need to sort this out *******************************
## JS-On vs JS-Off (T3 comparison)
- JS-on (n=4): Median 567ms
- JS-off (n=1, P3): 3456ms (full page reload)
- **Difference**: ~6× slower without JS (expected)

