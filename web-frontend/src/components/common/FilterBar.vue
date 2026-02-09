<template>
  <section class="ui-filter">
    <div class="ui-filter__row ui-filter__row--top">
      <div class="ui-filter__fields">
        <div class="ui-filter__field" style="width: var(--control-w-lg);">
          <label class="ui-filter__label">关键词</label>
          <div class="ui-filter__control">
            <UiInput v-model="keyword" placeholder="姓名 / 邮箱 / 职位" clearable fullWidth />
          </div>
        </div>
        <div class="ui-filter__field" style="width: var(--control-w);">
          <label class="ui-filter__label">阶段</label>
          <div class="ui-filter__control">
            <UiSelect v-model="stage" :options="stageOptions" placeholder="全部阶段" fullWidth />
          </div>
        </div>
        <div class="ui-filter__field" style="width: var(--control-w);">
          <label class="ui-filter__label">状态</label>
          <div class="ui-filter__control">
            <UiSelect v-model="status" :options="statusOptions" placeholder="全部状态" fullWidth />
          </div>
        </div>
        <div class="ui-filter__field" style="width: var(--control-w-lg);">
          <label class="ui-filter__label">日期范围</label>
          <div class="ui-filter__control">
            <DateRange v-model="dateRange" placeholder="开始 ~ 结束" fullWidth />
          </div>
        </div>
      </div>
      <div class="ui-filter__actions">
        <UiButton variant="ghost" size="sm" @click="clearAll">清空</UiButton>
        <button class="ui-filter__toggle" type="button" @click="toggleAdvanced">
          <span>{{ showAdvanced ? "收起高级" : "展开高级" }}</span>
          <span class="ui-filter__toggle-icon">v</span>
        </button>
      </div>
    </div>

    <div class="ui-filter__row ui-filter__row--bottom">
      <div class="ui-filter__fields">
        <div class="ui-filter__field" style="width: var(--control-w-lg);">
          <label class="ui-filter__label">标签</label>
          <div class="ui-filter__control ui-filter__chips">
            <UiCheckbox v-for="tag in tags" :key="tag" v-model="tagSelections[tag]">
              {{ tag }}
            </UiCheckbox>
          </div>
        </div>
        <div v-if="showAdvanced" class="ui-filter__field" style="width: var(--control-w-lg);">
          <label class="ui-filter__label">高级条件</label>
          <div class="ui-filter__control">
            <UiInput v-model="advancedKeyword" placeholder="岗位 / 负责人" fullWidth />
          </div>
        </div>
      </div>
    </div>

    <div v-if="activeFilters.length > 0" class="ui-filter__chips">
      <UiTag v-for="chip in activeFilters" :key="chip">{{ chip }}</UiTag>
      <UiButton size="sm" variant="ghost" @click="clearAll">一键清空</UiButton>
    </div>
  </section>
</template>

<script setup lang="ts">
import { computed, reactive, ref } from "vue";
import UiInput from "../ui/Input.vue";
import UiSelect from "../ui/Select.vue";
import UiButton from "../ui/Button.vue";
import UiCheckbox from "../ui/Checkbox.vue";
import UiTag from "../ui/Tag.vue";
import DateRange from "../ui/DateRange.vue";

const keyword = ref("");
const stage = ref("");
const status = ref("");
const dateRange = ref({ start: "", end: "" });
const showAdvanced = ref(false);
const advancedKeyword = ref("");

const tags = ["高潜", "急招", "内推", "海外"];
const tagSelections = reactive<Record<string, boolean>>({
  高潜: false,
  急招: false,
  内推: false,
  海外: false
});

const stageOptions = [
  { value: "初筛", label: "初筛" },
  { value: "面试", label: "面试" },
  { value: "终面", label: "终面" },
  { value: "Offer", label: "Offer" }
];

const statusOptions = [
  { value: "active", label: "进行中" },
  { value: "pending", label: "待处理" },
  { value: "failed", label: "失败" },
  { value: "success", label: "已完成" }
];

const activeFilters = computed(() => {
  const chips: string[] = [];
  if (keyword.value) chips.push(`关键词: ${keyword.value}`);
  if (stage.value) chips.push(`阶段: ${stage.value}`);
  if (status.value) chips.push(`状态: ${status.value}`);
  if (dateRange.value.start || dateRange.value.end) {
    chips.push(`日期: ${dateRange.value.start || "-"} ~ ${dateRange.value.end || "-"}`);
  }
  if (advancedKeyword.value) chips.push(`高级: ${advancedKeyword.value}`);
  Object.entries(tagSelections).forEach(([key, value]) => {
    if (value) chips.push(`标签: ${key}`);
  });
  return chips;
});

function clearAll(): void {
  keyword.value = "";
  stage.value = "";
  status.value = "";
  dateRange.value = { start: "", end: "" };
  advancedKeyword.value = "";
  Object.keys(tagSelections).forEach((key) => {
    tagSelections[key] = false;
  });
}

function toggleAdvanced(): void {
  showAdvanced.value = !showAdvanced.value;
}
</script>

<style scoped>
.ui-filter {
  display: grid;
  gap: var(--space-3);
  padding: var(--space-4);
  background: var(--bg-alt);
  border-radius: var(--radius-lg);
  border: 1px solid var(--border);
  box-shadow: var(--shadow-1);
}

.ui-filter__row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-3);
  flex-wrap: wrap;
}

.ui-filter__fields {
  display: flex;
  align-items: center;
  gap: var(--space-3);
  flex-wrap: wrap;
  flex: 0 0 auto;
}

.ui-filter__field {
  display: flex;
  align-items: center;
  gap: var(--space-3);
  flex: 0 0 auto;
}

.ui-filter__label {
  white-space: nowrap;
  flex: 0 0 auto;
  font-size: var(--text-sm);
  color: var(--text-muted);
}

.ui-filter__control {
  flex: 1 1 auto;
  min-width: 0;
}

.ui-filter__chips {
  display: flex;
  flex-wrap: wrap;
  gap: var(--space-2);
  align-items: center;
}

.ui-filter__actions {
  display: flex;
  align-items: center;
  gap: var(--space-2);
  flex: 0 0 auto;
}

.ui-filter__toggle {
  border: none;
  background: transparent;
  color: var(--text-muted);
  display: inline-flex;
  align-items: center;
  gap: var(--space-1);
  cursor: pointer;
  transition: color var(--dur-2) var(--ease-out);
}

.ui-filter__toggle:hover {
  color: var(--text);
}

.ui-filter__toggle-icon {
  font-size: var(--text-sm);
}
</style>
