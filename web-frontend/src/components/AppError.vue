<template>
  <div class="page-card">
    <div class="status" style="color: var(--danger)">
      <span class="status-dot" style="background: var(--danger)"></span>
      <span>{{ displayMessage }}</span>
    </div>
    <UiButton v-if="onRetry" size="sm" variant="secondary" style="margin-top: var(--space-md)" @click="onRetry">
      {{ t("common.retry") }}
    </UiButton>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useI18n } from "vue-i18n";
import UiButton from "./ui/Button.vue";

interface Props {
  message?: string;
  onRetry?: () => void;
}

const props = defineProps<Props>();
const { t } = useI18n();

const displayMessage = computed(() => props.message || t("common.requestFailed"));
</script>
