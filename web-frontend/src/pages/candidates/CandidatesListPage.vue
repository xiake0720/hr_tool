<template>
  <section class="ui-candidates page">
    <PageHeader title="候选人" subtitle="管理候选人状态与核心进展。">
      <template #actions>
        <UiButton @click="uploaderOpen = true">上传简历</UiButton>
      </template>
    </PageHeader>

    <FilterBar />

    <DataTablePro
      :columns="columns"
      :rows="rows"
      :page="page"
      :pageSize="pageSize"
      :total="total"
      @rowClick="openDetail"
      @pageChange="page = $event"
      @emptyAction="uploaderOpen = true"
    >
      <template #cell-status="{ row }">
        <StatusPill :status="row.status" />
      </template>
      <template #cell-actions="{ row }">
        <div class="ui-candidates__actions">
          <UiButton size="sm" variant="ghost" @click.stop="openDetail(row)">查看</UiButton>
          <UiButton size="sm" variant="ghost">评估</UiButton>
          <UiButton size="sm" variant="ghost">推进阶段</UiButton>
        </div>
      </template>
      <template #cell-name="{ row }">
        <div class="ui-candidates__name">{{ row.name }}</div>
        <div class="muted ui-candidates__meta">{{ row.email }}</div>
      </template>
    </DataTablePro>

    <EntityDetailDrawer :open="drawerOpen" :entity="activeRow" @close="drawerOpen = false" />

    <TaskCenter />

    <UiModal :open="uploaderOpen" title="上传简历" @close="uploaderOpen = false">
      <Uploader @uploadSuccess="notifyUpload" />
    </UiModal>
  </section>
</template>

<script setup lang="ts">
import { ref } from "vue";
import UiButton from "../../components/ui/Button.vue";
import UiModal from "../../components/ui/Modal.vue";
import PageHeader from "../../components/common/PageHeader.vue";
import FilterBar from "../../components/common/FilterBar.vue";
import DataTablePro from "../../components/common/DataTablePro.vue";
import EntityDetailDrawer from "../../components/common/EntityDetailDrawer.vue";
import StatusPill from "../../components/common/StatusPill.vue";
import Uploader from "../../components/common/Uploader.vue";
import TaskCenter from "../../components/common/TaskCenter.vue";
import type { Candidate, TableColumn } from "../../types/ui";

const columns: TableColumn<Candidate>[] = [
  { key: "name", label: "候选人" },
  { key: "role", label: "岗位" },
  { key: "stage", label: "阶段" },
  { key: "status", label: "状态", width: "120px" },
  { key: "updatedAt", label: "更新时间", width: "140px" },
  { key: "actions", label: "操作", width: "220px" }
];

const rows = ref<Candidate[]>([
  {
    id: 1,
    name: "陈可安",
    email: "kean.chen@hire.com",
    role: "高级前端工程师",
    stage: "面试",
    status: "active",
    updatedAt: "2026-02-08"
  },
  {
    id: 2,
    name: "徐亦航",
    email: "yihang.xu@hire.com",
    role: "数据产品经理",
    stage: "初筛",
    status: "pending",
    updatedAt: "2026-02-07"
  },
  {
    id: 3,
    name: "刘铭卓",
    email: "mingzhuo.liu@hire.com",
    role: "平台架构师",
    stage: "终面",
    status: "failed",
    updatedAt: "2026-02-06"
  },
  {
    id: 4,
    name: "宋雅雯",
    email: "yawen.song@hire.com",
    role: "交互设计师",
    stage: "面试",
    status: "active",
    updatedAt: "2026-02-06"
  }
]);

const page = ref(1);
const pageSize = ref(10);
const total = ref(42);

const drawerOpen = ref(false);
const activeRow = ref<Candidate | null>(null);
const uploaderOpen = ref(false);

function openDetail(row: Candidate): void {
  activeRow.value = row;
  drawerOpen.value = true;
}

function notifyUpload(): void {
  return;
}
</script>

<style scoped>
.ui-candidates {
  display: grid;
  gap: var(--space-3);
}


.ui-candidates__name {
  font-weight: 600;
}

.ui-candidates__meta {
  font-size: var(--text-sm);
}

.ui-candidates__actions {
  display: inline-flex;
  gap: var(--space-1);
}
</style>
