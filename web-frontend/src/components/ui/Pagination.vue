<template>
  <div class="ui-pagination">
    <div class="ui-pagination__info">Total {{ total }} items</div>
    <div class="ui-pagination__controls">
      <UiButton size="sm" variant="secondary" :disabled="page === 1" @click="emitPage(page - 1)">
        Prev
      </UiButton>
      <span class="ui-pagination__page">{{ page }} / {{ totalPages }}</span>
      <UiButton size="sm" variant="secondary" :disabled="page === totalPages" @click="emitPage(page + 1)">
        Next
      </UiButton>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import UiButton from "./Button.vue";

interface Props {
  page: number;
  pageSize: number;
  total: number;
}

const props = defineProps<Props>();
const emit = defineEmits<{ (event: "update:page", value: number): void }>();

const totalPages = computed(() => Math.max(1, Math.ceil(props.total / props.pageSize)));

function emitPage(value: number): void {
  emit("update:page", Math.min(Math.max(1, value), totalPages.value));
}
</script>

<style scoped>
.ui-pagination {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-3);
}

.ui-pagination__info {
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-pagination__controls {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

.ui-pagination__page {
  font-size: var(--text-sm);
  color: var(--text-muted);
}
</style>
