<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
// import { Menu, User } from '@element-plus/icons-vue'
</script>
<template>
    <el-container>
      <el-header>图标（无）</el-header>
      <el-container>
        <app-aside />
        <el-main>
          <RouterView />
        </el-main>
      </el-container>
    </el-container>
</template>
<style scoped lang="scss"></style>
