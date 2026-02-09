<template>
  <span class="ui-tooltip" @mouseenter="show" @mouseleave="hide" @focusin="show" @focusout="hide">
    <span ref="triggerRef" class="ui-tooltip__trigger" :aria-describedby="tooltipId">
      <slot />
    </span>
    <span v-if="visible" :id="tooltipId" class="ui-tooltip__content" role="tooltip">
      {{ content }}
    </span>
  </span>
</template>

<script setup lang="ts">
import { ref } from "vue";

interface Props {
  content: string;
}

const props = defineProps<Props>();
const visible = ref(false);
const triggerRef = ref<HTMLElement | null>(null);
const tooltipId = `ui-tooltip-${Math.random().toString(36).slice(2, 9)}`;

function show(): void {
  visible.value = true;
}

function hide(): void {
  visible.value = false;
}
</script>

<style scoped>
.ui-tooltip {
  position: relative;
  display: inline-flex;
}

.ui-tooltip__content {
  position: absolute;
  bottom: calc(100% + var(--space-1));
  left: 50%;
  transform: translateX(-50%);
  background: var(--text);
  color: var(--text-on-brand);
  padding: var(--space-1) var(--space-2);
  border-radius: var(--radius-sm);
  font-size: var(--text-sm);
  white-space: nowrap;
  box-shadow: var(--shadow-2);
  z-index: var(--z-dropdown);
}
</style>
