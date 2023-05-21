<template>
  <div class="home" style="padding: 20px">
    <div style="margin: 10px 0">

      <el-input v-model="paperName" placeholder="根据试卷名称查询" style="width: 20%" clearable>
        <template #suffix>
          <el-icon class="el-input__icon"><Search/></el-icon>
        </template>
      </el-input>
      <el-button type="primary" style="margin-left:1px" @click="load" >查询</el-button>

      <el-select v-model="courseType" placeholder="根据课程查询" style="width: 20%;margin-left: 40px" clearable>
        <el-option label="公共课程" value="公共课程" />
        <el-option
            v-for="item in dataList"
            :label="item"
            :value="item"
        />
      </el-select>
      <el-button type="primary" style="margin-left:1px" @click="load" >搜索</el-button>
    </div>

    <el-table :data="tableData" stripe border style="width: 1230px">

      <el-table-column prop="paperId" label="ID" width="70" sortable />
      <el-table-column prop="paperName" label="试卷名称" width="100" />
      <el-table-column prop="paperAuthor" label="出题人" width="100" />
      <el-table-column prop="courseName" label="课程名称" width="100" />
      <el-table-column prop="choiceQuestion" label="单选题" width="150" show-overflow-tooltip="true"/>
      <el-table-column prop="choiceScore" label="单选题分数" width="100" />
      <el-table-column prop="multiQuestion" label="多选题" width="150" show-overflow-tooltip="true"/>
      <el-table-column prop="multiScore" label="多选题分数" width="100" />
      <el-table-column prop="blankFillingQuestion" label="填空题" width="150" show-overflow-tooltip="true"/>
      <el-table-column prop="blankFillingScore" label="填空题分数" width="100" />
      <el-table-column prop="judgeQuestion" label="判断题" width="150" show-overflow-tooltip="true"/>
      <el-table-column prop="judgeScore"  label="判断题分数" width="100" />
      <el-table-column prop="shortQuestion" label="简答题" width="150" show-overflow-tooltip="true"/>
      <el-table-column prop="shortScore"  label="简答题分数" width="100" />
<!--      <el-table-column prop="paperAddress" label="试卷地址" width="100" />-->
<!--      <el-table-column prop="createTime"  label="创建时间" width="200" show-overflow-tooltip="true"/>-->

      <el-table-column fixed="right" label="操作" width="200">
        <template #default="scope">
          <el-button  type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>

          <el-popconfirm title="你确定删除吗?" @confirm="handleDelete(scope.row.paperId)">
            <template #reference="scope">
              <el-button  type="danger" size="small" >删除</el-button>
            </template>
          </el-popconfirm>
          <el-button  type="primary" size="small" @click="look(scope.row)">预览</el-button>
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
        <el-form :model="form">

          <el-row>
            <el-col :span="12">
              <el-form-item label="试卷名称" :label-width="formLabelWidth">
                <el-input v-model="form.paperName" autocomplete="off" style="width: 100%" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="课程名称" :label-width="formLabelWidth">
                <el-input v-model="form.courseName" autocomplete="off" style="width: 70%" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="单选题" :label-width="formLabelWidth">
                <el-input v-model="form.choiceQuestion" autocomplete="off"  />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="单选题分数" :label-width="formLabelWidth">
                <el-input-number v-model="form.choiceScore" :min="0" :max="100"  />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="多选题" :label-width="formLabelWidth">
                <el-input v-model="form.multiQuestion" autocomplete="off" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="多选题分数" :label-width="formLabelWidth">
                <el-input-number v-model="form.multiScore" :min="0" :max="100"  />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="填空题" :label-width="formLabelWidth">
                <el-input v-model="form.blankFillingQuestion" autocomplete="off"  />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="填空题分数" :label-width="formLabelWidth">
                <el-input-number v-model="form.blankFillingScore" :min="0" :max="100"  />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="判断题" :label-width="formLabelWidth">
                <el-input v-model="form.judgeQuestion" autocomplete="off" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="判断题分数" :label-width="formLabelWidth">
                <el-input-number v-model="form.judgeScore" :min="0" :max="100"  />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="简答题" :label-width="formLabelWidth">
                <el-input v-model="form.shortQuestion" autocomplete="off" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="简答题分数" :label-width="formLabelWidth">
                <el-input-number v-model="form.shortScore" :min="0" :max="100"  />
              </el-form-item>
            </el-col>
          </el-row>

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
import LookPaper from "@/views/teacher/LookPaper";

export default {
  name: 'TeacherPaper',
  components: {

  },
  data(){
    return {
      paperName:"",
      courseType:"",
      currentPage:1,
      total:10,
      pageSize:10,
      small:false,
      background:false,
      disabled:false,
      dialogTableVisible:false,
      dialogFormVisible:false,
      formLabelWidth :'140px',
      form:{
        userRole:"学生"
      },
      tableData: [],
      dataList:[]
    }
  },
  created() {
    this.load();
    this.getCourseName();
  },
  methods:{
    load(){
      request.get("/paper/queryPaper",{
        params:{
          pageNum : this.currentPage ,
          pageSize: this.pageSize,
          paperName: this.paperName,
          courseType: this.courseType,

        }
      }).then(res=>{
        this.total=res.data.total;
        this.tableData=res.data.records;
      })
    },
    //编辑
    handleEdit(row){
      this.form=JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
    },
    //查看
    look(row){
      // 跳转到目标页面并传递数据
      // console.log(row);
      // 跳转到目标页面并带上参数
      this.$router.push({ path: '/lookPaper', query: { paper: JSON.stringify(row) } });
    },
    //删除
    handleDelete(val){
      //删除
      request.delete("/paper/"+val).then(
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
    getCourseName(){
      request.get("/course/getCourseName").then(res=>{
        this.dataList=res.data;
      })
    },
    // 保存数据到后端
    save(){
      //编辑
       request.put("/paper",this.form).then(
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
    }
  }
}

</script>

<style scoped>

</style>
