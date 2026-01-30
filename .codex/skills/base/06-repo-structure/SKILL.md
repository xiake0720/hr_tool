---
name: base-06-repo-structure
description: Enforce repo layout  java-platform/ (Spring Boot 4), python-workers/ (LLM compute later), infra/ (docker/keycloak), .codex/skills/ (skills). Prevent mixing LLM logic into Java.
metadata:
  short-description: Repository layout & responsibility split
  tags: [base, repo, structure, boundaries]
---

# Purpose
Keep platform maintainable as tools grow.

# Rules
- Java platform code MUST live in `java-platform/`.
- Worker code MUST live in `python-workers/` (Phase 2+).
- Infrastructure MUST live in `infra/`.
- Skills MUST live in `.codex/skills/`.
- Phase 1: implement only platform + IAM in java-platform.

# Deliverables
- Directory skeleton exists with README notes.

# Acceptance Checklist
- [ ] No python LLM logic in java-platform.
- [ ] No infra scripts mixed into application source.

# Anti-Patterns
- Copying worker logic into controllers/services
