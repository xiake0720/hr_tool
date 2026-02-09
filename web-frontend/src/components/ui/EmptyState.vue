<template>
  <div class="ui-empty">
    <div class="ui-empty__icon">
      <slot name="icon">?</slot>
    </div>
    <div class="ui-empty__title">{{ title }}</div>
    <div v-if="description" class="ui-empty__desc">{{ description }}</div>
    <div v-if="$slots.action" class="ui-empty__action">
      <slot name="action" />
    </div>
  </div>
</template>

<script setup lang="ts">
interface Props {
  title: string;
  description?: string;
}

const props = withDefaults(defineProps<Props>(), {
  description: ""
});
</script>

<style scoped>
.ui-empty {
  padding: var(--space-5);
  border-radius: var(--radius-lg);
  border: 1px dashed var(--border);
  background: var(--bg-alt);
  text-align: center;
  display: grid;
  gap: var(--space-2);
  color: var(--text);
}

.ui-empty__icon {
  width: 48px;
  height: 48px;
  border-radius: 999px;
  background: var(--bg-muted);
  display: grid;
  place-items: center;
  margin: 0 auto;
  color: var(--text-muted);
  font-size: var(--text-lg);
}

.ui-empty__title {
  font-weight: 600;
  font-size: var(--text-lg);
}

.ui-empty__desc {
  color: var(--text-muted);
  font-size: var(--text-md);
}

.ui-empty__action {
  margin-top: var(--space-2);
}
</style>
