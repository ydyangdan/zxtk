<template>
  <div class="sidebar">
    <el-scrollbar wrap-class="scrollbar-wrap" view-class="scrollbar-view">
        <el-menu
            style="width: 180px; min-height: calc(100vh - 50px);overflow: auto"
            active-text-color="dodgerblue"
            background-color="#545c64"
            class="el-menu-vertical-demo"
            default-active="dashboard"
            text-color="#fff"
            router
            @open="handleOpen"
            @close="handleClose"
        >
          <el-menu-item index="dashboard">
            <el-icon><Odometer /></el-icon>
            <span>控制台</span>
          </el-menu-item>

          <el-sub-menu index="1">
              <template #title>
                <el-icon><DocumentCopy /></el-icon>
                <span>试题管理</span>
              </template>
              <el-menu-item index="teacherQueryQuestion">查询试题</el-menu-item>
              <el-menu-item index="teacherAddQuestion">手动上传</el-menu-item>
              <el-menu-item index="teacherAddBatch">批量上传</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="2">
              <template #title>
                <el-icon><Discount /></el-icon>
                <span>组卷管理</span>
              </template>
              <el-menu-item index="handPaper">手动组卷</el-menu-item>
              <el-menu-item index="autoPaper">自动组卷</el-menu-item>
              <el-menu-item index="addPaper">从模板组卷</el-menu-item>
          </el-sub-menu>

          <el-menu-item index="teacherPaper">
            <el-icon><Operation /></el-icon>
            <span>试卷管理</span>
          </el-menu-item>

          <el-menu-item index="teacherCourse">
              <el-icon><Menu /></el-icon>
              <span>课程管理</span>
          </el-menu-item>

          <el-menu-item index="point">
            <el-icon><Coin /></el-icon>
            <span>知识点管理</span>
          </el-menu-item>

          <el-menu-item index="template">
            <el-icon><SetUp /></el-icon>
            <span>模板管理</span>
          </el-menu-item>

<!--          <el-menu-item index="teacherQuestionBank">-->
<!--              <el-icon><Grid /></el-icon>-->
<!--              <span>题库管理</span>-->
<!--          </el-menu-item>-->

        </el-menu>

    </el-scrollbar>
  </div>
</template>

<style>
.sidebar {
  width: 200px;
  height: calc(100vh - 50px);
  overflow: hidden;
}
.sidebar::-webkit-scrollbar-thumb{
  background-color: #1d6fbd ;
}

.menu {
  padding: 20px;
}

.scrollbar-wrap {
  height: 100%;
}

.scrollbar-view {
  height: 100%;
}
</style>

<script>
export default {
  name: "TeacherAside",
  methods: {

  },
};
</script>