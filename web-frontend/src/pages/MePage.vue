<template>
  <section>
    <h2 class="page-title">{{ t("common.currentUser") }}</h2>
    <p class="page-subtitle">{{ t("common.meSubtitle") }}</p>
    <AppLoading v-if="loading" />
    <AppError v-else-if="error" :message="error" :on-retry="load" />
    <div v-else class="page-card">
      <div class="status" style="margin-bottom: var(--space-md);">
        <span class="status-dot"></span>
        <span>{{ t("common.connected") }}</span>
      </div>
      <div style="display: grid; gap: 8px;">
        <div><strong>{{ t("common.externalSub") }}:</strong> {{ me?.externalSub || "-" }}</div>
        <div><strong>{{ t("common.username") }}:</strong> {{ me?.username || "-" }}</div>
        <div><strong>{{ t("common.email") }}:</strong> {{ me?.email || "-" }}</div>
        <div><strong>{{ t("common.requestId") }}:</strong> {{ requestId || "-" }}</div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useI18n } from "vue-i18n";
import AppLoading from "../components/AppLoading.vue";
import AppError from "../components/AppError.vue";
import { getMe } from "../api/iam";
import { getStoredRequestId, type MeResponse } from "../stores/user";

const me = ref<MeResponse | null>(null);
const requestId = ref("");
const loading = ref(false);
const error = ref<string | null>(null);
const { t } = useI18n();

async function load(): Promise<void> {
  loading.value = true;
  error.value = null;
  try {
    me.value = await getMe();
    requestId.value = getStoredRequestId();
  } catch (err) {
    error.value = t("common.errorLoadMe");
    requestId.value = getStoredRequestId();
  } finally {
    loading.value = false;
  }
}

onMounted(load);
</script>
