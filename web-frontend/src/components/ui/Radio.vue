<template>
  <label class="ui-radio" :class="{ 'ui-radio--disabled': disabled }">
    <input
      class="ui-radio__input"
      type="radio"
      :checked="modelValue === value"
      :disabled="disabled"
      @change="onChange"
    />
    <span class="ui-radio__circle" aria-hidden="true"></span>
    <span class="ui-radio__label"><slot /></span>
  </label>
</template>

<script setup lang="ts">
interface Props {
  modelValue: string | number | boolean;
  value: string | number | boolean;
  disabled?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  disabled: false
});

const emit = defineEmits<{ (event: "update:modelValue", value: string | number | boolean): void }>();

function onChange(): void {
  if (!props.disabled) {
    emit("update:modelValue", props.value);
  }
}
</script>

<style scoped>
.ui-radio {
  display: inline-flex;
  align-items: center;
  gap: var(--space-2);
  font-size: var(--text-md);
  color: var(--text);
  cursor: pointer;
}

.ui-radio__input {
  position: absolute;
  opacity: 0;
  pointer-events: none;
}

.ui-radio__circle {
  width: 18px;
  height: 18px;
  border-radius: 999px;
  border: 1px solid var(--border);
  background: var(--bg-alt);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  transition: border-color var(--dur-2) var(--ease-out), background var(--dur-2) var(--ease-out);
}

.ui-radio__input:focus-visible + .ui-radio__circle {
  outline: 2px solid var(--brand-500);
  outline-offset: 2px;
}

.ui-radio__input:checked + .ui-radio__circle {
  border-color: var(--brand-500);
}

.ui-radio__input:checked + .ui-radio__circle::after {
  content: "";
  width: 8px;
  height: 8px;
  border-radius: 999px;
  background: var(--brand-500);
}

.ui-radio--disabled {
  cursor: not-allowed;
  color: var(--text-muted);
}

.ui-radio--disabled .ui-radio__circle {
  background: var(--bg-muted);
}
</style>
