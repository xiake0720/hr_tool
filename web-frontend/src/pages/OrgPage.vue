<template>
  <section>
    <h2 class="page-title">{{ t("common.org") }}</h2>
    <p class="page-subtitle">{{ t("common.orgSubtitle") }}</p>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: var(--space-lg);">
      <div class="helper">{{ t("common.orgNodes") }}: {{ flatNodes.length }}</div>
      <UiButton size="sm" type="button" @click="showForm = true">{{ t("common.newNode") }}</UiButton>
    </div>
    <AppLoading v-if="loading" />
    <AppError v-else-if="error" :message="error" :on-retry="load" />
    <AppEmpty v-else-if="nodes.length === 0" :message="t('common.noOrg')" />
    <div v-else class="page-card">
      <div v-for="node in flatNodes" :key="node.id" style="padding: 6px 0; border-bottom: 1px solid var(--border);">
        <span :style="{ paddingLeft: `${node.depth * 16}px` }">
          {{ node.name }}
          <span class="helper" v-if="node.type">({{ node.type }})</span>
        </span>
      </div>
    </div>
    <AppForm :open="showForm" :title="t('common.formTitleOrg')" :on-close="() => (showForm = false)">
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
import { getOrgTree, type OrgNode } from "../api/iam";

const nodes = ref<OrgNode[]>([]);
const showForm = ref(false);
const loading = ref(false);
const error = ref<string | null>(null);
const { t } = useI18n();

interface FlatNode extends OrgNode {
  depth: number;
}

const flatNodes = computed<FlatNode[]>(() => {
  const result: FlatNode[] = [];
  const walk = (items: OrgNode[], depth: number) => {
    items.forEach((item) => {
      result.push({ ...item, depth });
      if (item.children && item.children.length > 0) {
        walk(item.children, depth + 1);
      }
    });
  };
  walk(nodes.value, 0);
  return result;
});

async function load(): Promise<void> {
  loading.value = true;
  error.value = null;
  try {
    nodes.value = await getOrgTree();
  } catch (err) {
    error.value = t("common.errorLoadOrg");
  } finally {
    loading.value = false;
  }
}

onMounted(load);
</script>
