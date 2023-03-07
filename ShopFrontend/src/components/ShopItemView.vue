<template>
  <el-input-number
    v-model="item.count"
    :min="1"
    :max="99"
    @change="handleChange"
  />
</template>
<script lang="ts" setup>
import type { ShopItem } from "@/models/shop";
const props = defineProps<{
  item: ShopItem;
}>();
</script>
<script lang="ts">
export default {
  emits: ["itemChanged"],
  methods: {
    handleChange(current: number | undefined, prev: number | undefined) {
      this.$emit("itemChanged", current, prev);
    },
  },
};
</script>

<style></style>
