<template>
  <div id="main">
    <div>
      <h3>一、单选题</h3>
      <div v-for="(question, index) in questions" :key="index">
        <span class="cho">{{index+1}}.{{ question.title }}</span><br/>
        <span class="opt">A. {{ question.optionA }}</span><br/>
        <span class="opt">B. {{ question.optionB }}</span><br/>
        <span class="opt">C. {{ question.optionC }}</span><br/>
        <span class="opt">D. {{ question.optionD }}</span><br/>
      </div>
      <br/>
    </div>
    <div>
      <h3>二、多选题</h3>
      <div v-for="(question, index) in questions" :key="index">
        <span class="cho">{{index+1}}.{{ question.title }}</span><br/>
        <span class="opt">A. {{ question.optionA }}</span><br/>
        <span class="opt">B. {{ question.optionB }}</span><br/>
        <span class="opt">C. {{ question.optionC }}</span><br/>
        <span class="opt">D. {{ question.optionD }}</span><br/>
      </div>
      <br/>
    </div>
    <div>
      <h3>三、填空题</h3>
      <div v-for="(question, index) in questions" :key="index">
        <span class="cho">{{index+1}}.{{ question.title }}</span><br/>
      </div>
      <br/>
    </div>
    <div>
      <h3>四、判断题</h3>
      <div v-for="(question, index) in questions" :key="index">
        <span class="cho">{{index+1}}.{{ question.title }}</span><br/>
      </div>
      <br/>
    </div>
    <div>
      <h3>五、问答题</h3>
      <div v-for="(question, index) in questions" :key="index">
        <span class="cho">{{index+1}}.{{ question.title }}</span><br/>
      </div>
      <br/>
    </div>
  </div>
</template>

<script>
export default {
  name:'LookPaper',
  components: {},

  data() {
    return {
      questions: [
        {
          title: 'Question 1',
          optionA: 'Option A',
          optionB: 'Option B',
          optionC: 'Option C',
          optionD: 'Option D'
        },
        {
          title: 'Question 2',
          optionA: 'Option A',
          optionB: 'Option B',
          optionC: 'Option C',
          optionD: 'Option D'

        }
      ]
    }
  }
}
</script>
<style>
.opt{
  margin-left: 20px;
}
#main{

}
</style>