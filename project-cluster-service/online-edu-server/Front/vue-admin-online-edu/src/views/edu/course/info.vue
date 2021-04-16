<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">

        <el-form-item label="课程标题">
            <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>

        <!-- 所属分类：级联下拉列表 -->
        <!-- 一级分类 -->
        <el-form-item label="课程类别">
            <el-select
                v-model="courseInfo.subjectParentId"
                placeholder="一级分类" @change="oneSubjectCategoryChange">
                <el-option
                v-for="subject in oneSubjectCategory"
                :key="subject.id"
                :label="subject.title"
                :value="subject.id"/>
            </el-select>

            <!-- 二级分类 -->
            <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
            <el-option
                v-for="subject in twoSubjectCategory"
                :key="subject.value"
                :label="subject.title"
                :value="subject.id"/>
            </el-select>

        </el-form-item>

        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
            <el-select
                v-model="courseInfo.teacherId"
                placeholder="请选择">
                <el-option
                v-for="teacher in teacherList"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.id"/>
            </el-select>
        </el-form-item>

        <el-form-item label="总课时">
            <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介 -->
        <el-form-item label="课程简介">
            <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>

        <!-- 课程封面-->
        <el-form-item label="课程封面">

            <el-upload
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                name="mpf"
                :action="BASE_API+'/eduoss/fileoss/uploadOssFile'"
                class="avatar-uploader">
                <img :src="courseInfo.cover">
            </el-upload>

        </el-form-item>

        <el-form-item label="课程价格">
            <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

        <el-form-item>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdata">保存并下一步</el-button>
        </el-form-item>
    </el-form>
  </div>
</template>
<script>
import courseApi from '@/api/edu/course'
// 引入subject.js
import subjectApi from '@/api/edu/subject'
// 引入富文本编辑器组件
import Tinymce from '@/components/Tinymce'

const defaultForm = {
  title: '',
  subjectParentId: '',  // 一级分类ID
  subjectId: '',        // 二级分类ID
  teacherId: '', 
  lessonNum: 0,
  description: '',
  cover: 'https://jnyou.oss-cn-beijing.aliyuncs.com/2020/06/07/d2225022a3a04ae8950a19c68c5d703bmybatis.jpg',
  price: 0
}

export default {
    components: { Tinymce }, // 声明组件
    data(){
        return{
            courseInfo: defaultForm, // 默认信息
            saveBtnDisabled: false,  // 保存按钮是否禁用
            teacherList: [],         // 所有的讲师
            oneSubjectCategory: [],  // 一级分类
            twoSubjectCategory: [],  // 二级分类
            BASE_API: process.env.BASE_API, // OSS接口API地址
            courseId:''
        }
    },
    created(){
        this.init()
    },
    // 监听路由
    watch:{
      $route(to,from){ // 路由变化方式，路由发生改变，方法就会执行
        console.log('watch $route')
        this.init()
      }
    },
    methods:{
        init(){
            if(this.$route.params && this.$route.params.id){
                // 获取路由中的课程ID
                this.courseId = this.$route.params.id
                // 调用查询接口
                this.getCourseInfoByCourseId()
            } else {
                this.getTeacherList()
                this.getOneSubjectCategory()
                // 初始化
                this.courseInfo = defaultForm
            }
        },
        // 根据课程ID查询课程信息
        getCourseInfoByCourseId(){
            courseApi.getCourseInfoByCourseId(this.courseId)
            .then(res => {
                this.courseInfo = res.data.courseInfo
                // 查询所有的分类，包含一级和二级分类ID
                subjectApi.getSujectList()
                .then(res => {
                    this.oneSubjectCategory = res.data.list

                    for(let i = 0; i < this.oneSubjectCategory.length; i++){
                        // 获取每一个一级分类
                        let oneCategory = this.oneSubjectCategory[i]
                        if(this.courseInfo.subjectParentId == oneCategory.id){
                            this.twoSubjectCategory = oneCategory.children
                        }
                    }
                })
                this.getTeacherList()
            })
        },
        // 保存并下一步方法
        saveOrUpdata(){
            if(!this.courseInfo.id){
                // 没ID添加
                this.addCourseInfo()
            } else{
                this.updateCourseInfo()
            }
        },
        // 新增课程信息
        addCourseInfo(){
            if(this.courseInfo.subjectParentId == ''){
                this.$message({
                    type: 'error',
                    message: "一级课程分类不能为空"
                })
                return
            }
            if(this.courseInfo.title == ''){
                this.$message({
                    type: 'error',
                    message: "课程名称不能为空"
                })
                return
            }
            courseApi.addCourseInfo(this.courseInfo)
            .then(response => {
                this.$message({
                    type: 'success',
                    message: response.message
                })
                // 跳转到第二步
                this.$router.push({ path: `/course/chapter/${response.data.courseId}`})
            })
        },
        // 修改课程信息
        updateCourseInfo(){
            courseApi.updateCourseInfo(this.courseInfo)
            .then(response => {
                this.$message({
                    type: 'success',
                    message: response.message
                })
                // 跳转到第二步
                this.$router.push({ path: `/course/chapter/${this.courseId}`})
            })
        },
        // 查询所有的讲师
        getTeacherList(){
            courseApi.getTeacherList()
            .then(res => {
                this.teacherList = res.data.data
            })
        },
        // 查询所有一级课程分类
        getOneSubjectCategory(){
            subjectApi.getSujectList()
            .then(res => {
                this.oneSubjectCategory = res.data.list
            })
        },
        // 二级分类 value为点击一级分类的id
        oneSubjectCategoryChange(value){
            for(let i =0; i<this.oneSubjectCategory.length; i++){
                // 拿到每一个一级分类
                let one = this.oneSubjectCategory[i]
                // 判断点击的一级分类和拿到的一级分类对比
                if(one.id === value){
                    // 取出一级分类中的children
                    this.twoSubjectCategory = one.children
                }
            }
            this.courseInfo.subjectId = ''
        },
        // 上传课程封面成功方法
        handleAvatarSuccess(res){
            this.courseInfo.cover = res.data.url
        },
        // 上传之前调用的方法
        beforeAvatarUpload(file){
            const isJPG = file.type === 'image/jpeg' // 上传类型
            const isLt2M = file.size / 1024 / 1024 < 2 // 大小

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!')
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!')
            }
            return isJPG && isLt2M
        }
    }
}
</script>
<!--富文本样式-->
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>