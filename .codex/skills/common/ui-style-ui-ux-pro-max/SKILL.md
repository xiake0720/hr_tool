---
name: common-ui-style-ui-ux-pro-max
description: "UI-UX-Pro-Max style rules for the Vue admin: clean enterprise admin, consistent spacing/typography, component states, empty/loading/error patterns, and minimal visual noise."
metadata:
  short-description: "Design language constraints"
  tags: [common, ui, ux, design-system]
---

# Purpose
Ensure consistent, high-quality UI output aligned with ui-ux-pro-max.

# Relationship
- For full UI/UX guidance and search workflows, use the `ui-ux-pro-max` skill.

# Style Rules (High-level)
- Clean enterprise admin aesthetic: restrained colors, strong hierarchy, minimal decoration.
- Consistent spacing system (e.g., 4/8/12/16/24).
- Clear typography scale (title/subtitle/body/caption).
- Always define component states:
    - loading, empty, error, disabled
- Forms:
    - inline validation, clear error text, avoid noisy red blocks
- Tables:
    - sticky header if needed, pagination, row actions grouped
- Feedback:
    - toast for success/error, inline hints for validation

# Deliverables
- UI tokens (spacing/typography) in `src/styles/tokens.*`
- Standard layout: sidebar + header + content
- Reusable components:
    - `AppTable`, `AppForm`, `AppEmpty`, `AppLoading`, `AppError`

# Verification
- All pages have loading/empty/error states.
- UI is visually consistent across pages.

# Anti-Patterns
- Random spacing per page
- No empty state
- Overly flashy gradients/animations for enterprise admin
