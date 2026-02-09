<template>
  <div class="ui-task-center" :class="{ 'ui-task-center--open': !collapsed }">
    <button v-if="collapsed" class="ui-task-center__pill" type="button" @click="collapsed = false">
      任务中心
      <span v-if="runningCount > 0" class="ui-task-center__count">{{ runningCount }}</span>
    </button>

    <div v-else class="ui-task-center__panel">
      <div class="ui-task-center__header">
        <div>
          <div class="ui-task-center__title">任务中心</div>
          <div class="ui-task-center__subtitle">后台任务进度与结果</div>
        </div>
        <div class="ui-task-center__actions">
          <UiButton size="sm" variant="secondary" @click="refresh">刷新</UiButton>
          <UiButton size="sm" variant="ghost" @click="collapsed = true">收起</UiButton>
        </div>
      </div>

      <div class="ui-task-center__list">
        <div v-for="task in tasks" :key="task.id" class="ui-task-center__item">
          <div>
            <div class="ui-task-center__name">{{ task.name }}</div>
            <div class="ui-task-center__meta">{{ task.updatedAt }}</div>
          </div>
          <StatusPill :status="task.status" />
          <UiButton size="sm" variant="ghost" @click="openDetail(task)">查看</UiButton>
        </div>
      </div>
    </div>
  </div>

  <UiModal :open="detailOpen" title="任务详情" @close="detailOpen = false">
    <p class="muted">任务名称：{{ activeTask?.name }}</p>
    <p class="muted">状态：{{ activeTask?.status }}</p>
    <p class="muted">更新时间：{{ activeTask?.updatedAt }}</p>
  </UiModal>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import UiButton from "../ui/Button.vue";
import UiModal from "../ui/Modal.vue";
import StatusPill from "./StatusPill.vue";
import type { StatusTone } from "../../types/ui";

interface TaskItem {
  id: string;
  name: string;
  status: StatusTone;
  updatedAt: string;
}

const collapsed = ref(true);
const tasks = ref<TaskItem[]>([
  { id: "t1", name: "解析简历批次 02", status: "active", updatedAt: "10:12" },
  { id: "t2", name: "评估候选人 04", status: "pending", updatedAt: "09:48" },
  { id: "t3", name: "导出报表 Q2", status: "success", updatedAt: "昨天 17:20" }
]);

const detailOpen = ref(false);
const activeTask = ref<TaskItem | null>(null);

const runningCount = computed(
  () => tasks.value.filter((task) => task.status === "active" || task.status === "pending").length
);

function refresh(): void {
  tasks.value = [...tasks.value];
}

function openDetail(task: TaskItem): void {
  activeTask.value = task;
  detailOpen.value = true;
}
</script>

<style scoped>
.ui-task-center {
  position: fixed;
  right: var(--space-4);
  bottom: var(--space-4);
  z-index: var(--z-dropdown);
}

.ui-task-center__pill {
  display: inline-flex;
  align-items: center;
  gap: var(--space-2);
  padding: var(--space-2) var(--space-3);
  border-radius: 999px;
  border: 1px solid var(--border);
  background: var(--bg-alt);
  color: var(--text);
  box-shadow: var(--shadow-2);
  cursor: pointer;
  transition: transform var(--dur-2) var(--ease-out), box-shadow var(--dur-2) var(--ease-out);
}

.ui-task-center__pill:hover {
  transform: translateY(-1px);
  box-shadow: var(--shadow-3);
}

.ui-task-center__count {
  background: var(--brand-500);
  color: var(--text-on-brand);
  border-radius: 999px;
  padding: 0 var(--space-2);
  font-size: var(--text-sm);
}

.ui-task-center__panel {
  width: 320px;
  background: var(--bg-alt);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-3);
  padding: var(--space-3);
  display: grid;
  gap: var(--space-3);
}

.ui-task-center__header {
  display: flex;
  justify-content: space-between;
  gap: var(--space-2);
}

.ui-task-center__title {
  font-weight: 600;
}

.ui-task-center__subtitle {
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-task-center__actions {
  display: flex;
  gap: var(--space-1);
}

.ui-task-center__list {
  display: grid;
  gap: var(--space-2);
  max-height: 240px;
  overflow: auto;
}

.ui-task-center__item {
  display: grid;
  gap: var(--space-1);
  padding: var(--space-2);
  border-radius: var(--radius-md);
  border: 1px solid var(--border);
  background: var(--bg-alt);
}

.ui-task-center__name {
  font-weight: 600;
}

.ui-task-center__meta {
  font-size: var(--text-sm);
  color: var(--text-muted);
}
</style>
