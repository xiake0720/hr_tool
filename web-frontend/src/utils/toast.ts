import i18n from "../i18n";

export type ToastLevel = "info" | "success" | "warning" | "error";

export interface ToastOptions {
  message: string;
  level?: ToastLevel;
}

export function showToast(options: ToastOptions | string): void {
  const payload = typeof options === "string" ? { message: options } : options;
  const level = payload.level ?? "info";
  const t = i18n.global.t;
  const prefix =
    level === "error"
      ? t("toast.error")
      : level === "warning"
        ? t("toast.warning")
        : level === "success"
          ? t("toast.success")
          : t("toast.info");
  alert(`${prefix}: ${payload.message}`);
}
