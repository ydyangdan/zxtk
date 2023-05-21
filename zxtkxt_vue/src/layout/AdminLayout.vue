<template>
  <div>
    <!--    头部-->
    <AdminHeader />
    <!--    主体-->
    <div style="display: flex">
      <!--      菜单栏-->
      <AdminAside />
      <!--      内容区域-->
      <router-view style="flex: 1"/>

    </div>
  </div>
</template>

<script>
import Header from "@/components/TeacherHeader";
import AdminAside from "@/components/AdminAside";
import AdminHeader from "@/components/AdminHeader";

export default {
  name: "AdminLayout",
  components:{
    AdminHeader,
    AdminAside
  }
}
</script>

<style scoped>

</style>