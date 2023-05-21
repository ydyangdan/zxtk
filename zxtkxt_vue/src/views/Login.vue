<template>
  <div id="cc">
    <div class="fo">
      <span style="color: #1d6fbd">在线题库系统</span>
    </div>
    <div id="app">
      <el-form :model="ruleForm" status-icon :rules="rules" size="large" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model.number="ruleForm.userName" prefix-icon="user"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" prefix-icon="lock" v-model="ruleForm.password" autocomplete="off" show-password clearable></el-input>
        </el-form-item>
        <el-form-item label="身份" prop="userRole">
          <el-radio-group v-model="ruleForm.userRole">
            <el-radio label="教师"></el-radio>
            <el-radio label="管理员"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')" style="width: 150px" plain>登 录</el-button>
          <el-button @click="resetForm('ruleForm')" style="width: 100px" plain>重 置</el-button>
        </el-form-item>
        <div style="display: flex; align-items: center; justify-content: flex-end">
          <el-link type="primary" href="/#/register">立即注册</el-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>

import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "Login",
  data() {
    var checkuserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('账号不能为空'));
      }
      callback();
    };
    var validatePassword = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('密码不能为空'));
      }
      callback();
    };
    return {
      ruleForm: {
        password: '',
        userRole: '教师',
        userName: ''
      },
      rules: {
        password: [
          { validator: validatePassword, required: true,trigger: 'blur' }
        ],
        userRole: [
          { required: true, message: '请选择身份', trigger: 'change' }
        ],
        userName: [
          { validator: checkuserName,required: true, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Cookies.set('userName',this.ruleForm.userName);
          request.post('/user/loginCheck', this.ruleForm).then(res => {
            if (res.code==0){
              //小提示
              this. $message({
                type : "success" ,
                message:res.msg
              })
              this.$router.push("/"+res.data); //登录成功后跳转
            }else {
              //小提示
              this. $message({
                type : "error" ,
                message:res.msg
              })
            }
            // 处理响应
          }).catch(error => {
            // 处理错误
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}

</script>

<style scoped>
#cc{
  border: 2px solid #1d6fbd;
  width: 500px;
  margin: auto;
  margin-top:100px;
}
.fo{
  text-align: center;
  border-bottom: 2px solid #1d6fbd;
  font-size: 30px;
  padding: 5px;
}
#app{
  height: 300px;
  width: 500px;
  /*margin: auto;*/
  /*margin-top: 10px;*/
  padding-top: 20px;
  padding-right: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
</style>