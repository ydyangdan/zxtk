<template>
  <div>
    <!--    头部-->
    <TeacherHeader style="position: fixed; top: 0; left: 0; right: 0; z-index: 10;"/>
    <!--    主体-->
    <div style="display: flex">
      <!--      菜单栏-->
      <TeacherAside  style="position: fixed;margin-top: 50px" />
      <!--      内容区域-->
      <router-view style="flex: 1;margin-left: 200px;margin-top: 50px"/>

    </div>
  </div>
</template>

<script>
import TeacherAside from "@/components/TeacherAside";
import TeacherHeader from "@/components/TeacherHeader";
import request from "@/utils/request";

export default {
  name: "TeacherLayout",
  components:{
    TeacherHeader,
    TeacherAside
  }
}
</script>

<style scoped>

</style>