<template>
  <span
    ref="triggerRef"
    class="ui-popover__trigger"
    :class="{ 'ui-popover__trigger--full': props.fullWidth === true || props.matchTriggerWidth === true }"
    @click="toggle"
    @keydown="onKeydown"
    tabindex="0"
  >
    <slot name="trigger" />
  </span>

  <teleport to="body">
    <div
      v-if="isOpen"
      ref="panelRef"
      class="ui-popover__panel"
      :style="panelStyle"
      role="dialog"
      @keydown.esc="close"
    >
      <slot />
    </div>
  </teleport>
</template>

<script setup lang="ts">
import { computed, nextTick, onBeforeUnmount, ref, watch } from "vue";

interface Props {
  open?: boolean;
  minWidth?: number;
  maxWidth?: number;
  maxHeight?: number;
  offset?: number;
  align?: "left" | "right";
  fullWidth?: boolean;
  matchTriggerWidth?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  open: undefined,
  minWidth: 0,
  maxWidth: 420,
  maxHeight: 320,
  offset: 8,
  align: "left",
  fullWidth: false,
  matchTriggerWidth: false
});

const emit = defineEmits<{ (event: "update:open", value: boolean): void }>();
const triggerRef = ref<HTMLElement | null>(null);
const panelStyle = ref<Record<string, string>>({});
const panelRef = ref<HTMLElement | null>(null);

const isOpen = computed({
  get: () => props.open ?? false,
  set: (value: boolean) => emit("update:open", value)
});

function updatePosition(): void {
  const trigger = triggerRef.value;
  const panel = panelRef.value;
  if (!trigger || !panel) return;

  const rawViewportGap = Number.parseFloat(
    getComputedStyle(document.documentElement).getPropertyValue("--panel-viewport-gap")
  );
  const viewportGap = Number.isFinite(rawViewportGap) ? rawViewportGap : 12;
  const viewportWidth = window.innerWidth;
  const viewportHeight = window.innerHeight;
  const rect = trigger.getBoundingClientRect();
  const triggerWidth = Math.max(0, rect.width);
  const computedMaxWidth = Math.max(160, Math.min(props.maxWidth, viewportWidth - viewportGap * 2));
  const computedMaxHeight = Math.max(160, Math.min(props.maxHeight, viewportHeight - viewportGap * 2));
  const minByTrigger = props.matchTriggerWidth ? triggerWidth : 0;
  const computedMinWidth = Math.min(computedMaxWidth, Math.max(props.minWidth, minByTrigger));

  panelStyle.value = {
    top: `${Math.max(viewportGap, rect.bottom + props.offset)}px`,
    left: `${Math.max(viewportGap, props.align === "right" ? rect.right - computedMinWidth : rect.left)}px`,
    minWidth: `${computedMinWidth}px`,
    maxWidth: `${computedMaxWidth}px`,
    maxHeight: `${computedMaxHeight}px`,
    "--popover-trigger-w": `${triggerWidth}px`,
    "--popover-panel-min-w": `${computedMinWidth}px`
  };

  const panelRect = panel.getBoundingClientRect();
  const panelWidth = Math.min(panelRect.width, computedMaxWidth);
  const panelHeight = Math.min(panelRect.height, computedMaxHeight);
  const desiredLeft = props.align === "right" ? rect.right - panelWidth : rect.left;
  const desiredTop = rect.bottom + props.offset;
  const clampedLeft = Math.min(
    Math.max(viewportGap, desiredLeft),
    Math.max(viewportGap, viewportWidth - viewportGap - panelWidth)
  );
  const clampedTop = Math.min(
    Math.max(viewportGap, desiredTop),
    Math.max(viewportGap, viewportHeight - viewportGap - panelHeight)
  );

  panelStyle.value = {
    top: `${clampedTop}px`,
    left: `${clampedLeft}px`,
    minWidth: `${computedMinWidth}px`,
    maxWidth: `${computedMaxWidth}px`,
    maxHeight: `${computedMaxHeight}px`,
    "--popover-trigger-w": `${triggerWidth}px`,
    "--popover-panel-min-w": `${computedMinWidth}px`
  };
}

function toggle(): void {
  isOpen.value = !isOpen.value;
}

function close(): void {
  isOpen.value = false;
}

function onKeydown(event: KeyboardEvent): void {
  if (event.key === "Enter" || event.key === " " || event.key === "ArrowDown") {
    event.preventDefault();
    toggle();
  }
}

function onOutsideClick(event: MouseEvent): void {
  const trigger = triggerRef.value;
  const panel = panelRef.value;
  if (!trigger || !panel) return;
  const target = event.target as Node;
  if (trigger.contains(target) || panel.contains(target)) {
    return;
  }
  close();
}

watch(
  () => isOpen.value,
  async (open) => {
    if (open) {
      await nextTick();
      updatePosition();
      window.addEventListener("scroll", updatePosition, true);
      window.addEventListener("resize", updatePosition);
      window.addEventListener("mousedown", onOutsideClick);
    } else {
      window.removeEventListener("scroll", updatePosition, true);
      window.removeEventListener("resize", updatePosition);
      window.removeEventListener("mousedown", onOutsideClick);
    }
  }
);

onBeforeUnmount(() => {
  window.removeEventListener("scroll", updatePosition, true);
  window.removeEventListener("resize", updatePosition);
  window.removeEventListener("mousedown", onOutsideClick);
});
</script>

<style scoped>
.ui-popover__trigger {
  display: inline-flex;
  width: fit-content;
  flex: 0 0 auto;
}

.ui-popover__trigger--full {
  display: flex;
  width: 100%;
  min-width: 0;
}

.ui-popover__trigger:focus-visible {
  outline: 2px solid var(--brand-500);
  outline-offset: 2px;
}

.ui-popover__panel {
  position: fixed;
  background: var(--bg-alt);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  box-shadow: var(--shadow-3);
  padding: var(--space-2);
  z-index: var(--z-dropdown);
  min-width: var(--popover-panel-min-w, 0px);
  max-width: var(--panel-max-w);
  max-height: var(--panel-max-h);
  overflow-x: hidden;
  overflow-y: auto;
}
</style>
