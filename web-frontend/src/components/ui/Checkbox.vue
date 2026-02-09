<template>
  <label class="ui-checkbox" :class="{ 'ui-checkbox--disabled': disabled }">
    <input
      class="ui-checkbox__input"
      type="checkbox"
      :checked="modelValue"
      :disabled="disabled"
      @change="onChange"
    />
    <span class="ui-checkbox__box" aria-hidden="true"></span>
    <span class="ui-checkbox__label"><slot /></span>
  </label>
</template>

<script setup lang="ts">
interface Props {
  modelValue: boolean;
  disabled?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  disabled: false
});

const emit = defineEmits<{ (event: "update:modelValue", value: boolean): void }>();

function onChange(event: Event): void {
  const target = event.target as HTMLInputElement;
  emit("update:modelValue", target.checked);
}
</script>

<style scoped>
.ui-checkbox {
  display: inline-flex;
  align-items: center;
  gap: var(--space-2);
  font-size: var(--text-md);
  color: var(--text);
  cursor: pointer;
}

.ui-checkbox__input {
  position: absolute;
  opacity: 0;
  pointer-events: none;
}

.ui-checkbox__box {
  width: 18px;
  height: 18px;
  border-radius: var(--radius-sm);
  border: 1px solid var(--border);
  background: var(--bg-alt);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  transition: border-color var(--dur-2) var(--ease-out), background var(--dur-2) var(--ease-out);
}

.ui-checkbox__input:focus-visible + .ui-checkbox__box {
  outline: 2px solid var(--brand-500);
  outline-offset: 2px;
}

.ui-checkbox__input:checked + .ui-checkbox__box {
  background: var(--brand-500);
  border-color: var(--brand-500);
}

.ui-checkbox__input:checked + .ui-checkbox__box::after {
  content: "";
  width: 10px;
  height: 6px;
  border-left: 2px solid var(--text-on-brand);
  border-bottom: 2px solid var(--text-on-brand);
  transform: rotate(-45deg);
}

.ui-checkbox--disabled {
  cursor: not-allowed;
  color: var(--text-muted);
}

.ui-checkbox--disabled .ui-checkbox__box {
  background: var(--bg-muted);
}
</style>
