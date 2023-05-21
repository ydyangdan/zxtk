<template>
  <div>
    <div style="display: flex">
      <!--     为ECharts准备一个具备大小（宽高）的Dom -->
      <div id="echarts" style="width: 500px;height:400px;"></div>
      <!--     为ECharts准备一个具备大小（宽高）的Dom -->
      <div id="charts" style="width: 500px;height:400px;"></div>
    </div>
    <div>
      <el-row :gutter="20">
        <el-col :span="1"></el-col>
        <el-col :span="10">
           <h4>知识点覆盖度：{{this.paper.pointCoverage}}，未包含的知识点：{{resultInclude}}</h4>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="8">
           <h4>试卷难度：{{this.paper.paperLevel}}</h4>
        </el-col>
      </el-row>
      <el-row :gutter="10" style="margin-top: 30px">
        <el-col :span="1"></el-col>
        <el-col :span="12">
          <h4>试卷相似度：{{this.paper.repeatability}}</h4>
        </el-col>
      </el-row>
    </div>
  </div>

</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request";
export default {
  name: "AnalysePaper",
  data(){
    return{
      resultPoint:[],
      resultLevel:[],
      dataPointList:[],
      resultInclude:[],
      paper:{}
    }
  },
  created() {
    let result=JSON.parse(this.$route.query.data);
    this.resultPoint=result.resultPoint;
    this.resultLevel=result.resultLevel;
    this.paper=result.paper;
    this.getPointName();
  },
  mounted(){
     this.mcharts();
     this.charts();
  },
  methods:{
    mcharts(){
      // <!-- 基于准备好的dom，初始化echarts实例 -->
      const myChart = echarts.init(document.getElementById('echarts'));
      // <!-- 指定图表的配置项和数据 -->
      const option = {
        title: {
          text: '知识点分布情况',
          x:'center'
        },
        tooltip: {},
        // legend: {
        //   orient: 'vertical',
        //   left: 10,
        //   data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        // },
        series: [
          {
            // name: '访问来源',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: this.resultPoint,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      // <!-- 使用刚指定的配置项和数据显示图表 -->
      myChart.setOption(option)
    },
    charts(){
      // <!-- 基于准备好的dom，初始化echarts实例 -->
      const myChart = echarts.init(document.getElementById('charts'));
      // <!-- 指定图表的配置项和数据 -->
      const option = {
        title: {
          text: '难度分布',
          x:'center'
        },
        tooltip: {},
        series: [
          {
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: this.resultLevel,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      // <!-- 使用刚指定的配置项和数据显示图表 -->
      myChart.setOption(option)
    },
    getPointName(){
      request.get("/point/getPointName",{ params: {
          courseType: this.paper.courseName
        }}).then(res=>{
        this.dataPointList=res.data;
      })
    }
  },
  computed: {
    resultInclude() {
      // 对原始数据进行加工处理
      // 首先对arr1进行深拷贝，避免直接修改原数组
      let arr1 = JSON.parse(JSON.stringify(this.dataPointList));
      const arr2 = this.resultPoint.map(obj => obj.name);

      // 遍历arr2，将其中的元素在resultArr中找到相应的元素，做差并更新resultArr
      let resultInclude = arr1.filter(item => !arr2.includes(item));
      return resultInclude;
    }
  }
}
</script>

<style scoped>

</style>
