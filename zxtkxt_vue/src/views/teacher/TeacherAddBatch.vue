<template>
<div>
  <div id="aa">
    <el-form>
      <el-form-item>
          <el-button type="primary" plain>
            <el-link type="primary" href="/api/file?flag=question" :underline="false">下载Excel模板</el-link>
          </el-button>
      </el-form-item>

      <el-form-item>
          <el-upload
              action="/api/file/uploadExcel"
              :on-success="handleSuccess"
              :before-upload="beforeUpload"
              :show-file-list="false"
              :accept="['.xls', '.xlsx']"
          >
            <el-button type="primary">上传试题Excel</el-button>
          </el-upload>
          <span style="font-size: 20px;color: cornflowerblue;margin-left: 20px">{{msg}}</span>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>

<script>

import request from "@/utils/request";

export default {
  name:"TeacherAddBatch",
  data(){
    return{
      msg:"上传"
    }
  },
  methods: {
    beforeUpload(file) {
      // 验证文件类型
      const fileType = file.type;
      if (fileType !== "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
        this.$message.error("只能上传xlsx文件");
        return false;
      }
      // 验证文件大小，不超过10M
      const fileSize = file.size / 1024 / 1024;
      if (fileSize > 10) {
        this.$message.error("文件大小不能超过10M");
        return false;
      }
    },
    handleSuccess(response) {
      this.msg=response.msg;
    }
  }
};
</script>
<style scoped>
 #aa{
   width: 400px;
   height: 200px;
   margin-left: 30px;
   margin-top: 30px;
   padding: 40px;
   box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
 }
</style>
