<template>
  <div class="home" style="padding: 7px">
    <div style="margin: 6px 0;">
      <el-select v-model="questionType"  placeholder="请选择试题类型" style="width: 13%" clearable>
        <el-option label="单选题" value="单选题" />
        <el-option label="多选题" value="多选题" />
        <el-option label="填空题" value="填空题" />
        <el-option label="判断题" value="判断题" />
        <el-option label="简答题" value="简答题" />
      </el-select>
      <el-button type="primary" style="margin-left:1px" @click="load" >搜索</el-button>

      <el-select v-model="bankName" placeholder="请选择题库" style="width: 12%;margin-left: 40px" clearable>
        <el-option label="公共题库" value="公共题库" />
        <!--        <el-option label="私人题库" value="私人题库" />-->
        <el-option
            v-for="item in dataBankList"
            :key="item"
            :label="item"
            :value="item"
        />
      </el-select>
      <el-button type="primary" style="margin-left:1px" @click="load" >搜索</el-button>

      <el-select v-model="questionLevel" placeholder="请选择难度级别" style="width: 12%;margin-left: 40px" clearable>
        <el-option label="简单" value="简单"></el-option>
        <el-option label="一般" value="一般"></el-option>
        <el-option label="较难" value="较难"></el-option>
        <el-option label="很难" value="很难"></el-option>
      </el-select>
      <el-button type="primary" style="margin-left:1px" @click="load" >搜索</el-button>


      <el-select v-model="courseType" placeholder="请选择课程" style="width: 12%;margin-left: 40px" clearable>
        <el-option label="公共课程" value="公共课程" />
        <el-option
            v-for="item in dataList"
            :label="item"
            :value="item"
        />
      </el-select>
      <el-button type="primary" style="margin-left:1px" @click="load" >搜索</el-button>

      <el-input v-model="questionSubject" placeholder="根据题目内容搜索" style="width: 13%;margin-left: 45px" clearable>
        <template #suffix>
          <el-icon class="el-input__icon"><Search/></el-icon>
        </template>
      </el-input>
      <el-button type="primary" style="margin-left:1px" @click="load" >搜索</el-button>

    </div>


    <!--     全部 含选择题-->
    <el-table :data="tableData" stripe border style="width: 1250px" v-show="showChoiceTable"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="questionId" label="试题编号" width="110" sortable />
      <el-table-column prop="questionType" label="试题类型" width="100" />
      <el-table-column prop="questionLevel" label="难度级别" width="100" />
      <el-table-column prop="courseType" label="课程类型" width="100" />
      <el-table-column prop="questionPoint" label="知识点" width="120" />
      <el-table-column prop="questionSubject" label="题干"  show-overflow-tooltip="true"/>
      <el-table-column prop="optionA" label="选项A" width="100" show-overflow-tooltip="true"/>
      <el-table-column prop="optionB" label="选项B" width="100" show-overflow-tooltip="true"/>
      <el-table-column prop="optionC" label="选项C" width="100" show-overflow-tooltip="true"/>
      <el-table-column prop="optionD" label="选项D" width="100" show-overflow-tooltip="true"/>


    </el-table>
    <!--    其他题-->
    <el-table :data="tableData" stripe border style="width: 100%" v-show="showTable" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="questionId" label="试题编号" width="110" sortable />
      <el-table-column prop="questionType" label="试题类型" width="100" />
      <el-table-column prop="questionLevel" label="难度级别" width="100"/>
      <el-table-column prop="courseType" label="课程类型" width="100" />
      <el-table-column prop="questionPoint" label="知识点" width="120" />
      <el-table-column prop="bankName" label="题库名称" width="90"/>
      <el-table-column prop="questionSubject" label="题干" show-overflow-tooltip="true" />
    </el-table>

    <!--    分页-->
    <div style="margin: 10px 0">
      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20, 30]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

      <div style="margin: 6px 0;">
          <el-form :model="form" status-icon  size="default" ref="form"  label-width="10px" class="demo-ruleForm" >
            <el-row>
              <el-col :span="4">
                <el-form-item label="试卷名称" label-width="70px">
                  <el-input v-model="form.paperName" autocomplete="off" style="width: 200px;" />
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item label="课程名称" label-width="100px">
                  <el-input v-model="form.courseName" autocomplete="off" style="width: 200px;" />
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-button type="primary" style="margin-left:60px" @click="setChoiceQuestion" >加入单选题</el-button>
              </el-col>
              <el-col :span="4">
                <el-form-item label="单选题分数" :label-width="formLabelWidth" >
                  <el-input-number v-model="form.choiceScore" :min="0" :max="100"  />
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-button type="primary" style="margin-left:60px" @click="setMultiQuestion" >加入多选题</el-button>
              </el-col>
              <el-col :span="4">
                <el-form-item label="多选题分数" :label-width="formLabelWidth">
                  <el-input-number v-model="form.multiScore" :min="0" :max="100"  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="2">
                <el-button type="primary"  @click="setBlankFillingQuestion" >加入填空题</el-button>
              </el-col>
              <el-col :span="4">
                <el-form-item label="填空题分数" :label-width="formLabelWidth">
                  <el-input-number v-model="form.blankFillingScore" :min="0" :max="100"  />
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-button type="primary" style="margin-left:60px" @click="setJudgeQuestion" >加入判断题</el-button>
              </el-col>
              <el-col :span="4">
                <el-form-item label="判断题分数" :label-width="formLabelWidth">
                  <el-input-number v-model="form.judgeScore" :min="0" :max="100"  />
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-button type="primary" style="margin-left:60px" @click="setShortQuestion" >加入简答题</el-button>
              </el-col>
              <el-col :span="4">
                <el-form-item label="简答题分数" :label-width="formLabelWidth">
                  <el-input-number v-model="form.shortScore" :min="0" :max="100"  />
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-button type="primary" style="margin-left:60px" @click="createPaper" >生成试卷</el-button>
              </el-col>
            </el-row>
          </el-form>
      </div>
    </div>

  </div>
