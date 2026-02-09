<template>
  <teleport to="body">
    <div v-if="open" class="ui-modal__overlay" @click="onBackdrop">
      <section
        ref="dialogRef"
        class="ui-modal"
        role="dialog"
        aria-modal="true"
        :aria-labelledby="titleId"
        :aria-describedby="descriptionId"
        tabindex="-1"
        @click.stop
      >
        <header class="ui-modal__header">
          <div>
            <h2 :id="titleId" class="ui-modal__title">{{ title }}</h2>
            <p v-if="description" :id="descriptionId" class="ui-modal__description">{{ description }}</p>
          </div>
          <button class="ui-modal__close" type="button" aria-label="Close" @click="emitClose">x</button>
        </header>
        <div class="ui-modal__body">
          <slot />
        </div>
        <footer v-if="$slots.footer" class="ui-modal__footer">
          <slot name="footer" />
        </footer>
      </section>
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
const dialogRef = ref<HTMLElement | null>(null);
const uid = Math.random().toString(36).slice(2, 9);
const titleId = `ui-modal-title-${uid}`;
const descriptionId = `ui-modal-desc-${uid}`;

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
    return;
  }
  if (event.key === "Tab" && dialogRef.value) {
    const focusable = dialogRef.value.querySelectorAll<HTMLElement>(
      "button, [href], input, select, textarea, [tabindex]:not([tabindex='-1'])"
    );
    if (focusable.length === 0) return;
    const first = focusable[0];
    const last = focusable[focusable.length - 1];
    if (event.shiftKey && document.activeElement === first) {
      event.preventDefault();
      last.focus();
    } else if (!event.shiftKey && document.activeElement === last) {
      event.preventDefault();
      first.focus();
    }
  }
}

watch(
  () => props.open,
  async (isOpen) => {
    if (isOpen) {
      window.addEventListener("keydown", onKeydown);
      await nextTick();
      dialogRef.value?.focus();
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
.ui-modal__overlay {
  position: fixed;
  inset: 0;
  background: var(--overlay);
  display: grid;
  place-items: center;
  padding: var(--sp-3);
  z-index: var(--z-modal);
}

.ui-modal {
  width: min(640px, 100%);
  background: var(--bg-alt);
  border-radius: var(--radius-lg);
  border: 1px solid var(--border);
  box-shadow: var(--shadow-3);
  padding: var(--space-5);
  display: grid;
  gap: var(--space-4);
  outline: none;
}

.ui-modal__header {
  display: flex;
  justify-content: space-between;
  gap: var(--space-3);
}

.ui-modal__title {
  margin: 0;
  font-size: var(--text-xl);
}

.ui-modal__description {
  margin: var(--space-1) 0 0;
  color: var(--text-muted);
}

.ui-modal__body {
  display: grid;
  gap: var(--space-3);
}

.ui-modal__footer {
  display: flex;
  justify-content: flex-end;
  gap: var(--space-2);
}

.ui-modal__close {
  border: 1px solid var(--border);
  background: var(--bg-alt);
  color: var(--text-muted);
  width: 32px;
  height: 32px;
  border-radius: 999px;
  cursor: pointer;
}

.ui-modal__close:hover {
  color: var(--text);
  border-color: var(--brand-500);
}
</style>

<!--
Demo:
<UiModal :open="open" title="Invite user" @close="open=false">
  <p class="muted">Invite a new teammate to the workspace.</p>
  <template #footer>
    <UiButton variant="secondary" @click="open=false">Cancel</UiButton>
    <UiButton>Send</UiButton>
  </template>
</UiModal>
-->
