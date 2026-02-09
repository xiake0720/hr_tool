<template>
  <label class="ui-switch" :class="{ 'ui-switch--disabled': disabled }">
    <input
      class="ui-switch__input"
      type="checkbox"
      :checked="modelValue"
      :disabled="disabled"
      @change="onChange"
    />
    <span class="ui-switch__track" aria-hidden="true">
      <span class="ui-switch__thumb"></span>
    </span>
    <span class="ui-switch__label"><slot /></span>
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
.ui-switch {
  display: inline-flex;
  align-items: center;
  gap: var(--space-2);
  font-size: var(--text-md);
  color: var(--text);
  cursor: pointer;
}

.ui-switch__input {
  position: absolute;
  opacity: 0;
  pointer-events: none;
}

.ui-switch__track {
  width: 40px;
  height: 22px;
  border-radius: 999px;
  background: var(--bg-muted);
  border: 1px solid var(--border);
  display: inline-flex;
  align-items: center;
  padding: 2px;
  transition: background var(--dur-2) var(--ease-out), border-color var(--dur-2) var(--ease-out);
}

.ui-switch__thumb {
  width: 16px;
  height: 16px;
  border-radius: 999px;
  background: var(--bg-alt);
  box-shadow: var(--shadow-1);
  transform: translateX(0);
  transition: transform var(--dur-2) var(--ease-out);
}

.ui-switch__input:checked + .ui-switch__track {
  background: var(--brand-500);
  border-color: var(--brand-500);
}

.ui-switch__input:checked + .ui-switch__track .ui-switch__thumb {
  transform: translateX(18px);
  background: var(--text-on-brand);
}

.ui-switch__input:focus-visible + .ui-switch__track {
  outline: 2px solid var(--brand-500);
  outline-offset: 2px;
}

.ui-switch--disabled {
  cursor: not-allowed;
  color: var(--text-muted);
}

.ui-switch--disabled .ui-switch__track {
  background: var(--bg-muted);
}
</style>
