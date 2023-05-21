<template>
  <div id="cc">
    <div id="app">
      <el-form :model="ruleForm" status-icon :rules="rules" size="default" ref="ruleForm"  label-width="100px" class="demo-ruleForm" >
        <el-row>
          <el-col :span="12">
              <el-form-item label="试题类型" prop="questionType">
                <el-select v-model="ruleForm.questionType" placeholder="请选择试题类型">
                  <el-option label="单选题" value="单选题" />
                  <el-option label="多选题" value="多选题" />
                  <el-option label="填空题" value="填空题" />
                  <el-option label="判断题" value="判断题" />
                  <el-option label="简答题" value="简答题" />
                </el-select>
              </el-form-item>
          </el-col>
          <el-col :span="12">
              <el-form-item label="难度级别" prop="questionLevel"  >
                <el-select v-model="ruleForm.questionLevel" placeholder="请选择难度级别">
                  <el-option label="容易" value="容易"></el-option>
                  <el-option label="较易" value="较易"></el-option>
                  <el-option label="中等" value="中等"></el-option>
                  <el-option label="较难" value="较难"></el-option>
                  <el-option label="很难" value="很难"></el-option>
                </el-select>
              </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="归属题库" prop="bankName"  >
              <el-select v-model="ruleForm.bankName" placeholder="请选择题库"  clearable>
                <el-option label="公共题库" value="公共题库" />
<!--                <el-option label="私人题库" value="私人题库" />-->
                <el-option
                    v-for="item in dataBankList"
                    :label="item"
                    :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="课程类型" prop="courseType">
              <el-select v-model="ruleForm.courseType" placeholder="请选择课程类型"  clearable>
                <el-option label="公共课程" value="公共课程" />
                <el-option
                    v-for="item in dataList"
                    :label="item"
                    :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="知识点" prop="questionPoint">
          <el-select v-model="ruleForm.questionPoint" placeholder="请选择知识点"  clearable>
            <el-option
                v-for="item in dataPointList"
                :label="item"
                :value="item"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="题目" prop="questionSubject">
          <el-input v-model="ruleForm.questionSubject" type="textarea" size="small"/>
        </el-form-item>

        <!--          单选-->
       <div v-show="showChoice">
          <el-form-item label="选项A" prop="optionA">
            <el-input v-model="ruleForm.optionA" type="textarea"  size="small" />
          </el-form-item>
          <el-form-item label="选项B" prop="optionB">
            <el-input v-model="ruleForm.optionB" type="textarea" size="small" />
          </el-form-item>
          <el-form-item label="选项C" prop="optionC">
            <el-input v-model="ruleForm.optionC" type="textarea" size="small"/>
          </el-form-item>
          <el-form-item label="选项D" prop="optionD">
            <el-input v-model="ruleForm.optionD" type="textarea" size="small"/>
          </el-form-item>
         <el-form-item label="答案" prop="questionAnswer" size="default" >
           <el-select v-model="ruleForm.questionAnswer"   style="width: 400px">
             <el-option v-for="(item, index) in options" :key="index" :label="item.label" :value="item.value" ></el-option>
           </el-select>
         </el-form-item>
       </div>


<!--        多选-->
        <div v-show="showMultiple">
          <el-form-item label="选项A" prop="optionA">
            <el-input v-model="ruleForm.optionA" type="textarea"  size="small" />
          </el-form-item>
          <el-form-item label="选项B" prop="optionB">
            <el-input v-model="ruleForm.optionB" type="textarea" size="small" />
          </el-form-item>
          <el-form-item label="选项C" prop="optionC">
            <el-input v-model="ruleForm.optionC" type="textarea" size="small"/>
          </el-form-item>
          <el-form-item label="选项D" prop="optionD">
            <el-input v-model="ruleForm.optionD" type="textarea" size="small"/>
          </el-form-item>
          <el-form-item label="答案" prop="questionAnswer" size="default" >
            <el-select v-model="selectedOptions" :multiple="true"  @change="handleChange" style="width: 400px">
              <el-option v-for="(item, index) in options" :key="index" :label="item.label" :value="item.value" ></el-option>
            </el-select>
<!--            <h3>{{ruleForm.questionAnswer}}</h3>-->
          </el-form-item>
        </div>

<!--        填空，简答-->
        <div v-show="showShort">
          <el-form-item label="答案" prop="questionAnswer">
            <el-input v-model="ruleForm.questionAnswer" type="textarea" size="small"/>
          </el-form-item>
        </div>

