<template>
  <div id="cc">
    <div class="fo">
      <span style="color: #1d6fbd">在线题库系统</span>
    </div>

    <div id="app">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" size="large" class="demo-ruleForm">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model.number="ruleForm.userName"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off" show-password></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" show-password></el-input>
        </el-form-item>

        <el-form-item label="身份" prop="userRole">
          <el-radio-group v-model="ruleForm.userRole">
            <el-radio label="教师"></el-radio>
            <el-radio label="管理员"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')" style="width: 150px">注册</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
        <div style="display: flex; align-items: center; justify-content: flex-end">
          <el-link type="primary" href="/#/login">立即登录</el-link>
        </div>
      </el-form>

    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "register",
  data() {
    var checkUserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('账号不能为空'));
      }
      callback();
    };

    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.password !== '') {
          this.$refs.ruleForm.validateField('password');
        }
        callback();
      }
    };

    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      ruleForm: {
        password: '',
        checkPass: '',
        userName: '',
        userRole: '教师',
      },
      rules: {
        password: [
          { validator: validatePass,required: true,  trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2,required: true,  trigger: 'blur' }
        ],
        userRole: [
          { required: true, message: '请选择身份', trigger: 'change' }
        ],
        userName: [
          { validator: checkUserName,required: true,  trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post('/user/registerCheck', this.ruleForm).then(res => {
            alert(res.msg);
            if (res.code==0){
              window.location.href=res.data;
            }else {
              alert(res.msg)
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
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
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
  height: 355px;
  width: 500px;
  /*margin: auto;*/
  /*margin-top: 10px;*/
  padding-top: 20px;
  padding-right: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
</style>

