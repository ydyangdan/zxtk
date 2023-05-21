<template>
  <div id="cc">
    <div id="app">
      <div style="flex: 1;margin-left: 40px">您可以选择从模板生成试卷：</div>
      <br/>
      <br/>
      <br/>
      <el-form :model="form" status-icon :rules="rules" size="large" ref="form" label-width="100px"  style="margin-left: 40px">

            <el-form-item label="模板编号"  prop="templateId" :label-width="formLabelWidth">
              <el-input v-model="form.templateId" autocomplete="off" style="width: 75%;" />
            </el-form-item>

            <el-form-item label="试卷名称" prop="paperName" :label-width="formLabelWidth">
              <el-input v-model="form.paperName" autocomplete="off" style="width: 75%;" />
            </el-form-item>

            <el-form-item label="组卷方式"  prop="autoType"  :label-width="formLabelWidth">
              <el-select v-model="form.autoType" placeholder="请选择组卷方式" style="width: 75%" clearable>
                <el-option label="蚁群算法" value="蚁群算法"></el-option>
                <el-option label="随机算法" value="随机算法"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="课程名称"  prop="courseName"  :label-width="formLabelWidth">
              <el-select v-model="form.courseName" placeholder="请选择课程名称"  clearable style="width: 75%">
                <el-option label="公共课程" value="公共课程" />
                <el-option
                    v-for="item in dataList"
                    :label="item"
                    :value="item"
                />
              </el-select>
            </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="createPaper('form')" style="width: 100px;margin-left:30px">生成试卷</el-button>
          <el-button @click="resetForm('form')" style="width: 100px;margin-left:60px">重 置</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "AddPaper",
  data() {
    var checkTemplateId = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('模板编号不能为空'));
      }
      callback();
    };
    return {
      dataList:[],
      formLabelWidth:110,
      form: {
        courseName: '',
        templateId:'',
        autoType:'蚁群算法',
        paperName:''
      },
      rules: {
        autoType:  [
          { required: true, message: '请选择组卷方式', trigger: 'change' }
        ],
        courseName:  [
          { required: true, message: '请选择课程名称', trigger: 'change' }
        ],
        templateId: [
          { validator: checkTemplateId,required: true, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    createPaper(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert("正在生成中，请等待片刻");
          request.post('/paper/addPaper',this.form,{
            timeout: 20000 // 超时时间为5秒
          }).then(res => {
            if (res.code == 0) {
              //小提示
              this.$message({
                type: "success",
                message: res.msg
              });
              this.resetForm('form');
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
            alert("不好意思失败了")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    getCourseName(){
      request.get("/course/getCourseName").then(res=>{
        this.dataList=res.data;
      })
    },

  },
  created() {
    this.getCourseName();
  }
}
</script>

<style scoped>
#app{
  height: 600px;
  width: 600px;
  margin: auto;
  margin-top: 10px;
  padding-top: 20px;
  padding-right: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
</style>

