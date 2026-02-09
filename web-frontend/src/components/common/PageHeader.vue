<template>
  <header class="ui-page-header">
    <div>
      <h2 class="ui-page-header__title">{{ title }}</h2>
      <p v-if="subtitle" class="ui-page-header__subtitle">{{ subtitle }}</p>
    </div>
    <div class="ui-page-header__actions">
      <slot name="actions" />
    </div>
  </header>
</template>

<script setup lang="ts">
interface Props {
  title: string;
  subtitle?: string;
}

const props = withDefaults(defineProps<Props>(), {
  subtitle: ""
});
</script>

<style scoped>
.ui-page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-3);
  padding-bottom: var(--space-2);
}

.ui-page-header__title {
  margin: 0;
  font-size: var(--text-xl);
}

.ui-page-header__subtitle {
  margin: var(--space-1) 0 0;
  color: var(--text-muted);
  font-size: var(--text-md);
}

.ui-page-header__actions {
  display: inline-flex;
  gap: var(--space-2);
  align-items: center;
}
</style>
