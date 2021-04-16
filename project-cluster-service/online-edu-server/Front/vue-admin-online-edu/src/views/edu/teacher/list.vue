<template>
  <div class="app-container">

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form> 

    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="80" />

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="资历" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

     <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

  </div>
</template>

<script>
// 引入teacher.js
import teacherApi from '@/api/edu/teacher'

export default {

    // 编写核心代码
    data(){ // 定义变量和初始值
        return {
            list: null, // 查询出来的结果集    默认值为null
            total: 0,   // 查询出来的总记录数  默认值为0
            page: 1,    // 参数页
            limit: 10,  // 参数记录数
            teacherQuery: {} // 参数条件对象
        }
    },
    created(){ // 在页面渲染之前执行，一般调用methods中的方法
        this.getList()
    },
    methods:{ // 创建具体的方法，调用teacher.js返回数据
        // 讲师列表的方法
        getList(page = 1){ // 默认第一页
            this.page = page // 页数改变
            teacherApi.getTeacherListByPage(this.page,this.limit,this.teacherQuery)
            .then(res =>{ // 请求成功
                this.list = res.data.data
                this.total = res.data.count
                console.log(this.list);
                console.log(this.total);
            })
        },
        // 清空表单方法
        resetData(){  
            // 清空条件表单的数据
            this.teacherQuery = {}
            // 查询所有
            this.getList()
        },
        // 删除讲师的方法
        removeDataById(id){
            // debugger
            
            this.$confirm('你确定删除该讲师吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { // 点击确定执行then方法
                teacherApi.deleteTeacherById(id)
                .then(res => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    })
                  this.getList()
                })
                
            }) // 点击取消和失败调用catch方法
        }

    }
    
}
</script>