<template>
  <span class="ui-tag" :class="`ui-tag--${tone}`">
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
.ui-tag {
  display: inline-flex;
  align-items: center;
  height: 24px;
  padding: 0 var(--space-3);
  border-radius: 999px;
  font-size: var(--text-sm);
  font-weight: 600;
  background: var(--bg-muted);
  color: var(--text-muted);
  width: fit-content;
}

.ui-tag--success {
  background: var(--success-50);
  color: var(--success-500);
}

.ui-tag--warning {
  background: var(--warn-50);
  color: var(--warn-500);
}

.ui-tag--danger {
  background: var(--danger-50);
  color: var(--danger-500);
}

.ui-tag--info {
  background: var(--info-50);
  color: var(--info-500);
}
</style>
