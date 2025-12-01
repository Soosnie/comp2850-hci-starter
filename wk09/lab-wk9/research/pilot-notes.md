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



# First pilot;

6.04, 4
7.12, 5
6.85, 4
1.04, 5

Debrief:
- "liked that when task was added it showed up straight away"
1- Edit
2- adding task
3- adding task

# Second Pilot;

7.90, 4
6.88, 4 
11.46, 5
3.84, 5

Debrief:
-"Easy to delete"
-"Liked being able to tab through everything"

1- Edit
2- No
3- No

# Third Pilot;

6.78, 5
did not work
7.34, 5
1.23, 5

# Fourth Pilot;

6.54, 5
8.23, 4
6.87, 5
2.54, 5


Completion rate:
    - T1: 100%
    - T2: 75%
    - T3: 100%
    - T4: 100%

Median Times:
    - T1: [15,9,17,10] ms   [6.04,7.90,6.78,6.54] s
    - T2: [12,12,?,15] ms   [7.12,6.88,?,8.23] s
    - T3: [20,18,3,18] ms   [6.85,11.46,7.34,6.87] s
    - T4: [3,4,15,3] ms    [1.04,3.84,1.23,2.54] s

Error Rate:
    -0%

# Pilot Summary Stats (n=3)

## Completion Rates
| Task | Completion | Notes |
|------|-----------|-------|
| T1 (Filter) | 4/4 (100%) | All participants successful |
| T2 (Edit) | 3/4 (75%) | The edit button did not work for P3 |
| T3 (Add) | 4/4 (100%) | All participants successful |
| T4 (Delete) | 4/4 (100%) | No issues |

## Median Times (success only)
| Task | Median (ms) | Median (s) | Range |
|------|------------|------------|-------|
| T1 | 12.5 | 6.66 | 6.04 – 7.90  |
| T2 | 12   | 7.12 | 6.88 – 8.23  |
| T3 | 10.5 | 7.11 | 6.85 – 11.46 |
| T4 | 3.5  | 1.89 | 1.04 – 3.84  |



