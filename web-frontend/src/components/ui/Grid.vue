<template>
  <div class="ui-grid" :style="gridStyle">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";

interface Props {
  cols?: number;
  gap?: number;
}

const props = withDefaults(defineProps<Props>(), {
  cols: 2,
  gap: 2
});

const gridStyle = computed(() => ({
  display: "grid",
  gridTemplateColumns: `repeat(${props.cols}, minmax(0, 1fr))`,
  gap: `var(--sp-${props.gap})`
}));
</script>
