import { apiRequest } from "./apiClient";
import type { MeResponse } from "../stores/user";

export interface PageResponse<T> {
  items: T[];
  page: number;
  pageSize: number;
  total: number;
}

export interface User {
  id: number;
  tenantId?: number;
  externalSub?: string;
  loginName?: string;
  username?: string;
  email?: string;
  phone?: string;
  enabled?: boolean;
  createdAt?: string;
}

export interface OrgNode {
  id: number;
  parentId?: number | null;
  name: string;
  type?: string;
  path?: string;
  children?: OrgNode[];
}

export interface Role {
  id: number;
  code: string;
  name: string;
  createdAt?: string;
}

export interface Permission {
  id: number;
  action: string;
  name: string;
  createdAt?: string;
}

export function getMe(): Promise<MeResponse> {
  return apiRequest({ method: "GET", url: "/api/me" });
}

export function listUsers(page = 1, pageSize = 20): Promise<PageResponse<User>> {
  return apiRequest({ method: "GET", url: "/api/users", params: { page, pageSize } });
}

export function getOrgTree(): Promise<OrgNode[]> {
  return apiRequest({ method: "GET", url: "/api/org-units/tree" });
}

export function listRoles(page = 1, pageSize = 50): Promise<PageResponse<Role>> {
  return apiRequest({ method: "GET", url: "/api/roles", params: { page, pageSize } });
}

export function listPermissions(page = 1, pageSize = 50): Promise<PageResponse<Permission>> {
  return apiRequest({ method: "GET", url: "/api/permissions", params: { page, pageSize } });
}
