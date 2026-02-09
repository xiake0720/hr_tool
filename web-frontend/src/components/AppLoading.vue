<template>
  <div class="page-card">
    <div class="status">
      <span class="status-dot" style="background: var(--warning)"></span>
      <span>{{ t("common.loading") }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useI18n } from "vue-i18n";

const { t } = useI18n();
</script>
