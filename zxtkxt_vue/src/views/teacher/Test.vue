<template>
  <div>
<!--     为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="echarts" style="width: 600px;height:400px;"></div>
    <input type="button" @click="mcharts" value="显示图表">
  </div>

</template>

<script>
import * as echarts from 'echarts';
export default {
  name: "AnalysePaper",
  created() {
    this.paper=this.$route.query.paper;
    alert(this.paper.paperName);
  },
  methods:{
    mcharts(){
      // <!-- 基于准备好的dom，初始化echarts实例 -->
      const myChart = echarts.init(document.getElementById('echarts'));
      // <!-- 指定图表的配置项和数据 -->
      const option = {
        title: {
          text: '第一个 ECharts 实例'
        },
        tooltip: {},
        xAxis: {    //  x轴的下标
          data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
        },
        yAxis: {    //  y轴
          type: 'value'
        },
        series: [{   //  y轴的数据
          name: '销量',
          type: 'line',    //  bar为柱状图，line为折线图，pie为饼图
          data: [5, 20, 36, 10, 10, 20]
        }]
      };
      // <!-- 使用刚指定的配置项和数据显示图表 -->
      myChart.setOption(option)
    }
  }
}
</script>

<style scoped>

</style>


