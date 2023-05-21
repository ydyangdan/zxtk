<template>
  <div id="cc">
    <div id="app">
      <div style="flex: 1;margin-left: 40px">您可以修改个人信息</div>
      <br/>
      <el-form :model="form" status-icon  size="large" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="form.userName" autocomplete="off" style="width: 80%" />
        </el-form-item>
        <el-form-item label="密码"  :label-width="formLabelWidth">
          <el-input type="password" prefix-icon="lock" v-model="form.password" autocomplete="off" show-password style="width: 80%" ></el-input>
        </el-form-item>
        <el-form-item label="联系方式"  :label-width="formLabelWidth">
          <el-input v-model="form.contact" autocomplete="off" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="年龄"  :label-width="formLabelWidth">
          <el-input-number v-model="form.userAge" :min="0" :max="100"  style="width: 80%"/>
        </el-form-item>
        <el-form-item label="性别"  :label-width="formLabelWidth">
          <el-radio-group v-model="form.userSex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
            <el-radio label="未知"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <span style="width: 70px"></span>
          <el-button type="primary" @click="save('ruleForm')" style="width: 150px;">提交修改</el-button>
          <el-button @click="resetForm('ruleForm')" style="width: 100px">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "PersonalInfo",
  data() {
    var checkEmpty = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('不能为空'));
      }
      callback();
    };

    return {
      formLabelWidth :'140px',
      form: {
        userAge:0,
        password: '',
        userSex: '男',
        contact:'',
        userId:0,
      },
    };
  },
  methods: {
      resetForm(formName) {
          this.$refs[formName].resetFields();
      },
      save() {
          request.put("/user",this.form).then(res => {
            if (res.code == 0) {
              //小提示
              this.$message({
                type: "success",
                message: res.msg
              });
              this.resetForm('ruleForm');
            } else {
              //小提示
              this.$message({
                type: "error",
                message: res.msg
              })
            }
            // 处理响应
          }).catch(error => {
            // 处理错误
          })
      }
  }
}
</script>

<style scoped>
#app{
  height: 600px;
  width: 700px;
  margin: auto;
  margin-top: 30px;
  padding-top: 20px;
  padding-right: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)

}
</style>