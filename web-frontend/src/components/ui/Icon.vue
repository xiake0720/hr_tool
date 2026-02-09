<template>
  <svg
    :class="iconClass"
    :width="size"
    :height="size"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    stroke-width="1.8"
    stroke-linecap="round"
    stroke-linejoin="round"
    aria-hidden="true"
  >
    <path v-for="(d, index) in paths" :key="index" :d="d" />
  </svg>
</template>

<script setup lang="ts">
import { computed } from "vue";

type IconName = "search" | "filter" | "chevron" | "close" | "plus" | "more" | "check" | "alert";

interface Props {
  name: IconName;
  size?: number | string;
  class?: string;
}

const props = withDefaults(defineProps<Props>(), {
  size: 16,
  class: ""
});

const iconMap: Record<IconName, string[]> = {
  search: ["M11 5a6 6 0 1 1 0 12a6 6 0 0 1 0-12", "M16 16l4 4"],
  filter: ["M4 6h16", "M7 12h10", "M10 18h4"],
  chevron: ["M6 9l6 6 6-6"],
  close: ["M6 6l12 12", "M18 6l-12 12"],
  plus: ["M12 5v14", "M5 12h14"],
  more: ["M5 12a1 1 0 1 0 0.01 0", "M12 12a1 1 0 1 0 0.01 0", "M19 12a1 1 0 1 0 0.01 0"],
  check: ["M5 13l4 4L19 7"],
  alert: ["M12 3l9 16H3l9-16", "M12 9v4", "M12 17h.01"]
};

const paths = computed(() => iconMap[props.name]);
const iconClass = computed(() => (props.class ? `ui-icon ${props.class}` : "ui-icon"));
</script>

<style scoped>
.ui-icon {
  display: inline-flex;
  color: currentColor;
}
</style>
