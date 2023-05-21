<template>
  <div id="cc">
    <div id="app">
      <div style="flex: 1;margin-left: 40px">请输入您的要求，系统会自动生成试卷：</div>
      <br/>
      <el-form :model="form" status-icon :rules="rules" size="large" ref="form" label-width="100px"  style="margin-left: 40px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="单选题数量"  prop="choiceCount"  :label-width="formLabelWidth" >
              <el-input-number v-model="form.choiceCount" :min="0" :max="100"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单选题分数" prop="choiceScore" :label-width="formLabelWidth">
              <el-input-number v-model="form.choiceScore" :min="0" :max="100"  />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="多选题数量" prop="multiCount" :label-width="formLabelWidth">
              <el-input-number v-model="form.multiCount" :min="0" :max="100"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="多选题分数" prop="multiScore" :label-width="formLabelWidth">
              <el-input-number v-model="form.multiScore" :min="0" :max="100"  />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="填空题数量" prop="blankFillingCount" :label-width="formLabelWidth">
              <el-input-number v-model="form.blankFillingCount" :min="0" :max="100"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="填空题分数" prop="blankFillingScore" :label-width="formLabelWidth">
              <el-input-number v-model="form.blankFillingScore" :min="0" :max="100"  />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="判断题数量" prop="judgeCount" :label-width="formLabelWidth">
              <el-input-number v-model="form.judgeCount" :min="0" :max="100"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="判断题分数" prop="judgeScore" :label-width="formLabelWidth">
              <el-input-number v-model="form.judgeScore" :min="0" :max="100"  />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="简答题数量"  prop="shortCount"  :label-width="formLabelWidth">
              <el-input-number v-model="form.shortCount" :min="0" :max="100"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="简答题分数"  prop="shortScore"  :label-width="formLabelWidth">
              <el-input-number v-model="form.shortScore" :min="0" :max="100"  />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="试卷名称" :label-width="formLabelWidth">
              <el-input v-model="form.paperName" autocomplete="off" style="width: 200px;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组卷方式"  prop="autoType"  :label-width="formLabelWidth">
              <el-select v-model="form.autoType" placeholder="请选择组卷方式" style="width: 90%" clearable>
                <el-option label="蚁群算法" value="蚁群算法"></el-option>
                <el-option label="随机算法" value="随机算法"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="难度级别"  prop="paperLevel"  :label-width="formLabelWidth">
              <el-select v-model="form.paperLevel" placeholder="请选择难度级别" style="width: 90%" clearable>
                <el-option label="容易" value="容易"></el-option>
                <el-option label="较易" value="较易"></el-option>
                <el-option label="中等" value="中等"></el-option>
                <el-option label="较难" value="较难"></el-option>
                <el-option label="很难" value="很难"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="课程名称"  prop="courseName"  :label-width="formLabelWidth">
              <el-select v-model="form.courseName" placeholder="请选择课程名称"  clearable style="width: 90%">
                <el-option label="公共课程" value="公共课程" />
                <el-option
                    v-for="item in dataList"
                    :label="item"
                    :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="知识点覆盖度"  prop="pointCoverage"  :label-width="formLabelWidth">
              <el-input type="number" v-model="form.pointCoverage" :min="0" :max="1" :precision="2" :step="0.1"  style="width: 90%" clearable />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="试卷重复度"  prop="repeatability"  :label-width="formLabelWidth">
              <el-input  type="number"  v-model.trim="form.repeatability" :min="0" :max="1" :precision="2"  style="width: 90%" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item>
          <el-button type="primary" @click="createPaper('form')" style="width: 100px;margin-left:50px">生成试卷</el-button>
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
  name: "AutoPaper",
  data() {
    return {
      dataList:[],
      formLabelWidth:110,
      form: {
        paperLevel: '中等',
        courseName: '',
        repeatability:'0.2',
        autoType:'蚁群算法',
        pointCoverage:'0.8',
        userName:Cookies.get("userName")
      },
      rules: {
        autoType:  [
          { required: true, message: '请选择组卷方式', trigger: 'change' }
        ],
        courseName:  [
          { required: true, message: '请选择课程名称', trigger: 'change' }
        ],
        paperLevel: [
          { required: true, message: '请选择难度级别', trigger: 'change' }
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
          request.post('/paper/createPaper', this.form,{
            timeout: 20000 // 超时时间为20秒
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
  height: 650px;
  width: 700px;
  margin: auto;
  margin-top: 10px;
  padding-top: 20px;
  padding-right: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
</style>
