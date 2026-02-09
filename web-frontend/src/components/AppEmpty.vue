<template>
  <div class="page-card">
    <div class="status">
      <span class="status-dot" style="background: var(--muted)"></span>
      <span>{{ displayMessage }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useI18n } from "vue-i18n";

interface Props {
  message?: string;
}

const props = defineProps<Props>();
const { t } = useI18n();

const displayMessage = computed(() => props.message || t("common.noData"));
</script>