</template>

<script>
// @ is an alias to /src

import request from "@/utils/request";

export default {
  name: 'HandPaper',
  components: {

  },
  data(){
    return {

      questionType:'单选题',
      bankName:'',
      questionSubject:'',
      courseType:'',
      questionLevel:'',
      currentPage:1,
      total:20,
      pageSize:5,
      small:false,
      background:false,
      disabled:false,
      formLabelWidth :'100px',
      showChoiceTable:true,
      showTable:false,

      tableData: [],
      dataList:[],
      dataBankList:[],
      dataPointList:[],
      ids:[],
      questions:'',

      form:{
        choiceQuestion:'',
        choiceScore:'',
      }
    }
  },
  created() {
    this.load();
    this.getCourseName();
    this.getBankName();
    this.getPointName();
  },
  watch:{
    questionType(){
      if (this.questionType == '填空题' || this.questionType == '判断题' ||
          this.questionType === '简答题') {
        this.showChoiceTable=false;
        this.showTable=true;
      }else {
        this.showChoiceTable=true;
        this.showTable=false;
      }
    }
  },
  methods:{

    // 查询
    load(){
      request.get("/question/queryQuestion", {
        params: {
          pageNum : this.currentPage ,
          pageSize: this.pageSize,
          questionSubject: this.questionSubject,
          questionType: this.questionType,
          bankName: this.bankName,
          courseType: this.courseType,
          questionLevel:this.questionLevel
        }
      }).then(res=>{
        console.log(this.questionType);
        this.total=res.data.total;
        this.tableData=res.data.records;
      })
    },
    handleSelectionChange(val) {
      this.ids = val.map(v => v.questionId);
      // 将所选选项合并为字符串
      this.questions = this.ids.join(',')
    },

    setChoiceQuestion() {
      this.form.choiceQuestion= this.questions;
    },
    setMultiQuestion() {
      this.form.multiQuestion= this.questions;
    },
    setBlankFillingQuestion() {
      this.form.blankFillingQuestion= this.questions;
    },
    setJudgeQuestion() {
      this.form.judgeQuestion= this.questions;
    },
    setShortQuestion() {
      this.form.shortQuestion= this.questions;
    },

    createPaper() {
      console.log(this.form);
      request.post('/paper/save', this.form).then(res => {
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
      })
    },

      //分页
    handleSizeChange(){
      this.load();
    },
    handleCurrentChange(){
      this.load();
    },
    getCourseName(){
      request.get("/course/getCourseName").then(res=>{
        this.dataList=res.data;
      })
    },
    getBankName(){
      request.get("/bank/getBankName").then(res=>{
        this.dataBankList=res.data;
      })
    },
    getPointName(){
      request.get("/point/getPointName").then(res=>{
        this.dataPointList=res.data;
      })
    },
  },
}

</script>

<style scoped>

</style>
