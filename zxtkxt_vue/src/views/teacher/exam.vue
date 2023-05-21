<template>
  <div>
    <el-tabs v-model="activeTab">
      <el-tab-pane label="单选题">
        <el-table :data="choiceQuestions" border>
          <el-table-column label="题目" prop="title"></el-table-column>
          <el-table-column label="选项" prop="options"></el-table-column>
          <el-table-column label="答案" prop="answer"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="多选题">
        <el-table :data="multiQuestions" border>
          <el-table-column label="题目" prop="title"></el-table-column>
          <el-table-column label="选项" prop="options"></el-table-column>
          <el-table-column label="答案" prop="answer"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="填空题">
        <el-table :data="fillBlankQuestions" border>
          <el-table-column label="题目" prop="title"></el-table-column>
          <el-table-column label="答案" prop="answer"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="判断题">
        <el-table :data="trueFalseQuestions" border>
          <el-table-column label="题目" prop="title"></el-table-column>
          <el-table-column label="答案" prop="answer"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="问答题">
        <el-table :data="essayQuestions" border>
          <el-table-column label="题目" prop="title"></el-table-column>
          <el-table-column label="答案" prop="answer"></el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "LookPaper",
  data() {
    return {
      activeTab: "选择题",
      choiceQuestions: [
        {
          title: "下列哪个不是计算机硬件的组成部分？",
          options: "A. 主板  B. 显卡  C. 操作系统  D. 内存",
          answer: "C",
        },
        {
          title: "下列哪种算法不是用来排序的？",
          options: "A. 冒泡排序  B. 选择排序  C. 快速排序  D. 布隆过滤器",
          answer: "D",
        },
      ],
      multiQuestions:[
        {
          title: "下列哪个是计算机硬件的组成部分？",
          options: "A. 主板  B. 显卡  C. 操作系统  D. 内存",
          answer: "A,B,D",
        },
        {
          title: "下列哪种算法空间复杂度是n^2？",
          options: "A. 冒泡排序  B. 选择排序  C. 快速排序  D. 布隆过滤器",
          answer: "A,C",
        }
      ],
      fillBlankQuestions: [
        {
          title: "计算机中最基本的存储单元是__。",
          answer: "字节",
        },
        {
          title: "在C++中，&是什么运算符？",
          answer: "取地址运算符",
        },
      ],
      trueFalseQuestions: [
        {
          title: "Java是一种面向对象的编程语言。",
          answer: "正确",
        },
        {
          title: "计算机中的0和1分别代表“关”和“开”。",
          answer: "正确",
        },
      ],
      essayQuestions: [
        {
          title: "请简述分布式系统的特点和优劣。",
          answer: "分布式系统是指在多台独立计算机上组成的系统，具有分布性、并发性和透明性等特点。优点是能提高系统的"
        },
        {
          title: "请简述分布式系统的特点和优劣。",
          answer: "分布式系统是指在多台独立计算机上组成的系统，具有分布性、并发性和透明性等特点。优点是能提高系统的"
        }
      ]
    }
  }
}
</script>

<style scoped>

</style>

