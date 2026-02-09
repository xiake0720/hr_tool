<template>
  <section>
    <h2 class="page-title">{{ t("common.roles") }} & {{ t("common.permissionsCount") }}</h2>
    <p class="page-subtitle">{{ t("common.rolesSubtitle") }}</p>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: var(--space-lg);">
      <div class="helper">
        {{ t("common.rolesCount") }}: {{ roles.length }} · {{ t("common.permissionsCount") }}: {{ permissions.length }}
      </div>
      <UiButton size="sm" type="button" @click="showForm = true">{{ t("common.newRole") }}</UiButton>
    </div>
    <AppLoading v-if="loading" />
    <AppError v-else-if="error" :message="error" :on-retry="load" />
    <div v-else style="display: grid; gap: var(--space-xl);">
      <div>
        <h3 style="margin: 0 0 8px;">{{ t("common.roles") }}</h3>
        <AppEmpty v-if="roles.length === 0" :message="t('common.noRoles')" />
        <AppTable v-else :columns="roleColumns" :rows="roles" />
      </div>
      <div>
        <h3 style="margin: 0 0 8px;">{{ t("common.permissionsCount") }}</h3>
        <AppEmpty v-if="permissions.length === 0" :message="t('common.noPermissions')" />
        <AppTable v-else :columns="permissionColumns" :rows="permissions" />
      </div>
    </div>
    <AppForm :open="showForm" :title="t('common.formTitleRole')" :on-close="() => (showForm = false)">
      <p class="helper">{{ t("common.formPlaceholder") }}</p>
    </AppForm>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useI18n } from "vue-i18n";
import AppForm from "../components/AppForm.vue";
import UiButton from "../components/ui/Button.vue";
import AppLoading from "../components/AppLoading.vue";
import AppError from "../components/AppError.vue";
import AppEmpty from "../components/AppEmpty.vue";
import AppTable from "../components/AppTable.vue";
import { listRoles, listPermissions, type Role, type Permission } from "../api/iam";

const roles = ref<Role[]>([]);
const permissions = ref<Permission[]>([]);
const showForm = ref(false);
const loading = ref(false);
const error = ref<string | null>(null);
const { t } = useI18n();

const roleColumns = computed(() => [
  { key: "id", label: t("common.id"), width: "80px" },
  { key: "code", label: t("common.code") },
  { key: "name", label: t("common.name") }
]);

const permissionColumns = computed(() => [
  { key: "id", label: t("common.id"), width: "80px" },
  { key: "action", label: t("common.action") },
  { key: "name", label: t("common.name") }
]);

async function load(): Promise<void> {
  loading.value = true;
  error.value = null;
  try {
    const [rolePage, permissionPage] = await Promise.all([listRoles(1, 30), listPermissions(1, 30)]);
    roles.value = rolePage.items ?? [];
    permissions.value = permissionPage.items ?? [];
  } catch (err) {
    error.value = t("common.errorLoadRoles");
  } finally {
    loading.value = false;
  }
}

onMounted(load);
</script>
