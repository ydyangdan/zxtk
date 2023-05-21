<template>
  <div class="home" style="padding: 7px">
    <div style="margin: 6px 0">
      <el-button type="primary" @click="add" size="large">新增</el-button>
      <el-input v-model="search" placeholder="根据题库名查询" size="large" style="width: 20%;margin-left: 50px" clearable>
        <template #suffix>
          <el-icon class="el-input__icon"><Search/></el-icon>
        </template>
      </el-input>
      <el-button type="primary" style="margin-left:1px" @click="load" size="large">查询</el-button>
    </div>
    <!--    <img alt="Vue logo" src="../assets/logo.png">-->
    <el-table :data="tableData" stripe border style="width: 98%">

      <el-table-column prop="bankId" label="ID" width="70" sortable />
      <el-table-column prop="bankName" label="题库名称" width="150" />
      <el-table-column prop="choiceCount" label="单选题数量" width="100" />
      <el-table-column prop="multiCount" label="多选题数量" width="100" />
      <el-table-column prop="blankFillingCount" label="填空题数量" width="100" />
      <el-table-column prop="judgeCount" label="判断题数量" width="100" />
      <el-table-column prop="shortCount" label="简答题数量" width="100" />
      <el-table-column prop="userName" label="题库所有者" width="100" />
      <el-table-column prop="bankRemark" label="题库备注" width="100" />
      <el-table-column prop="createTime" label="创建时间" />

      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button  type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="你确定删除吗?" @confirm="handleDelete(scope.row.bankId)">
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

      <!--      &lt;!&ndash; Form &ndash;&gt;-->
      <!--      <el-button text @click="dialogFormVisible = true">-->
      <!--        open a Form nested Dialog-->
      <!--      </el-button>-->
      <!--     弹窗-->
      <el-dialog v-model="dialogFormVisible" title="新增">
        <el-form :model="form" :rules="rules"  ref="form" >
          <el-form-item label="题库名称" :label-width="formLabelWidth" prop="bankName">
            <el-input v-model="form.bankName" autocomplete="off" style="width: 80%" />
          </el-form-item>
          <el-form-item label="题库备注" :label-width="formLabelWidth">
            <el-input v-model="form.bankRemark" autocomplete="off" style="width: 80%"/>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save('form')">
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
  name: 'TeacherQuestionBank',
  components: {

  },
  data(){
    var checkBankName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('题库名称不能为空'));
      }
      callback();
    };
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

      },
      tableData: [],
      rules: {
        bankName: [
          { validator: checkBankName,required: true, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/bank/queryQuestionBank",{
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
      this.form=JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
    },
    //删除
    handleDelete(val){
      //删除
      request.delete("/bank/"+val).then(
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
    save(form){
      if (this.form.bankId){
        //编辑
        request.put("/bank",this.form).then(
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

      }else {  //新增

          this.$refs[form].validate((valid) => {
            if (valid) {
              request.post("/bank/save",this.form).then(
                  res=>{
                    alert(res.msg);
                    this.load();
                    this.dialogFormVisible = false;
                  }
              ).catch(error => {
                // 处理错误
              })
            } else {
              console.log('error submit!!');
              return false;
            }
          });

      }
    }
  },
}

</script>

<style scoped>

</style>
