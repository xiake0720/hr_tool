<template>
  <div class="ui-progress" role="progressbar" :aria-valuenow="value" :aria-valuemin="0" :aria-valuemax="100">
    <div class="ui-progress__track">
      <div class="ui-progress__bar" :style="{ width: `${safeValue}%` }"></div>
    </div>
    <span v-if="showLabel" class="ui-progress__label">{{ safeValue }}%</span>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";

interface Props {
  value: number;
  showLabel?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  showLabel: false
});

const safeValue = computed(() => Math.min(100, Math.max(0, props.value)));
</script>

<style scoped>
.ui-progress {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

.ui-progress__track {
  flex: 1;
  height: 8px;
  border-radius: 999px;
  background: var(--bg-muted);
  overflow: hidden;
}

.ui-progress__bar {
  height: 100%;
  background: var(--brand-500);
  border-radius: 999px;
  transition: width var(--dur-3) var(--ease-out);
}

.ui-progress__label {
  font-size: var(--text-sm);
  color: var(--text-muted);
}
</style>
