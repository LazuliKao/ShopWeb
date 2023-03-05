<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
// import { Menu, User } from '@element-plus/icons-vue'
</script>
<template>
  <div class="common-layout">
    <el-container>
      <el-header>测试</el-header>
      <el-container>
        <app-aside />
        <el-main>
          <RouterView />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<style scoped lang="scss"></style>
