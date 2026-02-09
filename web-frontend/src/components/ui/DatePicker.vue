<template>
  <div class="ui-date" :class="{ 'ui-date--disabled': disabled, 'ui-date--error': !!error }">
    <label v-if="label" class="ui-date__label">{{ label }}</label>

    <Popover :open="open && !disabled" :minWidth="260" @update:open="setOpen">
      <template #trigger>
        <div class="ui-date__control" role="button" :aria-expanded="open ? 'true' : 'false'">
          <span class="ui-date__value" :class="{ 'ui-date__value--placeholder': !modelValue }">
            {{ modelValue || placeholder }}
          </span>
          <span class="ui-date__chevron">v</span>
        </div>
      </template>

      <div ref="panelRef" class="ui-date__panel" tabindex="0" @keydown="onKeydown">
        <div class="ui-date__header">
          <button class="ui-date__nav" type="button" @click="prevMonth">&lt;</button>
          <div class="ui-date__month">{{ monthLabel }}</div>
          <button class="ui-date__nav" type="button" @click="nextMonth">&gt;</button>
        </div>
        <div class="ui-date__week">
          <span v-for="day in weekDays" :key="day">{{ day }}</span>
        </div>
        <div class="ui-date__grid">
          <button
            v-for="day in calendarDays"
            :key="day.key"
            class="ui-date__cell"
            :class="{
              'ui-date__cell--muted': day.isOutside,
              'ui-date__cell--selected': day.value === modelValue
            }"
            type="button"
            @click="selectDay(day.value)"
          >
            {{ day.label }}
          </button>
        </div>
      </div>
    </Popover>

    <p v-if="helperText" class="ui-date__help">{{ helperText }}</p>
  </div>
</template>

<script setup lang="ts">
import { computed, nextTick, ref, watch } from "vue";
import Popover from "./Popover.vue";

interface Props {
  modelValue: string;
  label?: string;
  placeholder?: string;
  helperText?: string;
  error?: string;
  disabled?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  label: "",
  placeholder: "选择日期",
  helperText: "",
  error: "",
  disabled: false
});

const emit = defineEmits<{ (event: "update:modelValue", value: string): void }>();

const open = ref(false);
const current = ref(new Date());
const panelRef = ref<HTMLElement | null>(null);

const weekDays = ["一", "二", "三", "四", "五", "六", "日"];

const monthLabel = computed(() => {
  const year = current.value.getFullYear();
  const month = current.value.getMonth() + 1;
  return `${year} / ${month}`;
});

const calendarDays = computed(() => {
  const year = current.value.getFullYear();
  const month = current.value.getMonth();
  const firstDay = new Date(year, month, 1);
  const startDay = (firstDay.getDay() + 6) % 7;
  const daysInMonth = new Date(year, month + 1, 0).getDate();
  const days: Array<{ key: string; label: number; value: string; isOutside: boolean }> = [];

  for (let i = 0; i < startDay; i++) {
    const date = new Date(year, month, i - startDay + 1);
    days.push({
      key: `p-${i}`,
      label: date.getDate(),
      value: formatDate(date),
      isOutside: true
    });
  }

  for (let day = 1; day <= daysInMonth; day++) {
    const date = new Date(year, month, day);
    days.push({
      key: `c-${day}`,
      label: day,
      value: formatDate(date),
      isOutside: false
    });
  }

  return days;
});

watch(
  () => props.modelValue,
  (value) => {
    if (!value) return;
    const [year, month, day] = value.split("-").map(Number);
    if (!year || !month || !day) return;
    current.value = new Date(year, month - 1, day);
  }
);

watch(
  () => open.value,
  async (value) => {
    if (!value) return;
    await nextTick();
    panelRef.value?.focus();
  }
);

function formatDate(date: Date): string {
  const year = date.getFullYear();
  const month = `${date.getMonth() + 1}`.padStart(2, "0");
  const day = `${date.getDate()}`.padStart(2, "0");
  return `${year}-${month}-${day}`;
}

function selectDay(value: string): void {
  emit("update:modelValue", value);
  open.value = false;
}

function prevMonth(): void {
  current.value = new Date(current.value.getFullYear(), current.value.getMonth() - 1, 1);
}

function nextMonth(): void {
  current.value = new Date(current.value.getFullYear(), current.value.getMonth() + 1, 1);
}

function onKeydown(event: KeyboardEvent): void {
  if (event.key === "Escape") {
    open.value = false;
  }
}

function setOpen(value: boolean): void {
  if (props.disabled) {
    open.value = false;
    return;
  }
  open.value = value;
}
</script>

<style scoped>
.ui-date {
  display: grid;
  gap: var(--space-2);
}

.ui-date__label {
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-date__control {
  height: 36px;
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

.ui-date__control:hover {
  border-color: var(--brand-500);
  box-shadow: var(--shadow-1);
}

.ui-date__value--placeholder {
  color: var(--text-muted);
}

.ui-date__chevron {
  color: var(--text-muted);
  font-size: var(--text-sm);
}

.ui-date__panel {
  display: grid;
  gap: var(--space-2);
}

.ui-date__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: var(--space-2);
}

.ui-date__nav {
  border: 1px solid var(--border);
  background: var(--bg-alt);
  color: var(--text);
  width: 28px;
  height: 28px;
  border-radius: var(--radius-sm);
  cursor: pointer;
}

.ui-date__month {
  font-weight: 600;
}

.ui-date__week {
  display: grid;
  grid-template-columns: repeat(7, minmax(0, 1fr));
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-date__grid {
  display: grid;
  grid-template-columns: repeat(7, minmax(0, 1fr));
  gap: var(--space-1);
}

.ui-date__cell {
  border: none;
  background: transparent;
  padding: var(--space-1);
  border-radius: var(--radius-sm);
  cursor: pointer;
  color: var(--text);
}

.ui-date__cell--muted {
  color: var(--text-muted);
}

.ui-date__cell--selected {
  background: var(--brand-100);
  color: var(--brand-600);
}

.ui-date__help {
  margin: 0;
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-date--disabled .ui-date__control {
  background: var(--bg-muted);
  color: var(--text-muted);
  cursor: not-allowed;
  pointer-events: none;
}

.ui-date--error .ui-date__control {
  border-color: var(--danger-500);
}
</style>
