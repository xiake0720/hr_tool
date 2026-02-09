import { reactive } from "vue";

export type ToastTone = "success" | "warning" | "error" | "info";

export interface ToastItem {
  id: string;
  message: string;
  tone: ToastTone;
  duration: number;
}

const state = reactive({
  items: [] as ToastItem[]
});

function remove(id: string): void {
  state.items = state.items.filter((item) => item.id !== id);
}

function notify(message: string, tone: ToastTone = "info", duration = 3000): void {
  const id = Math.random().toString(36).slice(2, 9);
  state.items = [...state.items, { id, message, tone, duration }];
  window.setTimeout(() => remove(id), duration);
}

export function useToast() {
  return {
    toasts: state.items,
    notify,
    remove
  };
}
