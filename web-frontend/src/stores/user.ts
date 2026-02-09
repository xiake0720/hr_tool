import { defineStore } from "pinia";
import { ref } from "vue";

const TOKEN_KEY = "auth_token";
const REQUEST_ID_KEY = "last_request_id";

export interface MeResponse {
  externalSub?: string;
  username?: string;
  email?: string;
  requestId?: string;
  tenantId?: string | number;
  userId?: string | number;
}

export function getStoredToken(): string {
  return localStorage.getItem(TOKEN_KEY) ?? "";
}

export function setStoredToken(token: string): void {
  if (token) {
    localStorage.setItem(TOKEN_KEY, token);
  } else {
    localStorage.removeItem(TOKEN_KEY);
  }
}

export function getStoredRequestId(): string {
  return localStorage.getItem(REQUEST_ID_KEY) ?? "";
}

export function setStoredRequestId(requestId: string): void {
  if (requestId) {
    localStorage.setItem(REQUEST_ID_KEY, requestId);
  }
}

export const useUserStore = defineStore("user", () => {
  const token = ref(getStoredToken());
  const me = ref<MeResponse | null>(null);

  function setToken(nextToken: string): void {
    token.value = nextToken;
    setStoredToken(nextToken);
  }

  function clearToken(): void {
    token.value = "";
    setStoredToken("");
  }

  function setMe(payload: MeResponse | null): void {
    me.value = payload;
  }

  return {
    token,
    me,
    setToken,
    clearToken,
    setMe
  };
});
