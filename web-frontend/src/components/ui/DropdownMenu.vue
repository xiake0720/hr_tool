<template>
  <div class="ui-dropdown" @keydown="onKeydown">
    <div
      class="ui-dropdown__trigger"
      role="button"
      tabindex="0"
      @click="toggle"
      @keydown.enter.prevent="toggle"
      @keydown.space.prevent="toggle"
      :aria-expanded="open ? 'true' : 'false'"
    >
      <slot name="trigger" />
    </div>
    <ul v-if="open" class="ui-dropdown__menu" role="menu">
      <li
        v-for="(item, index) in items"
        :key="item.value"
        class="ui-dropdown__item"
        :class="{
          'ui-dropdown__item--active': index === activeIndex,
          'ui-dropdown__item--disabled': item.disabled
        }"
        role="menuitem"
        tabindex="-1"
        @click="selectItem(item)"
        @mousemove="activeIndex = index"
      >
        {{ item.label }}
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

interface DropdownItem {
  label: string;
  value: string;
  disabled?: boolean;
}

interface Props {
  items: DropdownItem[];
}

const props = defineProps<Props>();
const emit = defineEmits<{ (event: "select", value: string): void }>();

const open = ref(false);
const activeIndex = ref(0);

function toggle(): void {
  open.value = !open.value;
}

function close(): void {
  open.value = false;
}

function selectItem(item: DropdownItem): void {
  if (item.disabled) return;
  emit("select", item.value);
  close();
}

function onKeydown(event: KeyboardEvent): void {
  if (!open.value && (event.key === "Enter" || event.key === "ArrowDown")) {
    open.value = true;
    return;
  }
  if (event.key === "Escape") {
    close();
    return;
  }
  if (event.key === "ArrowDown") {
    activeIndex.value = Math.min(activeIndex.value + 1, props.items.length - 1);
    return;
  }
  if (event.key === "ArrowUp") {
    activeIndex.value = Math.max(activeIndex.value - 1, 0);
    return;
  }
  if (event.key === "Enter") {
    const item = props.items[activeIndex.value];
    if (item) {
      selectItem(item);
    }
  }
}
</script>

<style scoped>
.ui-dropdown {
  position: relative;
  display: inline-flex;
}

.ui-dropdown__trigger {
  border: none;
  background: transparent;
  padding: 0;
  cursor: pointer;
  color: inherit;
}

.ui-dropdown__trigger:focus-visible {
  outline: 2px solid var(--brand-500);
  outline-offset: 2px;
}

.ui-dropdown__menu {
  position: absolute;
  top: calc(100% + var(--space-1));
  right: 0;
  background: var(--bg-alt);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  box-shadow: var(--shadow-3);
  padding: var(--space-2);
  min-width: 160px;
  z-index: var(--z-dropdown);
}

.ui-dropdown__item {
  padding: var(--space-2) var(--space-3);
  border-radius: var(--radius-sm);
  cursor: pointer;
  color: var(--text);
}

.ui-dropdown__item--active {
  background: var(--brand-50);
}

.ui-dropdown__item--disabled {
  color: var(--text-muted);
  cursor: not-allowed;
}
</style>
