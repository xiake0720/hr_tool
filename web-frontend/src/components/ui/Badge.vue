<template>
  <span class="ui-badge" :class="`ui-badge--${tone}`">
    <slot />
  </span>
</template>

<script setup lang="ts">
type Tone = "neutral" | "success" | "warning" | "danger" | "info";

interface Props {
  tone?: Tone;
}

const props = withDefaults(defineProps<Props>(), {
  tone: "neutral"
});
</script>

<style scoped>
.ui-badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 18px;
  height: 18px;
  padding: 0 var(--space-2);
  border-radius: 999px;
  font-size: 11px;
  font-weight: 600;
  background: var(--bg-muted);
  color: var(--text-muted);
}

.ui-badge--success {
  background: var(--success-50);
  color: var(--success-500);
}

.ui-badge--warning {
  background: var(--warn-50);
  color: var(--warn-500);
}

.ui-badge--danger {
  background: var(--danger-50);
  color: var(--danger-500);
}

.ui-badge--info {
  background: var(--info-50);
  color: var(--info-500);
}
</style>
