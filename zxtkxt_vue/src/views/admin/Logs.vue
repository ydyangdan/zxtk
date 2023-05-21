<template>
  <div class="home" style="padding: 7px">

    <el-table :data="tableData" stripe border style="width: 1300px">

      <el-table-column prop="id" label="ID" width="100" sortable />
      <el-table-column prop="userName" label="用户名称" width="150" />
      <el-table-column prop="paperName" label="试卷名称" width="100" />
      <el-table-column prop="autoType" label="组卷方式" width="100" />
      <el-table-column prop="paperDuration" label="组卷时长(秒)" width="120" />
      <el-table-column prop="fitness" label="适应度" width="100" />
      <el-table-column prop="difficulty" label="实际难度" width="100" />
      <el-table-column prop="pointCoverage" label="实际知识点覆盖度" width="100" />
      <el-table-column prop="repeatability" label="实际相似度" width="100" />
      <el-table-column prop="targetDifficulty" label="目标难度" width="100" />
      <el-table-column prop="targetPointCoverage" label="目标知识点覆盖度" width="100" />
      <el-table-column prop="targetRepeatability" label="目标相似度" width="100" />
      <el-table-column prop="operateTime" label="创建时间" width="170" />

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

    </div>

  </div>
</template>

<script>
// @ is an alias to /src

import request from "@/utils/request";

export default {
  name: 'Logs',
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
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/logs/queryLogs",{
        params:{
          pageNum : this.currentPage ,
          pageSize: this.pageSize
        }
      }).then(res=>{
        console.log(res);
        this.total=res.data.total;
        this.tableData=res.data.records;
      })
    },

    //分页
    handleSizeChange(){
      this.load();
    },
    handleCurrentChange(){
      this.load();
    }
  },
}

</script>

<style scoped>

</style>
