<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div class="main">
    <div class="header">
      <nav>
        <RouterLink to="/">主页</RouterLink>
        <RouterLink to="/shop/center">商店</RouterLink>
        <RouterLink to="/login">登录</RouterLink>
      </nav>
    </div>
    <div class="view">
      <RouterView />
    </div>
  </div>
</template>
<style scoped>
</style>
