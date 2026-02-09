<template>
  <div class="ui-table">
    <table>
      <thead>
        <tr>
          <th v-for="column in columns" :key="column.key" :style="{ width: column.width || 'auto' }">
            {{ column.label }}
          </th>
        </tr>
      </thead>
      <tbody v-if="loading">
        <tr v-for="row in skeletonRows" :key="row">
          <td v-for="column in columns" :key="column.key">
            <div class="ui-table__skeleton"></div>
          </td>
        </tr>
      </tbody>
      <tbody v-else-if="rows.length === 0">
        <tr>
          <td :colspan="columns.length">
            <slot name="empty">
              <div class="ui-table__empty">No data</div>
            </slot>
          </td>
        </tr>
      </tbody>
      <tbody v-else>
        <tr v-for="row in rows" :key="rowKey(row)" @click="emitRow(row)">
          <td v-for="column in columns" :key="column.key">
            <slot :name="`cell-${column.key}`" :row="row">
              {{ row[column.key] }}
            </slot>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
interface Column {
  key: string;
  label: string;
  width?: string;
}

interface Props {
  columns: Column[];
  rows: Record<string, any>[];
  loading?: boolean;
  rowKey?: (row: Record<string, any>) => string | number;
  skeletonCount?: number;
}

const props = withDefaults(defineProps<Props>(), {
  loading: false,
  skeletonCount: 5,
  rowKey: (row) => String(row.id ?? row.key ?? JSON.stringify(row))
});

const emit = defineEmits<{ (event: "rowClick", row: Record<string, any>): void }>();

const skeletonRows = Array.from({ length: props.skeletonCount }, (_, index) => index);

function emitRow(row: Record<string, any>): void {
  emit("rowClick", row);
}
</script>

<style scoped>
.ui-table {
  width: 100%;
  background: var(--bg-alt);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  overflow: hidden;
  box-shadow: var(--shadow-1);
}

.ui-table table {
  width: 100%;
  border-collapse: collapse;
  font-size: var(--text-md);
}

.ui-table th,
.ui-table td {
  padding: var(--space-3) var(--space-4);
  text-align: left;
  border-bottom: 1px solid var(--border);
}

.ui-table tbody tr {
  transition: background var(--dur-2) var(--ease-out);
}

.ui-table tbody tr:hover {
  background: var(--brand-50);
  cursor: pointer;
}

.ui-table th {
  background: var(--bg-muted);
  color: var(--text-muted);
  font-weight: 600;
}

.ui-table tr:last-child td {
  border-bottom: none;
}

.ui-table__skeleton {
  height: 12px;
  border-radius: 999px;
  background: var(--bg-muted);
}

.ui-table__empty {
  padding: var(--space-4);
  color: var(--text-muted);
  text-align: center;
}
</style>

<!--
Demo:
<UiTable :columns="columns" :rows="rows" :loading="loading">
  <template #cell-status="{ row }">
    <UiTag :tone="row.statusTone">{{ row.status }}</UiTag>
  </template>
</UiTable>
-->
