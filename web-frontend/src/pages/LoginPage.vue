<template>
  <div style="min-height: 100vh; display: grid; place-items: center; padding: 40px; position: relative;">
    <div style="position: absolute; top: 24px; right: 24px; display: flex; gap: var(--space-sm); align-items: center;">
      <label class="helper" for="locale">{{ t("common.language") }}</label>
      <UiSelect id="locale" v-model="selectedLocale" :options="languageOptions" style="width: 140px;" />
    </div>
    <div class="page-card" style="max-width: 420px; width: 100%;">
      <h1 class="page-title">{{ t("common.tokenLogin") }}</h1>
      <p class="page-subtitle">{{ t("common.pasteJwt") }}</p>
      <UiInput
        id="token"
        v-model="token"
        :label="t('common.token')"
        :placeholder="t('common.placeholderToken')"
      />
      <div style="height: var(--space-3)"></div>
      <UiButton style="width: 100%;" @click="handleSave">{{ t("common.saveToken") }}</UiButton>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import { setLocale } from "../i18n";
import { useUserStore } from "../stores/user";
import UiButton from "../components/ui/Button.vue";
import UiInput from "../components/ui/Input.vue";
import UiSelect from "../components/ui/Select.vue";

const route = useRoute();
const router = useRouter();
const store = useUserStore();
const { t, locale } = useI18n();

const token = ref(store.token.value || "");

const languageOptions = computed(() => [
  { value: "zh-CN", label: t("common.languageZh") },
  { value: "en-US", label: t("common.languageEn") }
]);

const selectedLocale = computed({
  get: () => String(locale.value),
  set: (value: string | number) => setLocale(String(value))
});

function handleSave(): void {
  store.setToken(token.value.trim());
  const redirect = (route.query.redirect as string) || "/me";
  router.push(redirect);
}
</script>
