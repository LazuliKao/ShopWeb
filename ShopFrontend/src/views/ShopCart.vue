<script lang="ts" setup>
import "../components/ShopItemView.vue";
const OnError = (text: string) => {
  ElMessage.error(text);
};
</script>
<script lang="ts">
import type { ShopItem } from "@/models/shop";
import { CheckTokenOrRedirect, GetToken } from "@/stores/token";
export default {
  data(): {
    loaded: boolean;
    cartitems: Array<ShopItem>;
  } {
    return {
      loaded: false,
      cartitems: [],
    };
  },
  methods: {
    RefreshCartItems: async function () {
      let result = await this.axios.post("shop/getcart", {
        token: GetToken(),
      });
      const {
        success,
        message,
        items,
      }: { success: boolean; message: string; items: Array<ShopItem> } =
        result.data;
      if (!success) {
        ElMessage.error(message);
        return;
      }
      ElMessage.success(message);
      this.cartitems = items;
      this.loaded = true;
    },
  },
  mounted() {
    CheckTokenOrRedirect();
    setTimeout(() => {
      this.RefreshCartItems();
    }, 500);
  },
};
</script>
<template>
  <div>
    购物车：
    <table v-if="loaded">
      <tr>
        <th>编号</th>
        <th>商品名称</th>
        <th>描述</th>
        <th>操作</th>
      </tr>
      <tr v-for="item in cartitems">
        <td>{{ item.id }}</td>
        <td>{{ item.name }}</td>
        <td>{{ item.description }}</td>
        <td>
          <shop-item-view :item="item" />
        </td>
      </tr>
    </table>
    <div v-else>正在获取购物车商品</div>
    <el-button @click="RefreshCartItems">刷新购物车</el-button>
  </div>
</template>
<style>
.btadd {
  margin-left: 5px;
}
.numtb {
  width: 30px;
}
</style>
