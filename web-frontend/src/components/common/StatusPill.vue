<template>
  <UiTag :tone="toneMap[status]">
    {{ labelMap[status] ?? status }}
  </UiTag>
</template>

<script setup lang="ts">
import UiTag from "../ui/Tag.vue";
import type { StatusTone } from "../../types/ui";

interface Props {
  status: StatusTone;
}

const props = defineProps<Props>();

const toneMap: Record<StatusTone, "neutral" | "success" | "warning" | "danger" | "info"> = {
  active: "success",
  inactive: "neutral",
  pending: "warning",
  failed: "danger",
  success: "success"
};

const labelMap: Record<StatusTone, string> = {
  active: "进行中",
  inactive: "已停用",
  pending: "待处理",
  failed: "失败",
  success: "已完成"
};
</script>
