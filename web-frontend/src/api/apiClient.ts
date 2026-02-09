import axios, { type AxiosError, type AxiosRequestConfig, type AxiosResponse } from "axios";
import router from "../router";
import { getStoredRequestId, getStoredToken, setStoredRequestId } from "../stores/user";
import { showToast } from "../utils/toast";
import i18n from "../i18n";

export interface ApiResponse<T> {
  code: string;
  message: string;
  data: T;
  requestId?: string;
}

export interface ApiError {
  code: string;
  message: string;
  requestId?: string;
}

const client = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL ?? "",
  timeout: 15000
});

const t = (key: string) => i18n.global.t(key) as string;

client.interceptors.request.use((config) => {
  const token = getStoredToken();
  if (token) {
    config.headers = config.headers ?? {};
    config.headers.Authorization = `Bearer ${token}`;
  }

  const requestId = getStoredRequestId();
  if (requestId) {
    config.headers = config.headers ?? {};
    config.headers["X-Request-Id"] = requestId;
  }

  return config;
});

function unwrapApiResponse<T>(response: AxiosResponse<ApiResponse<T> | T>): T {
  const payload = response.data;
  if (payload && typeof payload === "object" && "code" in payload) {
    const apiPayload = payload as ApiResponse<T>;
    if (apiPayload.requestId) {
      setStoredRequestId(apiPayload.requestId);
    }

    if (apiPayload.code === "SYS_0000") {
      return apiPayload.data;
    }

    if (apiPayload.code === "AUTH_4001") {
      showToast({ message: t("common.pleaseLogin"), level: "warning" });
      if (router.currentRoute.value.path !== "/login") {
        router.push("/login");
      }
      throw { code: apiPayload.code, message: apiPayload.message, requestId: apiPayload.requestId } as ApiError;
    }

    if (apiPayload.code === "AUTH_4003") {
      showToast({ message: t("common.accessDenied"), level: "warning" });
      throw { code: apiPayload.code, message: apiPayload.message, requestId: apiPayload.requestId } as ApiError;
    }

    showToast({ message: apiPayload.message || t("common.requestFailed"), level: "error" });
    throw { code: apiPayload.code, message: apiPayload.message, requestId: apiPayload.requestId } as ApiError;
  }

  return payload as T;
}

client.interceptors.response.use(
  (response) => unwrapApiResponse(response),
  (error: AxiosError<ApiResponse<unknown>>) => {
    const response = error.response;
    if (response?.status === 401) {
      showToast({ message: t("common.pleaseLogin"), level: "warning" });
      if (router.currentRoute.value.path !== "/login") {
        router.push("/login");
      }
      return Promise.reject({ code: "AUTH_401", message: t("common.unauthorized") } as ApiError);
    }
    if (response?.status === 403) {
      showToast({ message: t("common.accessDenied"), level: "warning" });
      return Promise.reject({ code: "AUTH_403", message: t("common.forbidden") } as ApiError);
    }
    if (response?.data && typeof response.data === "object" && "code" in response.data) {
      const payload = response.data as ApiResponse<unknown>;
      if (payload.requestId) {
        setStoredRequestId(payload.requestId);
      }
      showToast({ message: payload.message || t("common.requestFailed"), level: "error" });
      return Promise.reject({ code: payload.code, message: payload.message, requestId: payload.requestId } as ApiError);
    }

    showToast({ message: error.message || t("common.networkError"), level: "error" });
    return Promise.reject({ code: "NETWORK_ERROR", message: error.message || t("common.networkError") } as ApiError);
  }
);

export async function apiRequest<T>(config: AxiosRequestConfig): Promise<T> {
  return client.request<T>(config) as unknown as Promise<T>;
}
