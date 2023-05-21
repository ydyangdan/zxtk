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
        <el-option label="容易" value="容易"></el-option>
        <el-option label="较易" value="较易"></el-option>
        <el-option label="中等" value="中等"></el-option>
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

    <!--    <img alt="Vue logo" src="../assets/logo.png">-->
<!--     全部 含选择题-->
    <el-table :data="tableData" stripe border style="width: 1250px" v-show="showChoiceTable">

      <el-table-column prop="questionId" label="试题编号" width="110" sortable />
      <el-table-column prop="questionType" label="试题类型" width="100" />
      <el-table-column prop="questionLevel" label="难度级别" width="100" />
      <el-table-column prop="courseType" label="课程类型" width="100" />
      <el-table-column prop="questionPoint" label="知识点" width="120" />
      <el-table-column prop="bankName" label="题库名称" width="100"/>
      <el-table-column prop="questionSubject" label="题干" width="100" show-overflow-tooltip="true"/>
      <el-table-column prop="optionA" label="选项A" width="100" show-overflow-tooltip="true"/>
      <el-table-column prop="optionB" label="选项B" width="100" show-overflow-tooltip="true"/>
      <el-table-column prop="optionC" label="选项C" width="100" show-overflow-tooltip="true"/>
      <el-table-column prop="optionD" label="选项D" width="100" show-overflow-tooltip="true"/>
      <el-table-column prop="questionAnswer" label="答案" width="100" show-overflow-tooltip="true"/>
      <el-table-column prop="questionImage" label="图片" width="100">
        <template #default="scope">
          <el-image
              style="width: 100px; height: 60px"
              :src="scope.row.questionImage"
              preview-teleported
              :preview-src-list="[scope.row.questionImage]"
          >
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="questionDesc" label="描述" width="100" show-overflow-tooltip="true"/>
<!--      <el-table-column prop="createTime" label="创建时间" width="150" show-overflow-tooltip="true"/>-->

      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button  type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="你确定删除吗?" @confirm="handleDelete(scope.row.questionId)">
            <template #reference="scope">
              <el-button  type="danger" size="small" >删除</el-button>
            </template>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>
<!--    其他题-->
    <el-table :data="tableData" stripe border style="width: 100%" v-show="showTable">

      <el-table-column prop="questionId" label="试题编号" width="110" sortable />
      <el-table-column prop="questionType" label="试题类型" width="100" />
      <el-table-column prop="questionLevel" label="难度级别" width="100"/>
      <el-table-column prop="courseType" label="课程类型" width="100" />
      <el-table-column prop="questionPoint" label="知识点" width="120" />
      <el-table-column prop="bankName" label="题库名称" width="90"/>
      <el-table-column prop="questionSubject" label="题干" show-overflow-tooltip="true" />
      <el-table-column prop="questionAnswer" label="答案" width="100"  show-overflow-tooltip="true"/>
      <el-table-column prop="questionImage" label="图片" width="100">
        <template #default="scope">
          <el-image
              style="width: 100px; height: 60px"
              :src="scope.row.questionImage"
              preview-teleported
              :preview-src-list="[scope.row.questionImage]"
          >
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="questionDesc" label="描述" width="90" show-overflow-tooltip="true"/>
      <el-table-column prop="createTime" label="创建时间" width="150"/>

      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button  type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="你确定删除吗?" @confirm="handleDelete(scope.row.questionId)">
            <template #reference="scope">
              <el-button  type="danger" size="small" >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
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

      <!--     弹窗-->
      <el-dialog v-model="dialogFormVisible" title="编辑">
        <el-form :model="ruleForm" style="width: 80%" >
          <el-form-item label="题目" prop="questionSubject"  :label-width="formLabelWidth">
            <el-input v-model="ruleForm.questionSubject" type="textarea" autocomplete="off" />
          </el-form-item>
          <el-form-item label="答案" prop="questionAnswer"  :label-width="formLabelWidth">
            <el-input v-model="ruleForm.questionAnswer" type="textarea" />
          </el-form-item>
          <el-form-item label="难度级别" prop="questionLevel"  :label-width="formLabelWidth">
            <el-select v-model="ruleForm.questionLevel" placeholder="请选择难度级别">
              <el-option label="容易" value="容易"></el-option>
              <el-option label="较易" value="较易"></el-option>
              <el-option label="中等" value="中等"></el-option>
              <el-option label="较难" value="较难"></el-option>
              <el-option label="很难" value="很难"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="知识点" prop="questionPoint"  :label-width="formLabelWidth">
            <el-select v-model="ruleForm.questionPoint" placeholder="请选择知识点"  clearable>
              <el-option
                  v-for="item in dataPointList"
                  :label="item"
                  :value="item"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="save">
              确认
            </el-button>
          </span>
        </template>
      </el-dialog>

    </div>

  </div>
</template>

<script>
// @ is an alias to /src

import request from "@/utils/request";

export default {
  name: 'TeacherQueryQuestion',
  components: {

  },
  data(){
    return {

      questionType:'',
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
      dialogTableVisible:false,
      dialogFormVisible:false,
      formLabelWidth :'100px',
      showChoiceTable:true,
      showTable:false,
      ruleForm:{

      },
      tableData: [],
      dataList:[],
      dataBankList:[],
      dataPointList:[]
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
    handleEdit(row){
      this.ruleForm=JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
    },
    //删除
    handleDelete(val){
      //删除
      request.delete("/question/"+val).then(
          res=>{
            if (res.code==0){
              //小提示
              this. $message({
                type : "success" ,
                message:res.msg
              })
              this.load();
            }else {
              //小提示
              this. $message({
                type : "error" ,
                message:res.msg
              })
            }
          }
      )
    },
    //分页
    handleSizeChange(){
      this.load();
    },
    handleCurrentChange(){
      this.load();
    },
    // 保存数据到后端
    save(){
      //编辑
      request.put("/question",this.ruleForm).then(
          res=>{
            if (res.code==0){
              //小提示
              this. $message({
                type : "success" ,
                message:res.msg
              })
              this.load();
              this.dialogFormVisible = false;
            }else {
              //小提示
              this. $message({
                type : "error" ,
                message:res.msg
              })
              this.dialogFormVisible = false;
            }
          }
      )

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
