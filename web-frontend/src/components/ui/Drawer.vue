<template>
  <teleport to="body">
    <div v-if="open" class="ui-drawer__overlay" @click="onBackdrop">
      <aside
        ref="drawerRef"
        class="ui-drawer"
        role="dialog"
        aria-modal="true"
        :aria-labelledby="titleId"
        :aria-describedby="descriptionId"
        tabindex="-1"
        @click.stop
      >
        <header class="ui-drawer__header">
          <div>
            <h2 :id="titleId" class="ui-drawer__title">{{ title }}</h2>
            <p v-if="description" :id="descriptionId" class="ui-drawer__description">{{ description }}</p>
          </div>
          <button class="ui-drawer__close" type="button" aria-label="Close" @click="emitClose">x</button>
        </header>
        <div class="ui-drawer__body">
          <slot />
        </div>
        <footer v-if="$slots.footer" class="ui-drawer__footer">
          <slot name="footer" />
        </footer>
      </aside>
    </div>
  </teleport>
</template>

<script setup lang="ts">
import { nextTick, onBeforeUnmount, ref, watch } from "vue";

interface Props {
  open: boolean;
  title: string;
  description?: string;
  closeOnBackdrop?: boolean;
  closeOnEsc?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  description: "",
  closeOnBackdrop: true,
  closeOnEsc: true
});

const emit = defineEmits<{ (event: "close"): void }>();
const drawerRef = ref<HTMLElement | null>(null);
const uid = Math.random().toString(36).slice(2, 9);
const titleId = `ui-drawer-title-${uid}`;
const descriptionId = `ui-drawer-desc-${uid}`;

function emitClose(): void {
  emit("close");
}

function onBackdrop(event: MouseEvent): void {
  if (!props.closeOnBackdrop) return;
  if (event.target === event.currentTarget) {
    emitClose();
  }
}

function onKeydown(event: KeyboardEvent): void {
  if (event.key === "Escape" && props.closeOnEsc) {
    emitClose();
  }
}

watch(
  () => props.open,
  async (isOpen) => {
    if (isOpen) {
      window.addEventListener("keydown", onKeydown);
      await nextTick();
      drawerRef.value?.focus();
    } else {
      window.removeEventListener("keydown", onKeydown);
    }
  },
  { immediate: true }
);

onBeforeUnmount(() => {
  window.removeEventListener("keydown", onKeydown);
});
</script>

<style scoped>
.ui-drawer__overlay {
  position: fixed;
  inset: 0;
  background: var(--overlay);
  display: flex;
  justify-content: flex-end;
  z-index: var(--z-modal);
}

.ui-drawer {
  width: min(420px, 100%);
  height: 100%;
  background: var(--bg-alt);
  border-left: 1px solid var(--border);
  box-shadow: var(--shadow-3);
  padding: var(--space-5);
  display: grid;
  gap: var(--space-4);
  outline: none;
  overflow: auto;
}

.ui-drawer__header {
  display: flex;
  justify-content: space-between;
  gap: var(--space-3);
}

.ui-drawer__title {
  margin: 0;
  font-size: var(--text-xl);
}

.ui-drawer__description {
  margin: var(--space-1) 0 0;
  color: var(--text-muted);
}

.ui-drawer__body {
  display: grid;
  gap: var(--space-3);
}

.ui-drawer__footer {
  display: flex;
  justify-content: flex-end;
  gap: var(--space-2);
}

.ui-drawer__close {
  border: 1px solid var(--border);
  background: var(--bg-alt);
  color: var(--text-muted);
  width: 32px;
  height: 32px;
  border-radius: 999px;
  cursor: pointer;
}

.ui-drawer__close:hover {
  color: var(--text);
  border-color: var(--brand-500);
}
</style>

<!--
Demo:
<UiDrawer :open="open" title="Candidate" @close="open=false">
  <p class="muted">Review candidate details and notes.</p>
</UiDrawer>
-->
