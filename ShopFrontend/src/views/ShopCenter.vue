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
    allitems: Array<{ dialogFormVisible: Ref<boolean>; data: ShopItem }>;
  } {
    return {
      loaded: false,
      allitems: [],
    };
  },
  methods: {
    RefreshAllItems: async function () {
      const { success, message, items } = await api.shop.all();
      if (!success) {
        ElMessage.error(message);
        return;
      }
      ElMessage.success(message);
      this.allitems = items.map((x) => ({
        data: x,
        dialogFormVisible: false,
      }));
      this.loaded = true;
    },
    AddToCart: async function (item: ShopItem) {
      const { success, message } = await api.shop.addtocart(
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
  <el-card v-if="loaded">
    <template #header>
      <div class="card-header">
        <span>商品列表</span>
        <el-button class="button" text @click="RefreshAllItems"
          >刷新商品列表</el-button
        >
      </div>
    </template>
    <table>
      <el-row>
        <el-col v-for="item in allitems" :key="item.data.id" :span="40">
          <el-card :body-style="{ padding: '0px' }" style="margin: 5px">
            <div
              style="
                width: 100px;
                height: 100px;
                border: 1px solid #000;
                margin: auto;
                margin-top: 10px;
              "
            ></div>
            <div style="padding: 14px">
              <h3 size="large">{{ item.data.name }}</h3>
              <span>[#{{ item.data.id }}]</span>
              <span>{{ item.data.description }}</span>
              <!-- <shop-item-view :item="item" /> -->
              <div style="margin-top: 5px">
                <el-button text @click="item.dialogFormVisible = true">
                  加入购物车
                </el-button>
                <el-dialog
                  v-model="item.dialogFormVisible"
                  title="确认加入购物车"
                >
                  <el-form :model="item">
                    <el-form-item label="商品信息：" style="display: block">
                      <h3 size="large">{{ item.data.name }}</h3>
                      <br />
                      <br />
                      <span>[#{{ item.data.id }}]</span>
                      <br />
                      <span>{{ item.data.description }}</span>
                    </el-form-item>
                    <el-form-item label="数量">
                      <shop-item-view :item="item.data" />
                    </el-form-item>
                  </el-form>
                  <template #footer>
                    <span class="dialog-footer">
                      <el-button @click="item.dialogFormVisible = false"
                        >取消</el-button
                      >
                      <el-button
                        type="primary"
                        @click="
                          () => {
                            item.dialogFormVisible = false;
                            AddToCart(item.data);
                          }
                        "
                        >加入购物车</el-button
                      >
                    </span>
                  </template>
                </el-dialog>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </table>
  </el-card>
</template>
<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
}

.image {
  width: 100%;
  display: block;
}
</style>
