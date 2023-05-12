<script lang="ts">
import { TryGetToken } from "@/stores/token";
export default {
  data(): {
    token: undefined | string;
  } {
    return {
      token: undefined,
    };
  },
  methods: {},
  mounted() {
    let r = TryGetToken();
    if (!r.success) {
      this.$router.push("/login/goto");
    } else {
      this.token = r.token;
    }
  },
};
</script>
<template>
  <div>
    <h3>主页</h3>
    会话ID：{{ token }}
    <div class="block text-center">
      <span>轮播图</span>
      <el-carousel height="150px">
        <el-carousel-item v-for="item in 4" :key="item">
          <h3 class="small justify-center" text="2xl">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div m="t-4">
      <span>轮播图2</span>
      <el-carousel trigger="click" height="150px">
        <el-carousel-item v-for="item in 4" :key="item">
          <h3 class="small justify-center" text="2xl">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>
<style scoped>
.demonstration {
  color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
