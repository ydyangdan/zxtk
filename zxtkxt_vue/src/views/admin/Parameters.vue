<template>
  <div id="cc">
    <div id="app">
      <div style="flex: 1;margin-left: 40px">您可以修改蚁群算法的参数：</div>
      <br/>
      <el-form :model="form" status-icon  size="large" ref="form" label-width="100px"  style="margin-left: 40px">
        <el-form-item label="蚁群数量"  prop="antCount"  :label-width="formLabelWidth" >
          <el-input-number v-model="form.antCount" :min="0"   />
        </el-form-item>

        <el-form-item label="迭代次数"  prop="iterationCount"  :label-width="formLabelWidth" >
          <el-input-number v-model="form.iterationCount" :min="0"  />
        </el-form-item>

        <el-form-item label="信息素挥发系数"  prop="rho"  :label-width="formLabelWidth" >
          <el-input-number v-model="form.rho" :min="0" :max="1"  />
        </el-form-item>

        <el-form-item label="信息素重要程度"  prop="alpha"  :label-width="formLabelWidth" >
          <el-input-number v-model="form.alpha" :min="0" :max="1"  />
        </el-form-item>

        <el-form-item label="启发因子"  prop="beta"  :label-width="formLabelWidth" >
          <el-input-number v-model="form.beta" :min="0" :max="1"  />
        </el-form-item>
        <el-form-item label="上次修改时间"   :label-width="formLabelWidth" >
          <el-input v-model="form.createTime"   style="width: 200px" disabled/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="put()" style="width: 100px;margin-left:50px">保存</el-button>
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
  name: "Parameters",
  data() {
    return {
      dataList:[],
      formLabelWidth:200,
      form: {
       alpha: '',
       antCount: '',
       beta:'',
       createTime:'',
       id: '1',
       iterationCount:200,
       rho:0.6
      },
    };
  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    put() {
          request.put('/parameters', this.form).then(res => {
            if (res.code == 0) {
              //小提示
              this.$message({
                type: "success",
                message: res.msg
              });
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

          });
    },

    getParameters(){
      request.get("/parameters/queryParameters").then(res=>{
        console.log(res.data)
        this.form=res.data;
      })
    },

  },
  created() {
    this.getParameters();
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
