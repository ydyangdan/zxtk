<template>
  <div class="home" style="padding: 7px">
    <div style="margin: 6px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-input v-model="search" placeholder="根据课程名称查询" style="width: 20%;margin-left: 60px" clearable>
        <template #suffix>
          <el-icon class="el-input__icon"><Search/></el-icon>
        </template>
      </el-input>
      <el-button type="primary" style="margin-left:1px" @click="load" >查询</el-button>
    </div>
    <!--    <img alt="Vue logo" src="../assets/logo.png">-->
    <el-table :data="tableData" stripe border style="width: 90%">

      <el-table-column prop="courseId" label="ID" width="100" sortable />
      <el-table-column prop="courseNumber" label="课程编号" width="150" />
      <el-table-column prop="courseName" label="课程名称" width="250" />
      <el-table-column prop="courseTeacher" label="任课教师" width="150" />
      <el-table-column prop="courseType" label="课程类型" width="200" />
      <el-table-column prop="createTime" label="创建时间" />

      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button  type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="你确定删除吗?" @confirm="handleDelete(scope.row.courseId)">
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
      <el-dialog v-model="dialogFormVisible" title="">
        <el-form :model="form">

          <el-form-item label="课程编号" :label-width="formLabelWidth">
            <el-input v-model="form.courseNumber" autocomplete="off" style="width: 80%" />
          </el-form-item>
          <el-form-item label="课程名称" :label-width="formLabelWidth">
            <el-input v-model="form.courseName" autocomplete="off" style="width: 80%"/>
          </el-form-item>
<!--          <el-form-item label="任课教师" :label-width="formLabelWidth">-->
<!--            <el-input v-model="form.courseTeacher" autocomplete="off" style="width: 80%"/>-->
<!--          </el-form-item>-->
          <el-form-item label="课程类型" :label-width="formLabelWidth">
            <el-input v-model="form.courseType" autocomplete="off" style="width: 80%"/>
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
  name: 'TeacherCourse',
  components: {

  },
  data(){
    return {
      search:"",
      currentPage:1,
      total:20,
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
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/course/queryCourse",{
        params:{
          pageNum : this.currentPage ,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res=>{
        console.log(res);
        this.total=res.data.total;
        this.tableData=res.data.records;
      })
    },
    handleEdit(row){
      this.form=JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
    },
    //删除
    handleDelete(val){
      //删除
      request.delete("/course/"+val).then(
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
    add(){
      this.dialogFormVisible = true;
      this.form={};
    },
    // 保存数据到后端
    save(){
      if (this.form.courseId){
        //编辑
        request.put("/course",this.form).then(
            res=>{
              if (res.code == 0){
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

      }else {  //新增
        request.post("/course/save",this.form).then(
            res=>{
              alert(res.msg);
              this.load();
              this.dialogFormVisible = false;
            }
        )
      }
    }
  },
}

</script>

<style scoped>

</style>

