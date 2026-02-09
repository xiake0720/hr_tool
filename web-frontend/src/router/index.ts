import { createRouter, createWebHistory, type RouteRecordRaw } from "vue-router";
import LoginPage from "../pages/LoginPage.vue";
import MePage from "../pages/MePage.vue";
import UsersPage from "../pages/UsersPage.vue";
import OrgPage from "../pages/OrgPage.vue";
import RolesPage from "../pages/RolesPage.vue";
import CandidateListPage from "../pages/CandidateListPage.vue";
import CandidatesListPage from "../pages/candidates/CandidatesListPage.vue";
import { getStoredToken } from "../stores/user";

const routes: RouteRecordRaw[] = [
  { path: "/", redirect: "/me" },
  { path: "/login", component: LoginPage, meta: { public: true } },
  { path: "/me", component: MePage, meta: { layout: "admin" } },
  { path: "/users", component: UsersPage, meta: { layout: "admin" } },
  { path: "/org", component: OrgPage, meta: { layout: "admin" } },
  { path: "/roles", component: RolesPage, meta: { layout: "admin" } },
  { path: "/candidates", component: CandidateListPage, meta: { layout: "admin" } },
  { path: "/candidates-list", component: CandidatesListPage, meta: { layout: "admin" } }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to) => {
  if (to.meta.public) {
    return true;
  }
  const token = getStoredToken();
  if (!token) {
    return { path: "/login", query: { redirect: to.fullPath } };
  }
  return true;
});

export default router;
