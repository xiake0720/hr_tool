<template>
  <div class="ui-input ui-control" :class="rootClass" :style="rootStyle">
    <label v-if="label" class="ui-input__label" :for="inputId">{{ label }}</label>
    <div class="ui-input__field">
      <input
        :id="inputId"
        class="ui-input__control"
        :type="type"
        :value="modelValue"
        :placeholder="placeholder"
        :disabled="disabled"
        :aria-invalid="error ? 'true' : 'false'"
        :aria-describedby="helperId"
        @input="onInput"
      />
      <button
        v-if="clearable && modelValue"
        class="ui-input__clear"
        type="button"
        aria-label="Clear input"
        @click="clear"
      >
        x
      </button>
    </div>
    <p v-if="helperText" :id="helperId" class="ui-input__help" :class="{ 'ui-input__help--error': !!error }">
      {{ helperText }}
    </p>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";

interface Props {
  modelValue: string;
  label?: string;
  placeholder?: string;
  type?: string;
  helpText?: string;
  error?: string;
  clearable?: boolean;
  disabled?: boolean;
  id?: string;
  width?: string;
  size?: "sm" | "md" | "lg";
  fullWidth?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  type: "text",
  placeholder: "",
  helpText: "",
  error: "",
  clearable: false,
  disabled: false,
  width: "",
  size: "md",
  fullWidth: false
});

const emit = defineEmits<{ (event: "update:modelValue", value: string): void }>();

const inputId = computed(() => props.id || `ui-input-${Math.random().toString(36).slice(2, 9)}`);
const helperId = computed(() => `${inputId.value}-help`);
const helperText = computed(() => props.error || props.helpText);
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

function onInput(event: Event): void {
  const target = event.target as HTMLInputElement;
  emit("update:modelValue", target.value);
}

function clear(): void {
  emit("update:modelValue", "");
}
</script>

<style scoped>
.ui-input {
  display: grid;
  gap: var(--space-2);
}

.ui-input__label {
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-input__field {
  position: relative;
  display: flex;
  align-items: center;
}

.ui-input__control {
  width: 100%;
  height: var(--control-h);
  padding: 0 var(--space-3);
  border-radius: var(--radius-md);
  border: 1px solid var(--border);
  background: var(--bg-alt);
  color: var(--text);
  font-size: var(--text-md);
  transition: border-color var(--dur-2) var(--ease-out), box-shadow var(--dur-2) var(--ease-out);
}

.ui-input__control:hover:not(:disabled) {
  border-color: var(--brand-500);
  box-shadow: var(--shadow-1);
}

.ui-input__control:focus {
  outline: none;
  border-color: var(--brand-500);
  box-shadow: var(--shadow-2);
}

.ui-input__control:disabled {
  background: var(--bg-muted);
  color: var(--text-muted);
  cursor: not-allowed;
}

.ui-input__clear {
  position: absolute;
  right: var(--space-2);
  border: none;
  background: transparent;
  color: var(--text-muted);
  cursor: pointer;
  height: 24px;
  width: 24px;
  border-radius: 999px;
}

.ui-input__clear:hover {
  background: var(--bg-muted);
  color: var(--text);
}

.ui-input__help {
  margin: 0;
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-input__help--error {
  color: var(--danger-500);
}
</style>
