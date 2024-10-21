<template>
  <h1 class="title">가이드북</h1>
  <IndexTab
    :tab="tab"
    :currentTab="currentTab"
    @click="(index) => changeTab(index)" />


  <section v-if="currentTab === 0" :key="currentTab">
    <Recommended/>
  </section>
  <section v-if="currentTab === 1" :key="currentTab">
    <Terms/>
  </section>
  <section v-if="currentTab === 2" :key="currentTab">
    <Etiquette/>
  </section>
</template>

<script setup>
import Etiquette from '@/components/guidebook/Etiquette.vue';
import IndexTab from '@/components/guidebook/IndexTab.vue';
import Recommended from '@/components/guidebook/Recommended.vue';
import Terms from '@/components/guidebook/Terms.vue';
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const currentTab = ref(0);
const tab = ref([
  { name: '입문 작품 추천' },
  { name: '뮤지컬 용어 안내' },
  { name: '뮤지컬 관람 매너' }
]);

const changeTab = (key) => {
  if (typeof key === 'number') {
    currentTab.value = key;
  }
};


const setTabFromUrl = () => {
  const path = router.currentRoute.value.path;
  switch (path) {
    case '/guidebook/guidemain':
      currentTab.value = 0;
      break;
    case '/guidebook/terms':
      currentTab.value = 1;
      break;
    case '/guidebook/etiquette':
      currentTab.value = 2;
      break;
  }
};

setTabFromUrl();

watch(currentTab, (newTab) => {
  let newPath;
  if (newTab === 0) {
    newPath = '/guidebook/guidemain';
  } else if (newTab === 1) {
    newPath = '/guidebook/terms';
  } else if (newTab === 2) {
    newPath = '/guidebook/etiquette';
  }
  if (newPath) {
    router.push(newPath);
  }
});

watch(
  () => router.currentRoute.value.path,
  (newPath) => {
    setTabFromUrl();
  }
);
</script>

<style scoped>
.title {
  margin-top: 50px;
  margin-left: 250px;
}
</style>