<template>
  <div class="ui-toast-host" role="status" aria-live="polite">
    <div v-for="toast in toasts" :key="toast.id" class="ui-toast" :class="`ui-toast--${toast.tone}`">
      <span>{{ toast.message }}</span>
      <button class="ui-toast__close" type="button" aria-label="Dismiss" @click="remove(toast.id)">x</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useToast } from "./useToast";

const { toasts, remove } = useToast();
</script>

<style scoped>
.ui-toast-host {
  position: fixed;
  top: var(--space-4);
  right: var(--space-4);
  display: flex;
  flex-direction: column;
  gap: var(--space-2);
  z-index: var(--z-toast);
}

.ui-toast {
  min-width: 240px;
  padding: var(--space-3) var(--space-4);
  border-radius: var(--radius-md);
  background: var(--bg-alt);
  border: 1px solid var(--border);
  box-shadow: var(--shadow-2);
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-3);
  color: var(--text);
}

.ui-toast--success {
  border-color: var(--success-500);
}

.ui-toast--warning {
  border-color: var(--warn-500);
}

.ui-toast--error {
  border-color: var(--danger-500);
}

.ui-toast--info {
  border-color: var(--info-500);
}

.ui-toast__close {
  border: none;
  background: transparent;
  color: var(--text-muted);
  cursor: pointer;
}
</style>
