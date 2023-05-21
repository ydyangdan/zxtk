<template>
  <div class="home" style="padding: 7px">
    <div style="margin: 6px 0">
      <el-button type="primary" size="default" @click="add">新增</el-button>
      <el-select v-model="search" placeholder="请选择课程" style="width: 20%;margin-left: 60px" clearable>
        <el-option label="公共" value="公共" />
        <el-option
            v-for="item in dataList"
            :key="item.id"
            :label="item.title"
            :value="item.id"
        />
      </el-select>
      <el-button type="primary" style="margin-left:0px" @click="load" >查询</el-button>
    </div>
    <!--    <img alt="Vue logo" src="../assets/logo.png">-->
    <el-table :data="tableData" stripe border style="width: 1250px">

      <el-table-column prop="templateId" label="编号" width="100" sortable />
      <el-table-column prop="choiceCount" label="单选题数量" width="100" />
      <el-table-column prop="choiceScore" label="单选题分数" width="100" />
      <el-table-column prop="multiCount" label="多选题数量" width="100" />
      <el-table-column prop="multiScore" label="多选题分数" width="100" />
      <el-table-column prop="blankFillingCount" label="填空题数量" width="100" />
      <el-table-column prop="blankFillingScore" label="填空题分数" width="100" />
      <el-table-column prop="judgeCount" label="判断题数量" width="100" />
      <el-table-column prop="judgeScore" label="判断题分数" width="100" />
      <el-table-column prop="shortCount" label="简答题数量" width="100" />
      <el-table-column prop="shortScore" label="简答题分数" width="100" />
      <el-table-column prop="pointCoverage" label="知识点覆盖度" width="120" />
      <el-table-column prop="paperLevel" label="试卷难度" width="100" />
      <el-table-column prop="repeatability" label="试卷相似度" width="100" />

      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button  type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="你确定删除吗?" @confirm="handleDelete(scope.row.templateId)">
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
        <el-form :model="form">

          <el-row>
            <el-col :span="12">
              <el-form-item label="单选题数量" :label-width="formLabelWidth">
                <el-input-number v-model="form.choiceCount" :min="0" :max="100"  />
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
                <el-form-item label="多选题数量" :label-width="formLabelWidth">
                  <el-input-number v-model="form.multiCount" :min="0" :max="100"  />
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
              <el-form-item label="填空题数量" :label-width="formLabelWidth">
                <el-input-number v-model="form.blankFillingCount" :min="0" :max="100"  />
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
              <el-form-item label="判断题数量" :label-width="formLabelWidth">
                <el-input-number v-model="form.judgeCount" :min="0" :max="100"  />
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
              <el-form-item label="简答题数量" :label-width="formLabelWidth">
                <el-input-number v-model="form.shortCount" :min="0" :max="100"  />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="简答题分数" :label-width="formLabelWidth">
                <el-input-number v-model="form.shortScore" :min="0" :max="100"  />
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="知识点覆盖度" :label-width="formLabelWidth">
            <el-input  type="number"  v-model.trim="form.pointCoverage" :min="0" :max="1" :precision="2"  style="width: 50%" />
          </el-form-item>
          <el-form-item label="难度级别"  :label-width="formLabelWidth">
            <el-select v-model="form.paperLevel" placeholder="请选择难度级别" style="width: 50%">
              <el-option label="容易" value="容易"></el-option>
              <el-option label="较易" value="较易"></el-option>
              <el-option label="中等" value="中等"></el-option>
              <el-option label="较难" value="较难"></el-option>
              <el-option label="很难" value="很难"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="试卷相似度" :label-width="formLabelWidth">
            <el-input  type="number"  v-model.trim="form.repeatability" :min="0" :max="1" :precision="2"  style="width: 50%" />
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
  name: 'PaperTemplate',
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
        paperLevel:''
      },
      tableData: [],
      dataList:[
        {id:1,title:'eeee'},
        {id:2,title:'sds'},
        {id:3,title:'edc'},
      ]
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/template/queryTemplate",{
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
      request.delete("/template/"+val).then(
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
      if (this.form.templateId){
        //编辑
        request.put("/template",this.form).then(
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
        request.post("/template/save",this.form).then(
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
  },
}

</script>

<style scoped>

</style>
