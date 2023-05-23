<template>
  <div id="main">
    <!-- 这里就是要变成图片的容器 -->
    <div id="pdfHtml" ref="printHtml" style="margin-left: 100px; width: 70%;">
      <div style="margin-left:200px;">
        <h3>参考答案</h3>
      </div>
      <div>
        <h5>一、单选题</h5>
        <div v-for="(choice, index) in choiceList" :key="index" style="width: 100% ;">
          <span class="cho">{{index+1}}.{{ choice.questionAnswer}}</span>
        </div>
        <br/>
      </div>

      <div>
        <h5>二、多选题</h5>
        <div v-for="(multi, index) in multiList" :key="index">
          <span class="cho">{{index+1}}.{{ multi.questionAnswer }}</span>
        </div>
        <br/>
      </div>
      <div>
        <h5>三、填空题</h5>
        <div v-for="(blank, index) in blankList" :key="index">
          <span class="cho">{{index+1}}.{{ blank.questionAnswer }}</span>
        </div>
        <br/>
      </div>
      <div>
        <h5>四、判断题</h5>
        <div v-for="(judge, index) in judgeList" :key="index">
          <span class="cho">{{index+1}}.{{ judge.questionAnswer }}</span><br/>
        </div>
        <br/>
      </div>
      <div>
        <h5>五、问答题</h5>
        <div v-for="(short, index) in shortList" :key="index">
          <span class="cho">{{index+1}}.{{ short.questionAnswer }}</span><br/>
        </div>
        <br/>
      </div>
    </div>
    <el-button type="primary" @click="exportPDF" style="margin-left: 200px" plain>导出PDF</el-button>
  </div>
</template>

<script>
import {getPdf} from "@/utils/exportPdf";
export default {
  name: "LookAnswers",
  data(){
    return{
      choiceList:[],
      multiList:[],
      blankList:[],
      judgeList:[],
      shortList:[],
    }
  },
  created() {
    let result=JSON.parse(this.$route.query.data);
    this.choiceList=result.choiceList;
    this.multiList=result.multiList;
    this.blankList=result.blankList;
    this.judgeList=result.judgeList;
    this.shortList=result.shortList;

  },
  methods:{
    // 导出PDF
    exportPDF() {
      getPdf("参考答案", 'pdfHtml');
    },
  }
}
</script>

<style scoped>
.cho{
  margin-left: 20px;
}
</style>