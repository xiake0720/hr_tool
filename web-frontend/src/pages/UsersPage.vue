<template>
  <section>
    <h2 class="page-title">{{ t("common.users") }}</h2>
    <p class="page-subtitle">{{ t("common.usersSubtitle") }}</p>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: var(--space-lg);">
      <div class="helper">{{ t("common.totalUsers") }}: {{ users.length }}</div>
      <UiButton size="sm" type="button" @click="showForm = true">{{ t("common.newUser") }}</UiButton>
    </div>
    <AppLoading v-if="loading" />
    <AppError v-else-if="error" :message="error" :on-retry="load" />
    <AppEmpty v-else-if="users.length === 0" :message="t('common.noUsers')" />
    <AppTable v-else :columns="columns" :rows="users">
      <template #cell-enabled="{ row }">
        <UiTag :tone="row.enabled ? 'success' : 'danger'">
          {{ row.enabled ? t("common.enabled") : t("common.disabled") }}
        </UiTag>
      </template>
    </AppTable>
    <AppForm :open="showForm" :title="t('common.formTitleUser')" :on-close="() => (showForm = false)">
      <p class="helper">{{ t("common.formPlaceholder") }}</p>
    </AppForm>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useI18n } from "vue-i18n";
import AppForm from "../components/AppForm.vue";
import UiButton from "../components/ui/Button.vue";
import UiTag from "../components/ui/Tag.vue";
import AppLoading from "../components/AppLoading.vue";
import AppError from "../components/AppError.vue";
import AppEmpty from "../components/AppEmpty.vue";
import AppTable from "../components/AppTable.vue";
import { listUsers, type User } from "../api/iam";

const users = ref<User[]>([]);
const showForm = ref(false);
const loading = ref(false);
const error = ref<string | null>(null);
const { t } = useI18n();

const columns = computed(() => [
  { key: "id", label: t("common.id"), width: "80px" },
  { key: "loginName", label: t("common.login") },
  { key: "username", label: t("common.name") },
  { key: "email", label: t("common.email") },
  { key: "enabled", label: t("common.status"), width: "110px" }
]);

async function load(): Promise<void> {
  loading.value = true;
  error.value = null;
  try {
    const page = await listUsers(1, 20);
    users.value = page.items ?? [];
  } catch (err) {
    error.value = t("common.errorLoadUsers");
  } finally {
    loading.value = false;
  }
}

onMounted(load);
</script>
