---
name: base-05-commit-conventions
description: Enforce Conventional Commits  type(scope)  subject with scopes like base/infra/iam/org/auth/common. Keep commits small and reviewable.
metadata:
  short-description: Git commit hygiene
  tags: [base, git, commits]
---

# Purpose
Keep history readable and reviewable.

# Rules
- Commits MUST follow Conventional Commits:
    - feat|fix|docs|refactor|test|chore|perf(scope): subject
- Subjects MUST be imperative and concise.
- Scope SHOULD reflect module/domain.

# Deliverables
- Optional: CONTRIBUTING.md describing conventions.

# Acceptance Checklist
- [ ] Recent commits follow the format.

# Anti-Patterns
- “update”, “fix bug” without scope
- Massive commits spanning unrelated modules
