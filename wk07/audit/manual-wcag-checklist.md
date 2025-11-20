# Manual WCAG 2.2 AA Checklist — Week 7

**Date**: [2025-11-20]
**Tester**: [Ash Smith]
**Assistive tech**: [e.g., NVDA 2024.1, Chrome 120]

---

## Principle 1: Perceivable

### 1.4.3 Contrast (Minimum) - Level AA
- [ ] **Text**: All text 4.5:1 contrast minimum
- [ ] **Large text**: 18pt+ or 14pt bold - 3:1 minimum
- [ ] **UI components**: Buttons, inputs, borders - 3:1 minimum

**Test method**: Use Chrome DevTools Color Picker or [WebAIM Contrast Checker](https://webaim.org/resources/contrastchecker/)

**Findings**: [List any contrast failures with ratios]

---

### 1.4.10 Reflow - Level AA
- [ ] **Zoom to 400%**: Content reflows without horizontal scroll
- [ ] **Viewport 320px wide**: No loss of content or functionality

**Test method**: Chrome DevTools → Responsive mode → 320×568 → Zoom to 400%

**Findings**: [Any overflow or broken layouts?]

---

## Principle 2: Operable

### 2.1.1 Keyboard - Level A
- [ ] **All interactive elements** reachable via Tab
- [ ] **Focus order** matches visual order
- [ ] **No keyboard traps** (can Tab out of all elements)

**Test method**: Unplug mouse, use Tab/Shift+Tab/Enter/Space only

**Findings**: [List any unreachable elements]

---

### 2.4.1 Bypass Blocks - Level A
- [ ] **Skip link** present and functional
- [ ] **Landmarks** (nav, main, aside) for screen reader navigation

**Test method**: Press Tab once → "Skip to main content" appears → Enter → focus jumps

**Findings**: [Skip link working? Landmarks present?]

---

### 2.4.3 Focus Order - Level A
- [ ] **Logical sequence**: Tab follows reading order
- [ ] **No unexpected jumps**: Focus doesn't skip sections

**Test method**: Tab through entire page, note order

**Findings**: [Any illogical jumps?]

---

### 2.4.7 Focus Visible - Level AA
- [ ] **All focusable elements** show clear visual indicator
- [ ] **Indicator visible** against all backgrounds

**Test method**: Tab through page, confirm blue outline visible

**Findings**: [Any invisible focus states?]

---

## Principle 3: Understandable

### 3.3.1 Error Identification - Level A
- [ ] **Error messages** identify what's wrong in text
- [ ] **Errors associated** with fields (`aria-describedby`, `aria-invalid`)

**Test method**: Submit empty form, check error display

**Findings**: [Errors clearly identified?]

---

### 3.3.2 Labels or Instructions - Level A
- [ ] **All inputs** have `<label>` or `aria-label`
- [ ] **Labels descriptive** ("Task title" not "Title")

**Test method**: Check all `<input>`, `<select>`, `<textarea>` have labels

**Findings**: [Any missing labels?]

---

## Principle 4: Robust

### 4.1.3 Status Messages - Level AA
- [ ] **ARIA live regions** for dynamic updates
- [ ] **Success/error messages** announced by screen reader

**Test method**: NVDA on → add task → confirm "Task added" announced

**Findings**: [Status messages announced?]

---

## Summary

**Total checks**: 10
**Passed**: [e.g., 7]
**Failed**: [e.g., 3]

**Critical failures** (Level A):
- [List Level A failures - these MUST be fixed]

**Important failures** (Level AA):
- [List Level AA failures - should be fixed]
