<template>
  <button
    :type="type"
    class="ui-button"
    :class="[`ui-button--${variant}`, `ui-button--${size}`, { 'ui-button--block': block }]"
    :disabled="disabled || loading"
    :aria-busy="loading ? 'true' : 'false'"
  >
    <span v-if="loading" class="ui-button__spinner" aria-hidden="true"></span>
    <span class="ui-button__label"><slot /></span>
  </button>
</template>

<script setup lang="ts">
type Variant = "primary" | "secondary" | "ghost" | "danger";
type Size = "sm" | "md" | "lg";

interface Props {
  type?: "button" | "submit" | "reset";
  variant?: Variant;
  size?: Size;
  loading?: boolean;
  disabled?: boolean;
  block?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  type: "button",
  variant: "primary",
  size: "md",
  loading: false,
  disabled: false,
  block: false
});
</script>

<style scoped>
.ui-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: var(--space-2);
  border-radius: var(--radius-md);
  border: 1px solid transparent;
  padding: 0 var(--space-4);
  height: 36px;
  line-height: 1;
  font-size: var(--text-md);
  font-weight: 600;
  cursor: pointer;
  transition: transform var(--dur-2) var(--ease-out), box-shadow var(--dur-2) var(--ease-out),
    background var(--dur-2) var(--ease-out), border-color var(--dur-2) var(--ease-out),
    color var(--dur-2) var(--ease-out);
}

.ui-button:focus-visible {
  outline: 2px solid var(--brand-500);
  outline-offset: 2px;
}

.ui-button--block {
  width: 100%;
}

.ui-button--sm {
  height: 32px;
  padding: 0 var(--space-3);
  font-size: var(--text-sm);
}

.ui-button--lg {
  height: 40px;
  padding: 0 var(--space-5);
  font-size: var(--text-lg);
}

.ui-button--primary {
  background: var(--brand-500);
  color: var(--text-on-brand);
  box-shadow: var(--shadow-1);
}

.ui-button--primary:hover:not(:disabled) {
  background: var(--brand-600);
  box-shadow: var(--shadow-2);
  transform: translateY(-1px);
}

.ui-button--primary:active:not(:disabled) {
  transform: translateY(0);
  box-shadow: var(--shadow-1);
}

.ui-button--secondary {
  background: var(--bg-alt);
  border-color: var(--border);
  color: var(--text);
  box-shadow: var(--shadow-1);
}

.ui-button--secondary:hover:not(:disabled) {
  border-color: var(--brand-500);
  color: var(--brand-500);
  box-shadow: var(--shadow-2);
  transform: translateY(-1px);
}

.ui-button--secondary:active:not(:disabled) {
  transform: translateY(0);
  box-shadow: var(--shadow-1);
}

.ui-button--ghost {
  background: transparent;
  color: var(--text-muted);
}

.ui-button--ghost:hover:not(:disabled) {
  background: var(--bg-muted);
  color: var(--text);
}

.ui-button--danger {
  background: var(--danger-50);
  color: var(--danger-500);
  border-color: var(--danger-50);
}

.ui-button--danger:hover:not(:disabled) {
  background: var(--danger-50);
  box-shadow: var(--shadow-2);
  transform: translateY(-1px);
}

.ui-button:disabled {
  cursor: not-allowed;
  opacity: 0.6;
  transform: none;
  box-shadow: none;
}

.ui-button__spinner {
  width: 14px;
  height: 14px;
  border-radius: 999px;
  border: 2px solid var(--text-on-brand);
  border-top-color: var(--brand-100);
  animation: ui-spin var(--dur-3) linear infinite;
}

.ui-button--secondary .ui-button__spinner,
.ui-button--ghost .ui-button__spinner,
.ui-button--danger .ui-button__spinner {
  border-color: var(--border);
  border-top-color: var(--text);
}

@keyframes ui-spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
