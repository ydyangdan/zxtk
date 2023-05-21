<template>
  <div class="home" style="padding: 20px">
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请根据题目" style="width: 20%" clearable>
        <template #suffix>
          <el-icon class="el-input__icon"><Search/></el-icon>
        </template>
      </el-input>
      <el-button type="primary" style="margin-left:5px" @click="load" >查询</el-button>
    </div>
    <!--    <img alt="Vue logo" src="../assets/logo.png">-->
    <el-table :data="tableData" stripe border style="width: 90%">

      <el-table-column prop="questionId" label="试题编号" width="100" sortable />
      <el-table-column prop="questionType" label="试题类型" width="100" />
      <el-table-column prop="questionLevel" label="难度级别" width="50" />
      <el-table-column prop="courseType" label="课程类型" width="100" />
      <el-table-column prop="questionPoint" label="知识点" width="100" />
      <el-table-column prop="questionSubject" label="题目" />
      <el-table-column prop="questionAnswer" label="答案" width="100" />
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
          <el-form-item label="试题类型" prop="questionType"  :label-width="formLabelWidth">
            <el-input v-model.number="ruleForm.questionType" ></el-input>
          </el-form-item>
          <el-form-item label="难度级别" prop="questionLevel"  :label-width="formLabelWidth">
            <el-select v-model="ruleForm.questionLevel" placeholder="请选择难度级别">
              <el-option label="简单" value="1"></el-option>
              <el-option label="一般" value="2"></el-option>
              <el-option label="较难" value="3"></el-option>
              <el-option label="很难" value="4"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="课程类型" prop="courseType"  :label-width="formLabelWidth">
            <el-input v-model="ruleForm.courseType" ></el-input>
          </el-form-item>
          <el-form-item label="知识点" prop="questionPoint"  :label-width="formLabelWidth">
            <el-input v-model="ruleForm.questionPoint" ></el-input>
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
  name: 'QueryQuestion',
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
      formLabelWidth :'100px',
      ruleForm:{

      },
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/question/queryQuestion",{
        params:{
          pageNum : this.currentPage ,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res=>{
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

    }
  },
}

</script>

<style scoped>

</style>
