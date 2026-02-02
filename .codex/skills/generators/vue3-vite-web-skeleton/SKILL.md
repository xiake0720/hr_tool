---
name: gen-vue3-vite-web-skeleton
description: "Generate Vue 3 + Vite + TypeScript admin web under web-frontend/ with Router+Pinia+axios ApiResponse client and UI scaffolding aligned to ui-ux-pro-max."
metadata:
  short-description: "Vue admin skeleton"
  tags: [generator, vue3, vite, typescript, pinia, router, axios]
---

# Purpose
Bootstrap a scalable Vue admin that matches backend contracts.

# Rules
- Create `web-frontend/` using Vue 3 + Vite + TS.
- Add Router, Pinia, axios client wrapper parsing ApiResponse.
- Add layout skeleton and common UI components (loading/empty/error).
- Apply ui-ux-pro-max style tokens.

# Deliverables
- `src/api/apiClient.ts` with ApiResponse parsing and global error handling
- `src/pages/MePage.vue` that calls `/api/me`
- `src/layouts/AdminLayout.vue` skeleton
- `src/styles/tokens.ts` and basic global styles

# Verification
- `npm run dev` works and Me page displays current user info when backend is running.

# Anti-Patterns
- axios calls scattered without wrapper
- ignoring ApiResponse wrapper
