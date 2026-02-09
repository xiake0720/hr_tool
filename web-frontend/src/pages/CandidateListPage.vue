<template>
  <section class="candidates">
    <header class="candidates__header">
      <div>
        <h2 class="candidates__title">候选人</h2>
        <p class="candidates__subtitle">集中查看候选人流转状态与关键画像。</p>
      </div>
      <UiButton variant="primary">新建候选人</UiButton>
    </header>

    <div class="candidates__filters">
      <UiInput v-model="filters.keyword" label="关键字" placeholder="姓名 / 邮箱 / 职位" />
      <UiSelect v-model="filters.status" label="状态" :options="statusOptions" placeholder="全部状态" />
      <UiSelect v-model="filters.stage" label="阶段" :options="stageOptions" placeholder="全部阶段" />
      <UiButton variant="secondary" @click="resetFilters">重置</UiButton>
    </div>

    <div class="candidates__table">
      <table class="table">
        <thead>
          <tr>
            <th>候选人</th>
            <th>岗位</th>
            <th>阶段</th>
            <th>状态</th>
            <th>更新时间</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="candidate in pagedCandidates" :key="candidate.id">
            <td>
              <div class="cell-title">{{ candidate.name }}</div>
              <div class="cell-meta">{{ candidate.email }}</div>
            </td>
            <td>{{ candidate.role }}</td>
            <td>{{ candidate.stage }}</td>
            <td>
              <UiTag :tone="statusTone(candidate.status)">{{ candidate.status }}</UiTag>
            </td>
            <td>{{ candidate.updatedAt }}</td>
            <td class="align-right">
              <UiButton size="sm" variant="ghost" @click="openDetail(candidate)">查看</UiButton>
            </td>
          </tr>
          <tr v-if="pagedCandidates.length === 0">
            <td colspan="6">
              <div class="empty">未找到匹配的候选人。</div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="candidates__pagination">
      <div class="helper">共 {{ filteredCandidates.length }} 人</div>
      <div class="pagination-controls">
        <UiButton size="sm" variant="secondary" :disabled="page === 1" @click="page--">上一页</UiButton>
        <div class="helper">第 {{ page }} / {{ totalPages }} 页</div>
        <UiButton size="sm" variant="secondary" :disabled="page === totalPages" @click="page++">下一页</UiButton>
      </div>
    </div>

    <UiDrawer
      :open="drawerOpen"
      title="候选人详情"
      description="记录关键标签与推进状态。"
      @close="closeDetail"
    >
      <div v-if="activeCandidate" class="drawer-content">
        <div class="drawer-block">
          <div class="drawer-label">姓名</div>
          <div class="drawer-value">{{ activeCandidate.name }}</div>
        </div>
        <div class="drawer-block">
          <div class="drawer-label">邮箱</div>
          <div class="drawer-value">{{ activeCandidate.email }}</div>
        </div>
        <div class="drawer-block">
          <div class="drawer-label">岗位</div>
          <div class="drawer-value">{{ activeCandidate.role }}</div>
        </div>
        <div class="drawer-block">
          <div class="drawer-label">阶段</div>
          <div class="drawer-value">{{ activeCandidate.stage }}</div>
        </div>
        <div class="drawer-block">
          <div class="drawer-label">状态</div>
          <UiTag :tone="statusTone(activeCandidate.status)">{{ activeCandidate.status }}</UiTag>
        </div>
        <div class="drawer-block">
          <div class="drawer-label">最近更新</div>
          <div class="drawer-value">{{ activeCandidate.updatedAt }}</div>
        </div>
        <div class="drawer-actions">
          <UiButton variant="secondary">备注</UiButton>
          <UiButton variant="primary">推进流程</UiButton>
        </div>
      </div>
    </UiDrawer>
  </section>
</template>

<script setup lang="ts">
import { computed, ref, watch } from "vue";
import UiButton from "../components/ui/Button.vue";
import UiInput from "../components/ui/Input.vue";
import UiSelect from "../components/ui/Select.vue";
import UiTag from "../components/ui/Tag.vue";
import UiDrawer from "../components/ui/Drawer.vue";

interface Candidate {
  id: number;
  name: string;
  email: string;
  role: string;
  stage: string;
  status: string;
  updatedAt: string;
}

const candidates = ref<Candidate[]>([
  {
    id: 1,
    name: "陈可安",
    email: "kean.chen@hire.com",
    role: "高级前端工程师",
    stage: "技术面",
    status: "跟进中",
    updatedAt: "2026-02-08"
  },
  {
    id: 2,
    name: "徐亦航",
    email: "yihang.xu@hire.com",
    role: "数据产品经理",
    stage: "初筛",
    status: "待沟通",
    updatedAt: "2026-02-07"
  },
  {
    id: 3,
    name: "刘铭卓",
    email: "mingzhuo.liu@hire.com",
    role: "平台架构师",
    stage: "终面",
    status: "高优先级",
    updatedAt: "2026-02-06"
  },
  {
    id: 4,
    name: "宋雅雯",
    email: "yawen.song@hire.com",
    role: "交互设计师",
    stage: "作品集评审",
    status: "跟进中",
    updatedAt: "2026-02-06"
  },
  {
    id: 5,
    name: "白宇晨",
    email: "yuchen.bai@hire.com",
    role: "Java 工程师",
    stage: "Offer",
    status: "已通过",
    updatedAt: "2026-02-05"
  },
  {
    id: 6,
    name: "唐诗韵",
    email: "shiyun.tang@hire.com",
    role: "SRE 工程师",
    stage: "复试",
    status: "跟进中",
    updatedAt: "2026-02-05"
  },
  {
    id: 7,
    name: "章泽坤",
    email: "zekun.zhang@hire.com",
    role: "算法工程师",
    stage: "初筛",
    status: "待沟通",
    updatedAt: "2026-02-04"
  }
]);

