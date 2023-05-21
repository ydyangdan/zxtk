<template>
  <div class="home" style="padding: 20px">
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      
      <el-input v-model="search" placeholder="根据用户名查询" style="width: 20%;margin-left: 40px" clearable>
        <template #suffix>
          <el-icon class="el-input__icon"><Search/></el-icon>
        </template>
      </el-input>
      <el-button type="primary" style="margin-left:1px" @click="load" >查询
      </el-button>
    </div>

    <el-table :data="tableData" stripe border style="width: 90%">

      <el-table-column prop="userId" label="ID" width="100" sortable />
      <el-table-column prop="userName" label="用户名" width="100" />
      <el-table-column prop="password" label="密码" width="100" />
      <el-table-column prop="userRole" label="用户身份" width="100" />
      <el-table-column prop="contact" label="联系方式" width="100" />
      <el-table-column prop="userSex" label="用户性别" width="100" />
      <el-table-column prop="userAge" label="用户年龄" width="100" />
      <el-table-column prop="createTime" label="创建时间" width="150"/>
      <el-table-column prop="latestLoginTime" label="最新登录时间" />


      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button  type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="你确定删除吗?" @confirm="handleDelete(scope.row.userId)">
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
      <el-dialog v-model="dialogFormVisible" >
        <el-form :model="form" ref="from" status-icon>
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-input v-model="form.userName" autocomplete="off" style="width: 80%" />
          </el-form-item>
          <el-form-item label="密码" prop="password" :label-width="formLabelWidth">
            <el-input type="password" v-model="form.password"  style="width: 80%" show-password clearable></el-input>
          </el-form-item>
          <el-form-item label="联系方式" :label-width="formLabelWidth">
            <el-input v-model="form.contact" autocomplete="off" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="年龄" :label-width="formLabelWidth">
            <el-input-number v-model="form.userAge" :min="1" :max="100"  style="width: 80%"/>
          </el-form-item>
          <el-form-item label="用户身份" prop="userRole" :label-width="formLabelWidth">
            <el-radio-group v-model="form.userRole">
              <el-radio label="教师"></el-radio>
              <el-radio label="管理员"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="用户性别" prop="userSex" :label-width="formLabelWidth">
            <el-radio-group v-model="form.userSex">
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
              <el-radio label="未知"></el-radio>
            </el-radio-group>
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
  name: 'User',
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
      form:{
        userName:'',
        userAge:'',
        userRole:'教师',
        userSex:'未知',
        contact:'',
        password:'123456'
      },
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/user/queryUser",{
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
      request.delete("/user/"+val).then(
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
      this.form={
        userRole:'教师',
        userSex:'未知',
        password:'123456'
      };
    },
    // 保存数据到后端
    save(){
      if (this.form.userId){
        //编辑
        request.put("/user",this.form).then(
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
        request.post("/user/save",this.form).then(
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