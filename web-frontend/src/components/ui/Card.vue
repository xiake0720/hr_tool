<template>
  <div class="ui-card" :class="{ 'ui-card--hover': hoverable }">
    <slot />
  </div>
</template>

<script setup lang="ts">
interface Props {
  hoverable?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  hoverable: false
});
</script>

<style scoped>
.ui-card {
  background: var(--bg-alt);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-1);
  padding: var(--space-4);
  transition: box-shadow var(--dur-2) var(--ease-out), transform var(--dur-2) var(--ease-out);
}

.ui-card--hover:hover {
  box-shadow: var(--shadow-3);
  transform: translateY(-2px);
}
</style>
