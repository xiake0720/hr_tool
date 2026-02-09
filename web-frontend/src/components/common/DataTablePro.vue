<template>
  <section class="ui-table-pro">
    <div class="ui-table-pro__toolbar">
      <div class="ui-table-pro__left">
        <UiCheckbox v-model="selectAll" :disabled="rows.length === 0">全选</UiCheckbox>
        <div v-if="selectedIds.length > 0" class="ui-table-pro__bulk">
          已选 {{ selectedIds.length }} 项
          <UiButton size="sm" variant="secondary">批量操作</UiButton>
        </div>
      </div>
      <div class="ui-table-pro__right">
        <UiDropdownMenu :items="columnItems" @select="toggleColumn">
          <template #trigger>
            <UiButton size="sm" variant="ghost">列设置</UiButton>
          </template>
        </UiDropdownMenu>
      </div>
    </div>

    <UiTable :columns="columnsWithSelection" :rows="rows" :loading="loading" @rowClick="emitRow">
      <template #cell-__select="{ row }">
        <UiCheckbox
          :modelValue="selectedIds.includes(rowKey(row))"
          @update:modelValue="(value) => toggleRow(row, value)"
          @click.stop
        />
      </template>
      <template v-for="col in visibleColumns" #[`cell-${String(col.key)}`]="slotProps">
        <slot :name="`cell-${String(col.key)}`" :row="slotProps.row" />
      </template>
      <template #empty>
        <EmptyState title="暂无候选人" description="上传简历后开始评估流程。">
          <template #action>
            <UiButton size="sm" variant="secondary" @click="$emit('emptyAction')">上传简历</UiButton>
          </template>
        </EmptyState>
      </template>
    </UiTable>

    <div class="ui-table-pro__footer">
      <div class="ui-table-pro__summary">
        共 {{ total }} 条
        <span v-if="selectedIds.length > 0"> · 已选 {{ selectedIds.length }} 条</span>
      </div>
      <UiPagination :page="page" :pageSize="pageSize" :total="total" @update:page="emitPage" />
    </div>
  </section>
</template>

<script setup lang="ts">
import { computed, ref, watch } from "vue";
import UiTable from "../ui/Table.vue";
import UiPagination from "../ui/Pagination.vue";
import UiButton from "../ui/Button.vue";
import UiCheckbox from "../ui/Checkbox.vue";
import UiDropdownMenu from "../ui/DropdownMenu.vue";
import EmptyState from "../ui/EmptyState.vue";
import type { TableColumn } from "../../types/ui";

interface Props {
  columns: TableColumn<Record<string, any>>[];
  rows: Record<string, any>[];
  loading?: boolean;
  page: number;
  pageSize: number;
  total: number;
  storageKey?: string;
  rowKey?: (row: Record<string, any>) => string | number;
}

const props = withDefaults(defineProps<Props>(), {
  loading: false,
  storageKey: "ui-table-pro-columns",
  rowKey: (row) => String(row.id ?? row.key ?? JSON.stringify(row))
});

const emit = defineEmits<{
  (event: "rowClick", row: Record<string, any>): void;
  (event: "selectionChange", ids: Array<string | number>): void;
  (event: "pageChange", page: number): void;
  (event: "emptyAction"): void;
}>();

const selectedIds = ref<Array<string | number>>([]);
const visibleColumns = computed(() => props.columns.filter((col) => !col.hidden));
const columnsWithSelection = computed(() => [
  { key: "__select", label: "", width: "48px" },
  ...visibleColumns.value
]);

const columnItems = computed(() =>
  props.columns.map((col) => ({
    label: `${col.label} ${col.hidden ? "(隐藏)" : ""}`,
    value: String(col.key)
  }))
);

const selectAll = computed({
  get: () => props.rows.length > 0 && selectedIds.value.length === props.rows.length,
  set: (value: boolean) => {
    selectedIds.value = value ? props.rows.map((row) => props.rowKey(row)) : [];
  }
});

watch(
  () => selectedIds.value,
  (value) => emit("selectionChange", value)
);

watch(
  () => props.columns,
  (cols) => {
    const stored = localStorage.getItem(props.storageKey);
    if (!stored) return;
    try {
      const hiddenKeys = JSON.parse(stored) as string[];
      cols.forEach((col) => {
        col.hidden = hiddenKeys.includes(String(col.key));
      });
    } catch {
      return;
    }
  },
  { immediate: true }
);


function toggleColumn(key: string): void {
  const target = props.columns.find((col) => String(col.key) === key);
  if (!target) return;
  target.hidden = !target.hidden;
  const hiddenKeys = props.columns.filter((col) => col.hidden).map((col) => String(col.key));
  localStorage.setItem(props.storageKey, JSON.stringify(hiddenKeys));
}

function emitPage(page: number): void {
  emit("pageChange", page);
}

function rowKey(row: Record<string, any>): string | number {
  return props.rowKey(row);
}

function toggleRow(row: Record<string, any>, value: boolean): void {
  const key = props.rowKey(row);
  if (value) {
    selectedIds.value = Array.from(new Set([...selectedIds.value, key]));
  } else {
    selectedIds.value = selectedIds.value.filter((id) => id !== key);
  }
}

function emitRow(row: Record<string, any>): void {
  emit("rowClick", row);
}
</script>

<style scoped>
.ui-table-pro {
  display: grid;
  gap: var(--space-3);
}

.ui-table-pro__toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.ui-table-pro__left {
  display: flex;
  align-items: center;
  gap: var(--space-3);
}

.ui-table-pro__bulk {
  display: inline-flex;
  align-items: center;
  gap: var(--space-2);
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-table-pro__right {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}


.ui-table-pro__footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-3);
}

.ui-table-pro__summary {
  font-size: var(--text-sm);
  color: var(--text-muted);
}
</style>
