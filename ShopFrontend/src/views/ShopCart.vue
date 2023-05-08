<script lang="ts" setup>
import { api } from "@/api";
import "../components/ShopItemView.vue";
const OnError = (text: string) => {
  ElMessage.error(text);
};
</script>
<script lang="ts">
import type { ShopItem } from "@/models/shop";
import { CheckTokenOrRedirect, GetToken } from "@/stores/token";
import type { id } from "element-plus/es/locale";
export default {
  computed: {
    FullPrice(): number {
      let result = 0;
      for (let i = 0; i < this.cartitems.length; i++) {
        const element = this.cartitems[i];
        if (element.count !== undefined) {
          result += element.price * element.count;
        }
      }
      return result;
    },
  },
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
    DelCart: async function (item: ShopItem) {
      const { success, message } = await api.shop.delcart(item.id);
      if (success) {
        ElMessage.success(message);
        var index = this.cartitems.findIndex((x) => x.id === item.id);
        if (index === -1) {
          this.RefreshCartItems();
        } else {
          console.log(index);
          this.cartitems.splice(index, 1);
        }
      } else {
        ElMessage.error(message);
      }
    },
    SetToCart: async function (item: ShopItem) {
      const { success, message } = await api.shop.settocart(
        item.id,
        item.count
      );
      if (success) {
        ElMessage.success(message);
      } else {
        ElMessage.error(message);
      }
    },
    RefreshCartItems: async function () {
      const { success, message, items } = await api.shop.getcart();
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
    }, 50);
  },
};
</script>
<template>
  <el-card v-if="loaded">
    <template #header>
      <div class="card-header">
        <span>商品列表</span>
        <el-button class="button" text @click="RefreshCartItems"
          >刷新购物车</el-button
        >
      </div>
    </template>
    <div v-if="loaded">
      <div v-if="cartitems.length">
        <table>
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
              <shop-item-view
                :item="item"
                @item-changed="
                  (current, prev) => {
                    item.count = current;
                    SetToCart(item);
                  }
                "
              />
            </td>
            <td>
              <el-button @click="DelCart(item)">删除</el-button>
            </td>
          </tr>
        </table>
        <h3 style="float: right">总价：{{ FullPrice }}</h3>
      </div>
      <div v-else>空空如也</div>
    </div>
    <div v-else>正在获取购物车商品</div>
  </el-card>
</template>
<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
}
</style>
