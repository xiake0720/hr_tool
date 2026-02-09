<template>
  <div v-if="mode === 'hidden' && !hasPermission" class="ui-permission-hidden"></div>
  <div v-else :class="{ 'ui-permission-disabled': mode === 'disabled' && !hasPermission }">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";

interface Props {
  permission?: string;
  permissions?: string[];
  mode?: "hidden" | "disabled";
}

const props = withDefaults(defineProps<Props>(), {
  permission: "",
  permissions: () => [],
  mode: "hidden"
});

function getCurrentPermissions(): string[] {
  const raw = localStorage.getItem("permissions");
  if (!raw) return [];
  try {
    return JSON.parse(raw) as string[];
  } catch {
    return [];
  }
}

const hasPermission = computed(() => {
  const current = getCurrentPermissions();
  if (props.permission) {
    return current.includes(props.permission);
  }
  if (props.permissions.length > 0) {
    return props.permissions.every((perm) => current.includes(perm));
  }
  return true;
});
</script>

<style scoped>
.ui-permission-hidden {
  display: none;
}

.ui-permission-disabled {
  pointer-events: none;
  opacity: 0.5;
}
</style>
