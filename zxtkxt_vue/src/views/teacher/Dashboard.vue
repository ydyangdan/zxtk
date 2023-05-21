<template>
  <div id="app">
      <div class="app-container">
        
          <div style="flex: 1">
             <h2 align="center" class="pm pm1" style="font-style:italic;size: 20px;color: #20a0ff">{{showUserName}},&nbsp;&nbsp;{{greeting}}! </h2>
          </div>

          <div class="clearfix">
            <el-button style="float: right; padding: 3px 0; font-size: 12px" type="text">系统版本：{{ version }}</el-button>
          </div>

          <div style="line-height: 32px;">

            <div class="title" style="padding-top: 0px">系统介绍</div>
            在线题库系统是一款基于SpringBoot+Vue开发的题库系统。提供试题管理和自动组卷功能。

            <div class="title">技术栈</div>
            <div class="tags">
              <el-tag>SpringBoot 2</el-tag>
              <el-tag>Vue3</el-tag>
              <el-tag>Mysql</el-tag>
              <el-tag>MyBatis Plus</el-tag>
              <el-tag>Redis</el-tag>
              <el-tag>JWT</el-tag>
              <el-tag>Element plus</el-tag>
            </div>

            <div class="title">产品功能</div>
            <div>
              <div>系统完善：完善的前后端分离架构、规范的接口文档</div>
              <div>基础功能：系统配置、用户管理、角色管理等。</div>
              <div>题库管理：支持单选题、多选题、判断题、简答题，支持试题批量导入导出</div>
              <div>自动组卷：教师设定组卷规则,系统会基于蚁群算法自动生成最合理的试卷</div>
            </div>

          </div>
      </div>
  </div>
</template>

<script>

import moment from "moment";
import Cookies from "js-cookie";

export default {
  name: 'Dashboard',
  data() {
    return {
      currentRole: 'adminDashboard',
      version: '1.2.3'
    }
  },
  computed: {
    greeting() {
      const currentHour = moment().hour();
      let greetingMessage = '';

      if (currentHour >= 0 && currentHour < 12) {
        greetingMessage = '早上好';
      } else if (currentHour >= 12 && currentHour < 18) {
        greetingMessage = '下午好';
      } else {
        greetingMessage = '晚上好';
      }

      return greetingMessage;
    },
    showUserName(){
      return Cookies.get("userName");
    }
  }
}
</script>

<style scoped>
.box{
  height: calc(100vh - 120px);
  font-size: 14px;
  line-height: 28px;
}
.title{
  font-size: 18px;
  font-weight: 700;
  padding-bottom: 10px;
  padding-top: 20px;
  margin-bottom: 10px;
  border-bottom: #eee 1px dotted;
}

.clearfix{
  font-size: 18px;
  font-weight: 700;
  padding-bottom: 10px;
  padding-top: 20px;
  margin-bottom: 10px;
  border-bottom: #eee 1px dotted;
}

.tags span{
  margin-right: 10px;
}

.box a{
  color: #20a0ff;
}

.box a:hover{
  color: #ff0000;
}
.app-container{
  height: 650px;
  width: 60%;
  margin: auto;
  margin-top: 20px;
  padding: 20px;
  padding-left: 30px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}

</style>
