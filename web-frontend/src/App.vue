<template>
  <AdminLayout v-if="useAdminLayout">
    <RouterView />
  </AdminLayout>
  <RouterView v-else />
</template>

<script setup lang="ts">
import { computed } from "vue";
import { RouterView, useRoute } from "vue-router";
import AdminLayout from "./layouts/AdminLayout.vue";

const route = useRoute();
const useAdminLayout = computed(() => route.meta.layout === "admin");
</script>
