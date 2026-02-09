<template>
  <div v-if="open" class="modal-backdrop" role="dialog" aria-modal="true">
    <div class="modal-card">
      <div class="modal-header">
        <h3 class="modal-title">{{ titleText }}</h3>
        <UiButton variant="secondary" size="sm" type="button" @click="handleClose">
          {{ t("common.close") }}
        </UiButton>
      </div>
      <div class="modal-body">
        <slot>
          <p class="helper">{{ t("common.formPlaceholder") }}</p>
        </slot>
      </div>
      <div class="modal-footer">
        <UiButton type="button" disabled>{{ t("common.saveSoon") }}</UiButton>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useI18n } from "vue-i18n";
import UiButton from "./ui/Button.vue";

interface Props {
  open: boolean;
  title?: string;
  onClose?: () => void;
}

const props = defineProps<Props>();
const { t } = useI18n();
const titleText = computed(() => props.title || t("common.formTitle"));

function handleClose(): void {
  props.onClose?.();
}
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(15, 23, 42, 0.45);
  display: grid;
  place-items: center;
  padding: var(--space-xl);
  z-index: 30;
}

.modal-card {
  background: var(--surface);
  border-radius: var(--radius-lg);
  width: min(560px, 100%);
  box-shadow: var(--shadow-lg);
  padding: var(--space-xl);
  display: flex;
  flex-direction: column;
  gap: var(--space-lg);
}

.modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-md);
}

.modal-title {
  margin: 0;
  font-family: var(--font-display);
  font-size: 20px;
}

.modal-body {
  display: grid;
  gap: var(--space-md);
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
}
</style>
