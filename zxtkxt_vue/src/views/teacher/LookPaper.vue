<template>
  <div id="main">
    <!-- 这里就是要变成图片的容器 -->
    <div id="pdfHtml" ref="printHtml" style="margin-left: 100px; width: 70%;">
        <div style="margin-left:300px;">
          <h2>{{paper.paperName}}</h2>
        </div>
        <div>
          <h4>一、单选题(每题{{paper.choiceScore}}分)</h4>
          <div v-for="(choice, index) in choiceList" :key="index">
            <span class="cho">{{index+1}}.{{ choice.questionSubject}}</span><br/>
            <span class="opt">A. {{ choice.optionA }}</span><br/>
            <span class="opt">B. {{ choice.optionB }}</span><br/>
            <span class="opt">C. {{ choice.optionC }}</span><br/>
            <span class="opt">D. {{ choice.optionD }}</span><br/>
          </div>
          <br/>
        </div>

        <div>
          <h4>二、多选题(每题{{paper.multiScore}}分)</h4>
          <div v-for="(multi, index) in multiList" :key="index">
            <span class="cho">{{index+1}}.{{ multi.questionSubject }}</span><br/>
            <span class="opt">A. {{ multi.optionA }}</span><br/>
            <span class="opt">B. {{ multi.optionB }}</span><br/>
            <span class="opt">C. {{ multi.optionC }}</span><br/>
            <span class="opt">D. {{ multi.optionD }}</span><br/>
          </div>
          <br/>
        </div>
        <div>
          <h4>三、填空题(每题{{paper.blankFillingScore}}分)</h4>
          <div v-for="(blank, index) in blankList" :key="index">
            <span class="cho">{{index+1}}.{{ blank.questionSubject }}</span><br/>
          </div>
          <br/>
        </div>
        <div>
          <h4>四、判断题(每题{{paper.judgeScore}}分)</h4>
          <div v-for="(judge, index) in judgeList" :key="index">
            <span class="cho">{{index+1}}.{{ judge.questionSubject }}</span><br/>
          </div>
          <br/>
        </div>
        <div>
          <h4>五、问答题(每题{{paper.shortScore}}分)</h4>
          <div v-for="(short, index) in shortList" :key="index">
            <span class="cho">{{index+1}}.{{ short.questionSubject }}</span><br/>
          </div>
          <br/>
        </div>
    </div>
    <el-button type="primary" @click="exportPDF" style="margin-left: 200px" plain>导出PDF</el-button>
    <el-button type="primary"  style="margin-left: 100px" @click="analyse" plain> 试卷分析</el-button>
  </div>

</template>

<script>
import request from "@/utils/request";
import {getPdf} from "@/utils/exportPdf";
import echarts from "echarts";

export default {
  name:'LookPaper',
  components: {},

  data() {
    return {
      paper:{
        paperName:'',
        choiceQuestion:'',
        multiQuestion:'',
        blankFillingQuestion:'',
        judgeQuestion:'',
        shortQuestion:'',
        choiceScore:'',
        multiScore:'',
        blankFillingScore:'',
        judgeScore:'',
        shortScore:''
      },
      choiceList:[],
      multiList:[],
      blankList:[],
      judgeList:[],
      shortList:[],

    }
  },
  created() {
    this.paper=JSON.parse(this.$route.query.paper);
    // alert(this.paper.paperName);
    this.getChoiceQuestion();
    this.getMultiQuestion();
    this.getBlankFillingQuestion();
    this.getJudgeQuestion();
    this.getShortQuestion();
  },
  methods:{
    getChoiceQuestion(){
      request.get("/question/getQuestions",{
        params:{
          str: this.paper.choiceQuestion
        }
      }).then(res=>{
        this.choiceList=res.data;
        // console.log(this.choiceList)
      })
    },
    getMultiQuestion(){
      request.get("/question/getQuestions",{
        params:{
          str: this.paper.multiQuestion
        }
      }).then(res=>{
        this.multiList=res.data;
      })
    },
    getBlankFillingQuestion(){
      request.get("/question/getQuestions",{
        params:{
          str: this.paper.blankFillingQuestion
        }
      }).then(res=>{
        this.blankList=res.data;
        console.log(this.choiceList);
      })
    },
    getJudgeQuestion(){
      request.get("/question/getQuestions",{
        params:{
          str: this.paper.judgeQuestion
        }
      }).then(res=>{
        this.judgeList=res.data;
      })
    },
    getShortQuestion(){
      request.get("/question/getQuestions",{
        params:{
          str: this.paper.shortQuestion
        }
      }).then(res=>{
        this.shortList=res.data;
      })
    },
    // 导出PDF
    exportPDF() {
      getPdf(this.paper.paperName, 'pdfHtml');
    },

    // 试卷分析
    analyse(){
      let data = this.choiceList.concat(this.multiList, this.blankList,this.judgeList,this.shortList);
      console.log(data);

      const resultPoint = Object.entries(data.reduce((acc, cur) => {
        const count = acc[cur.questionPoint] || 0;
        return {...acc, [cur.questionPoint]: count + 1};
      }, {})).map(([name, value]) => ({name, value}));

      const resultLevel = Object.entries(data.reduce((acc, cur) => {
        const count = acc[cur.questionLevel] || 0;
        return {...acc, [cur.questionLevel]: count + 1};
      }, {})).map(([name, value]) => ({name, value}));
      // 跳转到目标页面并带上参数
      this.$router.push({ path: '/analysePaper', query: { data:JSON.stringify({'resultPoint':resultPoint,'resultLevel':resultLevel,'paper':this.paper})}  });
    },

  }
}
</script>
<style>
.cho{
  margin-left: 20px;
}
.opt{
  margin-left: 40px;
}
#main{

}

</style>
