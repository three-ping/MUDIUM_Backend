<template>
    <div class="index-tab">
        <ul>
            <li 
            v-for="(item, key) in tab"
            :key="`tab-${key}`"
            :class="{'on':key === currentTab}"
            @click="$emit('click',key)">
            {{ item.name }}
            </li>
        </ul>
    </div>
</template>

<script setup>

const props = defineProps({
  tab: {
    type: Array,
    required: true
  },
  currentTab: {
    type: Number,
    default: 0
  }
});
</script>

<style scoped>
.index-tab {
    margin-top: 70px;
    display: flex;
    justify-content: center;

}

.index-tab ul {
    display: flex;
    justify-content: start;
    align-items: center;
    gap: 1px;
    list-style-type: none;
    padding: 0; 
    margin: 0; 
    width: 60%; 
}

.index-tab li {
    flex-grow: 1;
    text-align: center;
    padding:  10px;
    border-bottom: 2px solid transparent;
    color: #302f2f;
    background-color: #D8BFD8; 
    border-radius: 10px;
    font-size: 20px;
    font-weight: 700;
    transition: all 0.3s;
    cursor: pointer;
}

.index-tab li:hover {
    color: #9236A4;
    background-color: #C48DB4; 

}

.index-tab li.on {
    background-color: #9957a7;
    color: #e1c5e7;
}
</style>