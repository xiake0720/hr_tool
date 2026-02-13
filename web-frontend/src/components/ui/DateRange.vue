<template>
  <div
    class="ui-date-range ui-control"
    :class="[rootClass, { 'ui-date-range--disabled': disabled, 'ui-date-range--error': !!error }]"
    :style="rootStyle"
  >
    <label v-if="label" class="ui-date-range__label">{{ label }}</label>

    <Popover
      :open="open && !disabled"
      :minWidth="0"
      :matchTriggerWidth="true"
      :fullWidth="props.fullWidth === true"
      @update:open="setOpen"
    >
      <template #trigger>
        <div class="ui-date-range__control" role="button" :aria-expanded="open ? 'true' : 'false'">
          <span class="ui-date-range__value" :class="{ 'ui-date-range__value--placeholder': !displayText }">
            {{ displayText || placeholder }}
          </span>
          <span class="ui-date-range__chevron">v</span>
        </div>
      </template>

      <div class="ui-date-range__panel" @keydown="onKeydown">
        <div class="ui-date-range__fields">
          <div class="ui-date-range__field">
            <label class="ui-date-range__field-label">开始</label>
            <input class="ui-date-range__input" type="date" :value="modelValue.start" @input="onStart" />
          </div>
          <div class="ui-date-range__field">
            <label class="ui-date-range__field-label">结束</label>
            <input class="ui-date-range__input" type="date" :value="modelValue.end" @input="onEnd" />
          </div>
        </div>
      </div>
    </Popover>

    <p v-if="helperText" class="ui-date-range__help">{{ helperText }}</p>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import Popover from "./Popover.vue";

interface DateRangeValue {
  start: string;
  end: string;
}

interface Props {
  modelValue: DateRangeValue;
  label?: string;
  placeholder?: string;
  helperText?: string;
  error?: string;
  disabled?: boolean;
  width?: string;
  size?: "sm" | "md" | "lg";
  fullWidth?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  label: "",
  placeholder: "选择日期范围",
  helperText: "",
  error: "",
  disabled: false,
  width: "",
  size: "md",
  fullWidth: false
});

const emit = defineEmits<{ (event: "update:modelValue", value: DateRangeValue): void }>();
const open = ref(false);

const displayText = computed(() => {
  if (!props.modelValue.start && !props.modelValue.end) return "";
  return `${props.modelValue.start || "-"} ~ ${props.modelValue.end || "-"}`;
});

const rootClass = computed(() => ({
  "ui-control--sm": props.size === "sm",
  "ui-control--md": props.size === "md",
  "ui-control--lg": props.size === "lg",
  "ui-control--full": props.fullWidth === true
}));

const rootStyle = computed(() => {
  if (!props.width || props.fullWidth) {
    return {};
  }
  return { "--ui-control-w": props.width };
});

function setOpen(value: boolean): void {
  if (props.disabled) {
    open.value = false;
    return;
  }
  open.value = value;
}

function onStart(event: Event): void {
  const target = event.target as HTMLInputElement;
  emit("update:modelValue", { ...props.modelValue, start: target.value });
}

function onEnd(event: Event): void {
  const target = event.target as HTMLInputElement;
  emit("update:modelValue", { ...props.modelValue, end: target.value });
}

function onKeydown(event: KeyboardEvent): void {
  if (event.key === "Escape") {
    open.value = false;
  }
}
</script>

<style scoped>
.ui-date-range {
  display: grid;
  gap: var(--space-2);
}

.ui-date-range__label {
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-date-range__control {
  width: 100%;
  min-width: 0;
  height: var(--control-h);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  background: var(--bg-alt);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 var(--space-3);
  cursor: pointer;
  transition: border-color var(--dur-2) var(--ease-out), box-shadow var(--dur-2) var(--ease-out);
}

.ui-date-range__control:hover {
  border-color: var(--brand-500);
  box-shadow: var(--shadow-1);
}

:deep(.ui-popover__trigger:focus-visible) .ui-date-range__control {
  outline: 2px solid var(--brand-500);
  outline-offset: 2px;
}

.ui-date-range__value--placeholder {
  color: var(--text-muted);
}

.ui-date-range__value {
  flex: 1 1 auto;
  min-width: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.ui-date-range__chevron {
  flex: 0 0 16px;
  width: 16px;
  text-align: center;
  color: var(--text-muted);
  font-size: var(--text-sm);
}

.ui-date-range__panel {
  display: grid;
  gap: var(--space-2);
  width: 100%;
  min-width: var(--popover-trigger-w, 0px);
  max-width: 100%;
  overflow-x: hidden;
  overflow-y: auto;
}

.ui-date-range__fields {
  display: grid;
  gap: var(--space-2);
}

.ui-date-range__field {
  display: grid;
  gap: var(--space-1);
}

.ui-date-range__field-label {
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-date-range__input {
  height: var(--control-h);
  border-radius: var(--radius-md);
  border: 1px solid var(--border);
  background: var(--bg-alt);
  color: var(--text);
  padding: 0 var(--space-2);
}

.ui-date-range__help {
  margin: 0;
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-date-range--disabled .ui-date-range__control {
  background: var(--bg-muted);
  color: var(--text-muted);
  cursor: not-allowed;
  pointer-events: none;
}

.ui-date-range--error .ui-date-range__control {
  border-color: var(--danger-500);
}
</style>
