<template>
  <div class="ui-stack" :style="stackStyle">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";

interface Props {
  gap?: number;
  direction?: "vertical" | "horizontal";
  align?: string;
  justify?: string;
}

const props = withDefaults(defineProps<Props>(), {
  gap: 2,
  direction: "vertical",
  align: "stretch",
  justify: "flex-start"
});

const stackStyle = computed(() => ({
  display: "flex",
  flexDirection: props.direction === "vertical" ? "column" : "row",
  gap: `var(--sp-${props.gap})`,
  alignItems: props.align,
  justifyContent: props.justify
}));
</script>
