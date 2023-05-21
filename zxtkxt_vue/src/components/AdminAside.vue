<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        active-text-color="dodgerblue"
        background-color="#545c64"
        class="el-menu-vertical-demo"
        default-active="2"
        router
        text-color="#fff"
        @open="handleOpen"
        @close="handleClose"
    >
      <el-menu-item index="user">
        <el-icon><User /></el-icon>
        <span>用户管理</span>
      </el-menu-item>
      <el-menu-item index="logs">
        <el-icon><Menu /></el-icon>
        <span>组卷日志</span>
      </el-menu-item>
      <el-menu-item index="parameters">
        <el-icon><Grid /></el-icon>
        <span>算法设置</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "AdminAside"
}
</script>

<style scoped>

</style>