<!--         判断-->
        <div v-show="showJudge">
            <el-form-item label="答案" prop="questionAnswer">
              <el-radio-group v-model="ruleForm.questionAnswer">
                <el-radio label="对"></el-radio>
                <el-radio label="错"></el-radio>
              </el-radio-group>
            </el-form-item>
        </div>

        <el-form-item label="描述" prop="questionDesc">
          <el-input v-model="ruleForm.questionDesc" type="textarea" size="small"/>
        </el-form-item>

        <el-form-item label="图片" size="default">
          <el-upload
              action="/api/file/upload"
              :show-file-list="true"
              :on-success="handleSuccess"
              :before-upload="beforeUpload">
            <el-button size="default" type="success" >上传图片</el-button>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" size="large" @click="save('ruleForm')" style="width: 150px;margin-left: 100px">保 存</el-button>
          <span style="width: 30px"></span>
          <el-button @click="resetForm('ruleForm')"  size="large" style="width: 150px; ">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "TeacherAddQuestion",
  data() {
    var checkEmpty = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('不能为空'));
      }
      callback();
    };

    return {
      showMultiple:false,
      showChoice:false,
      showJudge:false,
      showShort: true,
      options: [
        { value: 'A', label: '选项A' },
        { value: 'B', label: '选项B' },
        { value: 'C', label: '选项C' },
        { value: 'D', label: '选项D' }

      ],
      selectedOptions: [],
      ruleForm: {
        questionSubject: '',
        questionAnswer: '',
        questionType: '填空题',
        questionLevel: '中等',
        courseType: 'java基础',
        questionPoint: '',
        questionImage: '',
        bankName:'公共题库'
      },
      dataList:[],
      dataBankList:[],
      dataPointList:[],

      rules: {
        bankName:[
          { required: true, message: '请选择题库名称', trigger: 'change' }
        ],
        questionSubject: [
          { validator: checkEmpty, required: true,trigger: 'blur' }
        ],
        // questionAnswer:  [
        //   { validator: checkEmpty, required: true,trigger: 'blur' }
        // ],
        questionType: [
          { required: true, message: '请选择试题类型', trigger: 'change' }
        ],
        courseType: [
          { required: true, message: '请选择课程类型', trigger: 'change' }
        ],
        questionPoint:  [
          { validator: checkEmpty, required: true,trigger: 'blur' }
        ],
        questionLevel: [
          { required: true, message: '请选择难度级别', trigger: 'change' }
        ]
      }
    };
  },
  watch:{
    'ruleForm.questionType':{
      // deep:true,
      handler(){
        this.ruleForm.questionAnswer='';
        if (this.ruleForm.questionType == '单选题' || this.ruleForm.questionType == '' ) {
          this.showChoice=true;
        }else {
          this.showChoice=false;
        }

        if (this.ruleForm.questionType == '多选题'){
           this.selectedOptions=[];
           this.showMultiple=true;
        }else {
          this.showMultiple=false;
        }

        if (this.ruleForm.questionType == '判断题'){
            this.showJudge=true;
        }else {
            this.showJudge=false;
        }

        if (this.ruleForm.questionType == '填空题' || this.ruleForm.questionType === '简答题') {
          this.showShort=true;
        }else {
          this.showShort=false;
        }
      }
    },

    'ruleForm.courseType': function(newVal, oldVal) {
      console.log('courseType changed from', oldVal, 'to', newVal);
      this.getPointName();
      // 在这里执行相关操作
    }

  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post('/question/save', this.ruleForm).then(res => {
            if (res.code == 0) {
              //小提示
              this.$message({
                type: "success",
                message: res.msg
              });
              this.resetForm('ruleForm');
              this.ruleForm={};
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
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleSuccess(response) {
      // 上传成功后，保存图片地址
      //服务器保护无法刷新

      // this.imageUrl = require(response.data);
      this.ruleForm.questionImage=response.data;
    },
    beforeUpload(file) {
      // 验证上传的图片是否符合要求，例如大小、类型等
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
        return false;
      }
      return true;
    },
    handleChange() {
      // 将所选选项合并为字符串
      this.ruleForm.questionAnswer = this.selectedOptions.join(',')
    },
    getCourseName(){
      request.get("/course/getCourseName").then(res=>{
        this.dataList=res.data;
      })
    },
    getPointName(){
      console.log(this.ruleForm.courseType)
      request.get("/point/getPointName", { params: {
        courseType: this.ruleForm.courseType
      }}).then(res=>{
        this.dataPointList=res.data;
      })
    },
    getBankName(){
      request.get("/bank/getBankName").then(res=>{
        this.dataBankList=res.data;
      })
    }
  },
  created() {
    this.getCourseName();
    this.getPointName();
    this.getBankName()
  }
}
</script>

<style scoped>
#app{
  height: 680px;
  width: 700px;
  margin: auto;
  margin-top: 10px;
  padding-top: 10px;
  padding-right: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
.el-form-item {
  margin-bottom: 14px; /* 调整表单项之间的底部间距 */
}
</style>
