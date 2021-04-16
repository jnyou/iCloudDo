<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-button type="text" @click="openDialogChapter()">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
        <li
            v-for="chapter in chapterVideoList"
            :key="chapter.id">
            <p>
                {{ chapter.title }}

                <span class="acts">
                    <el-button type="text" @click="openAddVideo(chapter.id)">添加课时</el-button>
                    <el-button style="" type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
                    <el-button type="text" @click="removeChapterById(chapter.id)">删除</el-button>
                </span>
            </p>

            <!-- 视频 -->
            <ul class="chanpterList videoList">
                <li
                    v-for="video in chapter.children"
                    :key="video.id">
                    <p>{{ video.title }}
                        <span class="acts">
                            <el-button type="text" @click="openEditVideo(video.id)">编辑</el-button>
                            <el-button type="text" @click="removeVideoById(video.id)">删除</el-button>
                        </span>
                    </p>
                </li>
            </ul>
        </li>
    </ul>

    <el-form label-width="120px">

      <el-form-item>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
      </el-form-item>
    </el-form>

    <!-- 章节弹框 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
        <el-form :model="chapter" label-width="120px">
            <el-form-item label="章节标题">
                <el-input v-model="chapter.title"/>
            </el-form-item>
            <el-form-item label="章节排序">
                <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
            <el-button :disabled="saveChapterBtnDisabled" type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 小节弹框 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
                :on-success="handleVodUploadSuccess"
                :on-remove="handleVodRemove"
                :before-remove="beforeVodRemove"
                :on-exceed="handleUploadExceed"
                :file-list="fileList"
                :action="BASE_API+'/eduvod/video/uploadVideoAliyun'"
                :limit="1"
                name="file"
                class="upload-demo">
          <el-button size="small" type="primary">上传视频</el-button>
          <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                  支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                  GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                  MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                  SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question"/>
          </el-tooltip>
          </el-upload>
      </el-form-item>
    </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import chapterApi from '@/api/edu/chapter'
import videoApi from '@/api/edu/video'

const defaultChapterForm = {
  title:'',
  sort:0
}

const defaultVideoForm = {
  title: '',
  sort: 0,
  isFree: 0,
  videoSourceId: '', // 云端视频ID
  videoOriginalName:'' //原始文件名称
}

export default {
    data(){
        return{
            saveBtnDisabled: false,
            chapterVideoList: [],
            courseId: '',  // 课程ID
            saveChapterBtnDisabled:false, //章节防止重复提交
            dialogChapterFormVisible: false, // 章节弹框
            chapter: defaultChapterForm, // 章节数据
            saveVideoBtnDisabled: false, //小节防止重复提交
            dialogVideoFormVisible:false, // 小节弹框
            video: defaultVideoForm, // 小节数据
            fileList: [],//上传文件列表
            BASE_API: process.env.BASE_API // 接口API地址
        }
    },
    created(){
      // 从上一步获取路由的ID值
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id
        this.getChapterVedioByCourseId()
      }
      
    },
    methods:{

// =====================================小节操作====================================================   
        // 添加小节弹框
        openAddVideo(chapterId){
          this.dialogVideoFormVisible = true
          // 初始化
          this.video = {}
          this.fileList = []
          // 添加章节ID
          this.video.chapterId = chapterId

        },
        // 保存
        saveOrUpdateVideo(){
          if(!this.video.id){
            this.addVideoInfo()
          }else{
            this.updateVideoInfo()
          }
          
        },
        // 添加
        addVideoInfo(){
          this.video.courseId = this.courseId
          videoApi.saveVideoInfo(this.video)
          .then(res => {
            // 关弹框
            this.dialogVideoFormVisible = false
            // 提示
            this.$message({
                type: 'success',
                message: res.message
            })
            // 刷新页面
            this.getChapterVedioByCourseId()
          })
        },
        //编辑回显
        openEditVideo(videoId){
          videoApi.getVideoInfoById(videoId)
          .then(res => {
            this.dialogVideoFormVisible = true
            // 数据回显
            this.video = res.data.video
            this.fileList = [{'name': this.video.videoOriginalName}]
          })
        },
        // 修改
        updateVideoInfo(){
          videoApi.updateVideoInfoById(this.video)
          .then(res => {
            // 关弹框
            this.dialogVideoFormVisible = false
            // 提示
            this.$message({
                type: 'success',
                message: res.message
            })
            // 刷新页面
            this.getChapterVedioByCourseId()
          })
        },
        // 删除
        removeVideoById(videoId){

          this.$confirm('你确定删除该课程章节吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { // 点击确定执行then方法
                videoApi.removeById(videoId)
                .then(res => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    })
                  this.getChapterVedioByCourseId()
                })
                
            }) // 点击取消和失败调用catch方法

        },
        // 上传成功的回调方法
        handleVodUploadSuccess(response, file, fileList){
          // 视频ID
          this.video.videoSourceId = response.data.videoId
          // 视频原始名称
          this.video.videoOriginalName = file.name
        },
        //视图上传多于一个视频
        handleUploadExceed(files, fileList) {
          this.$message.warning('想要重新上传视频，请先删除已上传的视频')
        },
        // 点击X删除视频弹出提示信息方法
        beforeVodRemove(file, fileList){
          return this.$confirm(`确定移除 ${file.name}？`)
        },
        // 点击确定按钮删除云端视频
        handleVodRemove(file, fileList){
          videoApi.removeVideoAliyun(this.video.videoSourceId)
          .then(res => {
            // 提示
            this.$message({
                type: 'success',
                message: res.message
            })
            // 把文件列表清空
            this.fileList = []
            // 清空
            this.video.videoSourceId = ''
            this.video.videoOriginalName = ''
          })
        },
