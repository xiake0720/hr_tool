---
name: base-09-module-boundaries
description: Prevent architecture erosion  IAM must not depend on recruitment. Common must not depend on business modules. Recruitment may depend on IAM/common. Keep dependency direction strict.
metadata:
  short-description: Dependency direction rules
  tags: [base, architecture, boundaries]
---

# Purpose
Keep the codebase evolvable as HR tools expand.

# Rules
- `modules/iam` MUST NOT reference `modules/recruitment` or future business modules.
- `common` MUST NOT reference any business module.
- Business modules MAY depend on `common` and `iam`.
- No cyclic dependencies.

# Deliverables
- Package structure that reflects boundaries:
    - `com.xxx.hrtools.common`
    - `com.xxx.hrtools.modules.iam`
    - (Phase 2+) `com.xxx.hrtools.modules.recruitment`

# Acceptance Checklist
- [ ] Build passes with no cross-module imports violating direction.
- [ ] IAM can be compiled independently of recruitment.

# Anti-Patterns
- Shared “god” module where everything depends on everything
