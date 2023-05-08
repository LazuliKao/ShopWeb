<script lang="ts" setup>
import { api } from "@/api";
import "../components/ShopItemView.vue";
</script>
<script lang="ts">
import type { ShopItem } from "@/models/shop";
import { CheckTokenOrRedirect, GetToken } from "@/stores/token";
export default {
  data(): {
    loaded: boolean;
    allitems: Array<ShopItem>;
  } {
    return {
      loaded: false,
      allitems: [],
    };
  },
  methods: {
    RefreshAllItems: async function () {
      const { success, message, items } = await api.shop.all(GetToken());
      if (!success) {
        ElMessage.error(message);
        return;
      }
      ElMessage.success(message);
      this.allitems = items;
      this.loaded = true;
    },
    AddToCart: async function (item: ShopItem) {
      const { success, message } = await api.shop.addtocart(
        GetToken(),
        item.id,
        item.count
      );
      if (success) {
        ElMessage.success(message);
      } else {
        ElMessage.error(message);
      }
    },
  },
  mounted() {
    CheckTokenOrRedirect();
    setTimeout(() => {
      this.RefreshAllItems();
    }, 50);
  },
};
</script>
<template>
  <div>
    <el-button @click="RefreshAllItems">刷新商品列表</el-button>
    <table v-if="loaded">
      <tr>
        <th>编号</th>
        <th>商品名称</th>
        <th>描述</th>
        <th>操作</th>
      </tr>
      <tr v-for="item in allitems">
        <td>
          {{ item.id }}
        </td>
        <td>
          {{ item.name }}
        </td>
        <td>
          {{ item.description }}
        </td>
        <td>
          <shop-item-view :item="item" />
        </td>
        <td>
          <el-button @click="AddToCart(item)">加入购物车</el-button>
        </td>
      </tr>
    </table>
    <div v-else>正在获取商品列表</div>
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
