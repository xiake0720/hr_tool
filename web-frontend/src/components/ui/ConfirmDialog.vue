<template>
  <UiModal :open="open" :title="title" :description="description" @close="emitCancel">
    <template #footer>
      <UiButton variant="secondary" @click="emitCancel">{{ cancelText }}</UiButton>
      <UiButton :loading="loading" @click="emitConfirm">{{ confirmText }}</UiButton>
    </template>
  </UiModal>
</template>

<script setup lang="ts">
import UiModal from "./Modal.vue";
import UiButton from "./Button.vue";

interface Props {
  open: boolean;
  title: string;
  description?: string;
  confirmText?: string;
  cancelText?: string;
  loading?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  description: "",
  confirmText: "Confirm",
  cancelText: "Cancel",
  loading: false
});

const emit = defineEmits<{ (event: "confirm"): void; (event: "cancel"): void }>();

function emitConfirm(): void {
  emit("confirm");
}

function emitCancel(): void {
  emit("cancel");
}
</script>
