<template>
  <el-aside>
    <el-menu router>
      <el-menu-item index="/">
        <el-icon><i-ep-HomeFilled /></el-icon>
        <span>主页</span>
      </el-menu-item>
      <el-menu-item index="/login">
        <el-icon> <i-ep-menu /> </el-icon>
        <span>登录</span>
      </el-menu-item>
        <el-sub-menu index="/shop">
            <template #title>
                <el-icon> <i-ep-menu /> </el-icon>
                <span>商城</span>
            </template>
            <el-menu-item index="/shop/center">
                <el-icon> <i-ep-menu /> </el-icon>
                <span>所有项目</span>
            </el-menu-item> 
        </el-sub-menu>
    </el-menu>
  </el-aside>
</template>
<script lang="ts" setup></script>
<style  scoped>
.el-aside {
    background-color: #eee;
    height: 100vh;
    width: auto;
    margin: -4px;
}
.el-menu{
  background-color: #eee;
  width: 170px;

}
</style>
