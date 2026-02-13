<template>
  <div
    class="ui-select ui-control"
    :class="[rootClass, { 'ui-select--disabled': disabled, 'ui-select--error': !!error }]"
    :style="rootStyle"
  >
    <label v-if="label" class="ui-select__label">{{ label }}</label>

    <Popover
      :open="open && !disabled"
      :minWidth="0"
      :matchTriggerWidth="true"
      :fullWidth="props.fullWidth === true"
      @update:open="setOpen"
    >
      <template #trigger>
        <div class="ui-select__control ui-select__control--full" role="combobox" :aria-expanded="open ? 'true' : 'false'">
          <span class="ui-select__value" :class="{ 'ui-select__value--placeholder': !selectedLabel }">
            {{ selectedLabel || placeholder }}
          </span>
          <span class="ui-select__chevron">v</span>
        </div>
      </template>

      <div ref="panelRef" class="ui-select__panel" role="listbox" tabindex="0" @keydown="onKeydown">
        <input
          v-if="searchable"
          v-model="search"
          class="ui-select__search"
          type="text"
          placeholder="搜索"
          @keydown.stop
        />
        <div
          v-for="(option, index) in filteredOptions"
          :key="option.value"
          class="ui-select__option"
          :class="{
            'ui-select__option--active': index === activeIndex,
            'ui-select__option--selected': option.value === modelValue,
            'ui-select__option--disabled': option.disabled
          }"
          role="option"
          :aria-selected="option.value === modelValue ? 'true' : 'false'"
          @mousemove="activeIndex = index"
          @mousedown.prevent="selectOption(option)"
        >
          {{ option.label }}
        </div>
        <div v-if="filteredOptions.length === 0" class="ui-select__empty">暂无数据</div>
      </div>
    </Popover>

    <p v-if="helperText" class="ui-select__help">{{ helperText }}</p>
  </div>
</template>

<script setup lang="ts">
import { computed, nextTick, ref, watch } from "vue";
import Popover from "./Popover.vue";

interface OptionItem {
  value: string | number;
  label: string;
  disabled?: boolean;
}

interface Props {
  modelValue: string | number;
  options: OptionItem[];
  label?: string;
  placeholder?: string;
  helperText?: string;
  error?: string;
  disabled?: boolean;
  searchable?: boolean;
  width?: string;
  size?: "sm" | "md" | "lg";
  fullWidth?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  label: "",
  placeholder: "请选择",
  helperText: "",
  error: "",
  disabled: false,
  searchable: false,
  width: "",
  size: "md",
  fullWidth: false
});

const emit = defineEmits<{ (event: "update:modelValue", value: string | number): void }>();
const open = ref(false);
const search = ref("");
const activeIndex = ref(0);
const panelRef = ref<HTMLElement | null>(null);

const filteredOptions = computed(() => {
  if (!props.searchable || !search.value) return props.options;
  const keyword = search.value.toLowerCase();
  return props.options.filter((option) => option.label.toLowerCase().includes(keyword));
});

const selectedLabel = computed(() => {
  const match = props.options.find((option) => option.value === props.modelValue);
  return match?.label ?? "";
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

watch(
  () => open.value,
  async (value) => {
    if (!value) {
      search.value = "";
      activeIndex.value = 0;
      return;
    }
    await nextTick();
    panelRef.value?.focus();
  }
);

function selectOption(option: OptionItem): void {
  if (option.disabled || props.disabled) return;
  emit("update:modelValue", option.value);
  open.value = false;
}

function setOpen(value: boolean): void {
  if (props.disabled) {
    open.value = false;
    return;
  }
  open.value = value;
}

function onKeydown(event: KeyboardEvent): void {
  if (event.key === "Escape") {
    open.value = false;
    return;
  }
  if (event.key === "ArrowDown") {
    activeIndex.value = Math.min(activeIndex.value + 1, filteredOptions.value.length - 1);
    return;
  }
  if (event.key === "ArrowUp") {
    activeIndex.value = Math.max(activeIndex.value - 1, 0);
    return;
  }
  if (event.key === "Enter") {
    const option = filteredOptions.value[activeIndex.value];
    if (option) {
      selectOption(option);
    }
  }
}
</script>

<style scoped>
.ui-select {
  display: grid;
  gap: var(--space-2);
}

.ui-select__label {
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-select__control {
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

.ui-select__control--full {
  width: 100%;
}

.ui-select__control:hover {
  border-color: var(--brand-500);
  box-shadow: var(--shadow-1);
}

:deep(.ui-popover__trigger:focus-visible) .ui-select__control {
  outline: 2px solid var(--brand-500);
  outline-offset: 2px;
}

.ui-select__value--placeholder {
  color: var(--text-muted);
}

.ui-select__value {
  flex: 1 1 auto;
  min-width: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.ui-select__chevron {
  flex: 0 0 16px;
  width: 16px;
  text-align: center;
  margin-left: var(--space-2);
  color: var(--text-muted);
  font-size: var(--text-sm);
}

.ui-select__panel {
  display: grid;
  gap: var(--space-1);
  width: 100%;
  overflow-x: hidden;
}

.ui-select__search {
  height: 32px;
  border-radius: var(--radius-sm);
  border: 1px solid var(--border);
  padding: 0 var(--space-2);
  background: var(--bg-alt);
  color: var(--text);
}

.ui-select__option {
  padding: var(--space-2) var(--space-3);
  border-radius: var(--radius-sm);
  cursor: pointer;
}

.ui-select__option--active {
  background: var(--brand-50);
}

.ui-select__option--selected {
  background: var(--brand-100);
  color: var(--brand-600);
}

.ui-select__option--disabled {
  color: var(--text-muted);
  cursor: not-allowed;
}

.ui-select__empty {
  padding: var(--space-2) var(--space-3);
  color: var(--text-muted);
}

.ui-select__help {
  margin: 0;
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-select--disabled .ui-select__control {
  background: var(--bg-muted);
  color: var(--text-muted);
  cursor: not-allowed;
  pointer-events: none;
}

.ui-select--error .ui-select__control {
  border-color: var(--danger-500);
}
</style>