const filters = ref({
  keyword: "",
  status: "",
  stage: ""
});

const statusOptions = [
  { value: "待沟通", label: "待沟通" },
  { value: "跟进中", label: "跟进中" },
  { value: "高优先级", label: "高优先级" },
  { value: "已通过", label: "已通过" }
];

const stageOptions = [
  { value: "初筛", label: "初筛" },
  { value: "作品集评审", label: "作品集评审" },
  { value: "技术面", label: "技术面" },
  { value: "复试", label: "复试" },
  { value: "终面", label: "终面" },
  { value: "Offer", label: "Offer" }
];

const filteredCandidates = computed(() => {
  const keyword = filters.value.keyword.trim();
  return candidates.value.filter((item) => {
    const matchKeyword =
      !keyword ||
      item.name.includes(keyword) ||
      item.email.includes(keyword) ||
      item.role.includes(keyword);
    const matchStatus = !filters.value.status || item.status === filters.value.status;
    const matchStage = !filters.value.stage || item.stage === filters.value.stage;
    return matchKeyword && matchStatus && matchStage;
  });
});

const page = ref(1);
const pageSize = 5;

const totalPages = computed(() => Math.max(1, Math.ceil(filteredCandidates.value.length / pageSize)));

const pagedCandidates = computed(() => {
  const start = (page.value - 1) * pageSize;
  return filteredCandidates.value.slice(start, start + pageSize);
});

watch(
  () => ({ ...filters.value }),
  () => {
    page.value = 1;
  }
);

function resetFilters(): void {
  filters.value = { keyword: "", status: "", stage: "" };
}

const drawerOpen = ref(false);
const activeCandidate = ref<Candidate | null>(null);

function openDetail(candidate: Candidate): void {
  activeCandidate.value = candidate;
  drawerOpen.value = true;
}

function closeDetail(): void {
  drawerOpen.value = false;
  activeCandidate.value = null;
}

function statusTone(status: string): "neutral" | "success" | "warning" | "danger" | "info" {
  if (status === "已通过") return "success";
  if (status === "高优先级") return "danger";
  if (status === "待沟通") return "warning";
  return "info";
}
</script>

<style scoped>
.candidates {
  display: grid;
  gap: var(--space-5);
}

.candidates__header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: var(--space-4);
}

.candidates__title {
  margin: 0;
  font-family: var(--font-display);
  font-size: 24px;
}

.candidates__subtitle {
  margin: var(--space-1) 0 0;
  color: var(--muted);
}

.candidates__filters {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr)) auto;
  gap: var(--space-3);
  padding: var(--space-4);
  background: var(--surface);
  border: 1px solid rgba(148, 163, 184, 0.2);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-sm);
}

:deep(.candidates__filters .ui-field) {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

:deep(.candidates__filters .ui-label) {
  width: 48px;
  margin: 0;
  font-size: 12px;
  color: var(--muted);
}

:deep(.candidates__filters .ui-input),
:deep(.candidates__filters .ui-select) {
  flex: 1;
}

.candidates__table {
  background: var(--surface);
  border-radius: var(--radius-lg);
  border: 1px solid rgba(148, 163, 184, 0.2);
  box-shadow: var(--shadow-sm);
  overflow: hidden;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: var(--space-3) var(--space-4);
  text-align: left;
  border-bottom: 1px solid var(--border);
  font-size: 14px;
}

.table th {
  background: var(--surface-alt);
  color: var(--muted);
  font-weight: 600;
}

.table tr:last-child td {
  border-bottom: none;
}

.cell-title {
  font-weight: 600;
  color: var(--ink);
}

.cell-meta {
  font-size: 12px;
  color: var(--muted);
  margin-top: 2px;
}

.align-right {
  text-align: right;
}

.empty {
  padding: var(--space-5);
  text-align: center;
  color: var(--muted);
}

.candidates__pagination {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-3);
}

.pagination-controls {
  display: flex;
  align-items: center;
  gap: var(--space-3);
}

.drawer-content {
  display: grid;
  gap: var(--space-3);
}

.drawer-block {
  display: grid;
  gap: var(--space-1);
  padding-bottom: var(--space-2);
  border-bottom: 1px solid var(--border);
}

.drawer-label {
  font-size: 12px;
  color: var(--muted);
}

.drawer-value {
  font-weight: 600;
}

.drawer-actions {
  display: flex;
  gap: var(--space-2);
  margin-top: var(--space-3);
}

@media (max-width: 960px) {
  .candidates__filters {
    grid-template-columns: 1fr;
  }

  .candidates__header {
    flex-direction: column;
  }
}
</style>
