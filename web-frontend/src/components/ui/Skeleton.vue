<template>
  <div class="ui-skeleton" :class="`ui-skeleton--${shape}`" :style="style"></div>
</template>

<script setup lang="ts">
import { computed } from "vue";

type Shape = "line" | "circle" | "rect";

interface Props {
  width?: string;
  height?: string;
  shape?: Shape;
}

const props = withDefaults(defineProps<Props>(), {
  width: "100%",
  height: "12px",
  shape: "line"
});

const style = computed(() => ({
  width: props.width,
  height: props.height
}));
</script>

<style scoped>
.ui-skeleton {
  background: var(--bg-muted);
  border-radius: var(--radius-sm);
  animation: ui-skeleton var(--dur-3) var(--ease-out) infinite alternate;
}

.ui-skeleton--circle {
  border-radius: 999px;
}

.ui-skeleton--rect {
  border-radius: var(--radius-md);
}

@keyframes ui-skeleton {
  from {
    opacity: 0.6;
  }
  to {
    opacity: 1;
  }
}
</style>
