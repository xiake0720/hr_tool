---
name: common-frontend-vue-web
description: "Vue 3 web frontend plan: project structure under web-frontend/, Vite+TS, Pinia, Router, axios client with ApiResponse parsing, and environment-based API base URL."
metadata:
  short-description: "Vue web scaffolding conventions"
  tags: [common, frontend, vue3, vite, pinia, axios]
---

# Purpose
Keep the Vue web client scalable and consistent with backend contracts.

# Project Path & Structure (Fixed)
- Root: `web-frontend/`
- Suggested structure:
    - `src/api/` (axios client, typed requests)
    - `src/pages/` (route pages)
    - `src/components/` (reusable components)
    - `src/stores/` (Pinia stores)
    - `src/router/` (routes, guards)
    - `src/styles/` (global styles, tokens)
    - `src/layouts/` (admin layout)

# Rules
- Use Vue 3 + Vite + TypeScript.
- Use Pinia for state; Vue Router for navigation.
- axios client must:
    - attach requestId header if available
    - parse `ApiResponse {code,message,data,requestId}`
    - handle AUTH_4001 by redirecting to login or showing re-auth prompt
- API base URL must come from env:
    - `VITE_API_BASE_URL`

# Deliverables
- Typed `apiClient` wrapper
- Unified error/toast handling (consistent UX)

# Verification
- Can call `/api/me` and display current user context.
- Handles 401/403 with correct UI feedback.

# Anti-Patterns
- Direct axios calls scattered in components without wrapper
- Ignoring ApiResponse wrapper and assuming raw JSON