// =====================================章节操作====================================================      
        // 弹框
        openDialogChapter(){
          // 打开弹框
          this.dialogChapterFormVisible = true
          this.chapter = defaultChapterForm
        },
        saveOrUpdate(){
          if(!this.chapter.id){
            this.addChapterInfo()
          }else{
            this.updateChapterInfo()
          }
          
        },
        // 添加
        addChapterInfo(){
          // 设置课程ID
          this.chapter.courseId = this.courseId
          chapterApi.addChapterInfo(this.chapter)
          .then(res => {
            // 关弹框
            this.dialogChapterFormVisible = false
            // 提示
            this.$message({
                type: 'success',
                message: res.message
            })
            // 刷新页面
            this.getChapterVedioByCourseId()
          })
        },
        // 修改章节
        updateChapterInfo(){
          chapterApi.updateChapterInfo(this.chapter)
          .then(res => {
            // 关弹框
            this.dialogChapterFormVisible = false
            // 提示
            this.$message({
                type: 'success',
                message: res.message
            })
            // 刷新页面
            this.getChapterVedioByCourseId()
          })
        },
        // 编辑回显数据
        openEditChapter(chapterId){
          this.dialogChapterFormVisible = true
          chapterApi.getChapterByChapterId(chapterId)
          .then(res => {
            this.chapter = res.data.chapter
          })
        },
        // 删除
        removeChapterById(chapterId){
          this.$confirm('你确定删除该课程章节吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { // 点击确定执行then方法
                chapterApi.deleteChapterInfo(chapterId)
                .then(res => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    })
                  this.getChapterVedioByCourseId()
                })
                
            }) // 点击取消和失败调用catch方法
        },
        // 保存课程大纲信息
        getChapterVedioByCourseId(){
          chapterApi.getChapterVedioByCourseId(this.courseId)
             .then(res => {
                  this.chapterVideoList = res.data.list
              })     
        },
        // 上一步修改课程信息
        previous(){
            this.$router.push({ path: '/course/info/' + this.courseId })
        },
        // 保存并下一步方法
        next(){
            this.$router.push({ path: '/course/publish/' + this.courseId })
        }
    }
}
</script>

<style scoped>
.chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chanpterList li{
  position: relative;
}
.chanpterList p{
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chanpterList .acts {
    float: right;
    font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}

</style>