<template>
  <div class="ui-divider" :class="`ui-divider--${direction}`"></div>
</template>

<script setup lang="ts">
type Direction = "horizontal" | "vertical";

interface Props {
  direction?: Direction;
}

const props = withDefaults(defineProps<Props>(), {
  direction: "horizontal"
});
</script>

<style scoped>
.ui-divider {
  background: var(--border);
}

.ui-divider--horizontal {
  height: 1px;
  width: 100%;
}

.ui-divider--vertical {
  width: 1px;
  height: 100%;
}
</style>
