<template>
  <el-form :model="form" ref="form" enctype="multipart/form-data">
    <el-form-item label="头像">
      <el-upload
          action="/api/upload"
          :headers="{ 'Authorization': 'Bearer ' + token }"
          :on-success="handleSuccess"
          :before-upload="beforeUpload"
          :file-list="form.avatarList"
          list-type="picture-card"
          :auto-upload="false"
          :multiple="false"
          :limit="1"
      >
        <i class="el-icon-plus"></i>
        <div class="el-upload__text">上传头像</div>
      </el-upload>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      form: {
        avatarList: []
      },
      token: 'your token here'
    };
  },
  methods: {
    beforeUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
        return false;
      }
      return true;
    },
    handleSuccess(response, file, fileList) {
      this.form.avatarList = fileList.slice(-1);
      this.$message.success('头像上传成功！');
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$message.success('表单提交成功！');
        } else {
          this.$message.error('表单提交失败，请检查表单输入是否正确。');
        }
      });
    }
  }
};
</script>