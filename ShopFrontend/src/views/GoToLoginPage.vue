<script setup lang="ts">
// import { useStore } from "@/stores";
// defineProps<{}>();
</script>
<script lang="ts">
export default {
  data() {
    return {
      time: 3,
      timeoutids: new Array<number>(), //当前计时器ID
    };
  },
  unmounted() {
    //卸载时清除计时器
    this.timeoutids.forEach((id) => {
      try {
        clearTimeout(id);
      } catch (error) {}
    });
  },
  mounted() {
    let i = 0;
    for (i = 0; i < this.time; i++) {
      this.timeoutids.push(
        setTimeout(() => {
          this.time -= 1; //倒计时
        }, 1000 * i)
      );
    }
    this.timeoutids.push(
      setTimeout(() => {
        this.$router.push("/login");
      }, 1000 * i)
    );
  },
};
</script>
<template>
  <div class="greetings">
    <h1 class="red">错误</h1>
    <h4>
      尚未登录！ 将在 {{ time }} 秒后跳转到
      <RouterLink to="/login">登录界面</RouterLink>
    </h4>
  </div>
</template>
<style scoped></style>
