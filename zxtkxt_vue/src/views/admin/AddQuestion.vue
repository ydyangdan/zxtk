<template>
  <div id="cc">
    <div id="app">
      <el-form :model="ruleForm" status-icon :rules="rules" size="large" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="题目" prop="questionSubject">
          <el-input v-model="ruleForm.questionSubject" type="textarea" />
        </el-form-item>
        <el-form-item label="答案" prop="questionAnswer">
          <el-input v-model="ruleForm.questionAnswer" type="textarea" />
        </el-form-item>
        <el-form-item label="试题类型" prop="questionType">
          <el-input v-model.number="ruleForm.questionType" ></el-input>
        </el-form-item>
        <el-form-item label="难度级别" prop="questionLevel">
          <el-select v-model="ruleForm.questionLevel" placeholder="请选择难度级别">
            <el-option label="简单" value="1"></el-option>
            <el-option label="一般" value="2"></el-option>
            <el-option label="较难" value="3"></el-option>
            <el-option label="很难" value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程类型" prop="courseType">
          <el-input v-model="ruleForm.courseType" ></el-input>
        </el-form-item>
        <el-form-item label="知识点" prop="questionPoint">
          <el-input v-model="ruleForm.questionPoint" ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="save('ruleForm')" style="width: 150px">保存</el-button>
          <el-button @click="resetForm('ruleForm')" style="width: 100px">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AddQuestion",
  data() {
    var checkEmpty = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('不能为空'));
      }
      callback();
    };

    return {
      ruleForm: {
        questionSubject: '',
        questionAnswer: '',
        questionType: '',
        questionLevel: '一般',
        courseType: '',
        questionPoint: ''
      },
      rules: {
        questionSubject: [
          { validator: checkEmpty, required: true,trigger: 'blur' }
        ],
        questionAnswer:  [
          { validator: checkEmpty, required: true,trigger: 'blur' }
        ],
        questionType:  [
          { validator: checkEmpty, required: true,trigger: 'blur' }
        ],
        courseType:  [
          { validator: checkEmpty, required: true,trigger: 'blur' }
        ],
        questionPoint:  [
          { validator: checkEmpty, required: true,trigger: 'blur' }
        ],
        questionLevel: [
          { required: true, message: '请选择难度级别', trigger: 'change' }
        ]
      }
    };
  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post('/question/save', this.ruleForm).then(res => {
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