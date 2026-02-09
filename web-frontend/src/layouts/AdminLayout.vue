<template>
  <div class="app-shell">
    <aside class="sidebar">
      <div class="brand">{{ t("common.appName") }}</div>
      <div class="status">
        <span class="status-dot"></span>
        <span>{{ t("common.localWorkspace") }}</span>
      </div>
      <nav class="nav-list">
        <RouterLink to="/me" class="nav-link" :class="{ active: isActive('/me') }">{{ t("common.me") }}</RouterLink>
        <RouterLink to="/users" class="nav-link" :class="{ active: isActive('/users') }">
          {{ t("common.users") }}
        </RouterLink>
        <RouterLink to="/org" class="nav-link" :class="{ active: isActive('/org') }">{{ t("common.org") }}</RouterLink>
        <RouterLink to="/roles" class="nav-link" :class="{ active: isActive('/roles') }">
          {{ t("common.roles") }}
        </RouterLink>
      </nav>
    </aside>
    <div class="content">
      <header class="topbar">
        <div>
          <div class="page-title">{{ t("common.iamConsole") }}</div>
          <div class="helper">{{ t("common.iamSubtitle") }}</div>
        </div>
        <div style="display: flex; gap: var(--space-sm); align-items: center;">
          <label class="helper" for="locale">{{ t("common.language") }}</label>
          <UiSelect
            id="locale"
            v-model="selectedLocale"
            :options="languageOptions"
            style="width: 140px;"
          />
          <UiButton variant="secondary" size="sm" type="button" @click="router.push('/login')">
            {{ t("common.switchToken") }}
          </UiButton>
        </div>
      </header>
      <main>
        <slot />
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useRoute, RouterLink, useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import { setLocale } from "../i18n";
import UiButton from "../components/ui/Button.vue";
import UiSelect from "../components/ui/Select.vue";

const route = useRoute();
const router = useRouter();
const { t, locale } = useI18n();

const isActive = (path: string) => route.path === path;

const languageOptions = computed(() => [
  { value: "zh-CN", label: t("common.languageZh") },
  { value: "en-US", label: t("common.languageEn") }
]);

const selectedLocale = computed({
  get: () => String(locale.value),
  set: (value: string | number) => setLocale(String(value))
});
</script>
