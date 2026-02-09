<template>
  <div class="page-card">
    <table class="table">
      <thead>
        <tr>
          <th v-for="column in columns" :key="column.key" :style="columnStyle(column)">
            {{ column.label }}
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in rows" :key="rowKey(row)">
          <td v-for="column in columns" :key="column.key" :style="columnStyle(column)">
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
  align?: "left" | "right" | "center";
  width?: string;
}

interface Props {
  columns: Column[];
  rows: Record<string, unknown>[];
  rowKey?: (row: Record<string, unknown>) => string | number;
}

const props = withDefaults(defineProps<Props>(), {
  rowKey: (row) => String(row.id ?? row.key ?? JSON.stringify(row))
});

const columnStyle = (column: Column) => ({
  textAlign: column.align ?? "left",
  width: column.width
});
</script>
