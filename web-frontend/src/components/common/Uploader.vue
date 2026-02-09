<template>
  <div class="ui-uploader" @dragover.prevent @drop.prevent="handleDrop">
    <input ref="fileInput" class="ui-uploader__input" type="file" multiple @change="handleFiles" />
    <div class="ui-uploader__content">
      <div class="ui-uploader__title">拖拽文件到此处</div>
      <div class="ui-uploader__desc">支持 PDF / DOC / DOCX</div>
      <UiButton size="sm" variant="secondary" @click="triggerSelect">选择文件</UiButton>
    </div>

    <div v-if="queue.length > 0" class="ui-uploader__list">
      <div v-for="item in queue" :key="item.id" class="ui-uploader__item">
        <div>
          <div class="ui-uploader__name">{{ item.name }}</div>
          <div class="ui-uploader__status">{{ item.statusLabel }}</div>
        </div>
        <UiProgress :value="item.progress" />
        <UiButton v-if="item.status === 'failed'" size="sm" variant="secondary" @click="retry(item)">
          重试
        </UiButton>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import UiButton from "../ui/Button.vue";
import UiProgress from "../ui/Progress.vue";

type UploadStatus = "idle" | "uploading" | "success" | "failed";

interface UploadItem {
  id: string;
  file: File;
  name: string;
  progress: number;
  status: UploadStatus;
  statusLabel: string;
}

const emit = defineEmits<{
  (event: "filesSelected", files: File[]): void;
  (event: "uploadStart", item: UploadItem): void;
  (event: "uploadSuccess", item: UploadItem): void;
  (event: "uploadFail", item: UploadItem): void;
}>();

const fileInput = ref<HTMLInputElement | null>(null);
const queue = ref<UploadItem[]>([]);

function triggerSelect(): void {
  fileInput.value?.click();
}

function handleDrop(event: DragEvent): void {
  const files = Array.from(event.dataTransfer?.files || []);
  handleQueue(files);
}

function handleFiles(event: Event): void {
  const target = event.target as HTMLInputElement;
  const files = Array.from(target.files || []);
  handleQueue(files);
  target.value = "";
}

function handleQueue(files: File[]): void {
  if (files.length === 0) return;
  emit("filesSelected", files);
  const items = files.map((file) => ({
    id: Math.random().toString(36).slice(2, 9),
    file,
    name: file.name,
    progress: 0,
    status: "idle" as UploadStatus,
    statusLabel: "等待上传"
  }));
  queue.value = [...items, ...queue.value];
  items.forEach((item) => startUpload(item));
}

function startUpload(item: UploadItem): void {
  item.status = "uploading";
  item.statusLabel = "上传中";
  item.progress = 0;
  emit("uploadStart", item);
  const timer = window.setInterval(() => {
    item.progress = Math.min(100, item.progress + Math.random() * 18);
    if (item.progress >= 100) {
      window.clearInterval(timer);
      const success = Math.random() > 0.2;
      item.status = success ? "success" : "failed";
      item.statusLabel = success ? "已完成" : "失败";
      if (success) {
        emit("uploadSuccess", item);
      } else {
        emit("uploadFail", item);
      }
    }
  }, 300);
}

function retry(item: UploadItem): void {
  startUpload(item);
}
</script>

<style scoped>
.ui-uploader {
  border: 1px dashed var(--border);
  border-radius: var(--radius-lg);
  padding: var(--space-4);
  background: var(--bg-alt);
  display: grid;
  gap: var(--space-3);
}

.ui-uploader__input {
  display: none;
}

.ui-uploader__content {
  display: grid;
  gap: var(--space-2);
  text-align: center;
}

.ui-uploader__title {
  font-weight: 600;
  font-size: var(--text-lg);
}

.ui-uploader__desc {
  color: var(--text-muted);
  font-size: var(--text-sm);
}

.ui-uploader__list {
  display: grid;
  gap: var(--space-2);
}

.ui-uploader__item {
  display: grid;
  gap: var(--space-2);
  padding: var(--space-2);
  border-radius: var(--radius-md);
  border: 1px solid var(--border);
  background: var(--bg-alt);
}

.ui-uploader__name {
  font-weight: 600;
}

.ui-uploader__status {
  font-size: var(--text-sm);
  color: var(--text-muted);
}
</style>
