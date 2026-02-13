<template>
  <UiDrawer :open="open" title="详情" @close="emitClose">
    <div class="ui-entity">
      <section class="ui-entity__profile">
        <div class="ui-entity__avatar">{{ initials }}</div>
        <div class="ui-entity__profile-main">
          <div class="ui-entity__name">{{ entity?.name || "-" }}</div>
          <StatusPill :status="entity?.status || 'pending'" />
        </div>
      </section>

      <section class="ui-entity__meta">
        <div class="ui-entity__meta-item">
          <div class="ui-entity__label">邮箱</div>
          <div class="ui-entity__value">{{ entity?.email || "-" }}</div>
        </div>
        <div class="ui-entity__meta-item">
          <div class="ui-entity__label">岗位</div>
          <div class="ui-entity__value">{{ entity?.role || "-" }}</div>
        </div>
        <div class="ui-entity__meta-item">
          <div class="ui-entity__label">阶段</div>
          <div class="ui-entity__value">{{ entity?.stage || "-" }}</div>
        </div>
        <div class="ui-entity__meta-item">
          <div class="ui-entity__label">更新时间</div>
          <div class="ui-entity__value">{{ entity?.updatedAt || "-" }}</div>
        </div>
      </section>

      <nav class="ui-entity__tabs" aria-label="详情标签">
        <button
          v-for="tab in tabs"
          :key="tab"
          class="ui-entity__tab"
          :class="{ 'ui-entity__tab--active': activeTab === tab }"
          type="button"
          @click="activeTab = tab"
        >
          {{ tab }}
        </button>
      </nav>

      <section class="ui-entity__content">
        <div class="ui-entity__panel" v-if="activeTab === '基本信息'">
          <p class="muted">基础信息与沟通记录占位。</p>
        </div>
        <div class="ui-entity__panel" v-else-if="activeTab === '评估'">
          <p class="muted">评估维度与反馈占位。</p>
        </div>
        <div class="ui-entity__panel" v-else>
          <p class="muted">日志流水占位。</p>
        </div>
      </section>
    </div>

    <template #footer>
      <div class="ui-entity__footer">
        <UiButton variant="secondary">编辑</UiButton>
        <UiButton variant="ghost">归档</UiButton>
        <UiButton variant="danger" @click="confirmOpen = true">删除</UiButton>
      </div>
    </template>
  </UiDrawer>

  <ConfirmDialog
    :open="confirmOpen"
    title="确认删除"
    description="删除后不可恢复，请确认。"
    @confirm="handleDelete"
    @cancel="confirmOpen = false"
  />
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import UiDrawer from "../ui/Drawer.vue";
import UiButton from "../ui/Button.vue";
import ConfirmDialog from "../ui/ConfirmDialog.vue";
import StatusPill from "./StatusPill.vue";
import type { Candidate } from "../../types/ui";

interface Props {
  open: boolean;
  entity?: Candidate | null;
}

const props = withDefaults(defineProps<Props>(), {
  entity: null
});

const emit = defineEmits<{ (event: "close"): void; (event: "delete", id: number | undefined): void }>();

const tabs = ["基本信息", "评估", "日志"];
const activeTab = ref("基本信息");
const confirmOpen = ref(false);

const initials = computed(() => {
  if (!props.entity?.name) return "-";
  return props.entity.name.slice(0, 2);
});

function emitClose(): void {
  emit("close");
}

function handleDelete(): void {
  confirmOpen.value = false;
  emit("delete", props.entity?.id);
}
</script>

<style scoped>
.ui-entity {
  display: grid;
  gap: var(--space-4);
  min-height: calc(100vh - 180px);
  grid-template-rows: auto auto auto minmax(0, 1fr);
}

.ui-entity__profile {
  display: flex;
  align-items: center;
  gap: var(--space-3);
}

.ui-entity__profile-main {
  min-width: 0;
  display: grid;
  gap: var(--space-1);
}

.ui-entity__avatar {
  width: 48px;
  height: 48px;
  border-radius: var(--radius-lg);
  background: var(--brand-50);
  color: var(--brand-600);
  display: grid;
  place-items: center;
  font-weight: 700;
}

.ui-entity__name {
  font-size: var(--text-lg);
  font-weight: 600;
}

.ui-entity__meta {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: var(--space-2) var(--space-4);
}

.ui-entity__meta-item {
  display: grid;
  grid-template-columns: 72px minmax(0, 1fr);
  align-items: center;
  column-gap: var(--space-2);
  row-gap: var(--space-1);
  padding-bottom: var(--space-1);
  border-bottom: 1px solid var(--border);
}

.ui-entity__label {
  color: var(--text-muted);
  font-size: var(--text-sm);
}

.ui-entity__value {
  font-weight: 600;
  min-width: 0;
  word-break: break-word;
}

.ui-entity__tabs {
  display: flex;
  gap: var(--space-2);
}

.ui-entity__tab {
  border: 1px solid var(--border);
  background: var(--bg-alt);
  color: var(--text-muted);
  padding: var(--space-1) var(--space-3);
  border-radius: var(--radius-md);
  cursor: pointer;
}

.ui-entity__tab--active {
  background: var(--brand-50);
  color: var(--brand-600);
  border-color: var(--brand-100);
}

.ui-entity__panel {
  padding: var(--space-3);
  border: 1px dashed var(--border);
  border-radius: var(--radius-md);
}

.ui-entity__content {
  min-height: 0;
  overflow: auto;
  padding-right: var(--space-1);
}

.ui-entity__footer {
  position: sticky;
  bottom: 0;
  display: flex;
  justify-content: flex-end;
  gap: var(--space-2);
  background: var(--bg-alt);
  border-top: 1px solid var(--border);
  padding-top: var(--space-3);
}

@media (max-width: 768px) {
  .ui-entity__meta {
    grid-template-columns: 1fr;
  }
}
</style>
