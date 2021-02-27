package org.jnyou.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jnyou.eduservice.entity.EduSubject;
import org.jnyou.eduservice.entity.excel.SubjectData;
import org.jnyou.eduservice.service.EduSubjectService;
import org.jnyou.servicebase.exception.IsMeException;

import java.util.Map;

/**
 * @ClassName yjn
 * @Description: 读取excel的监听器
 * @Author: 夏小颜
 * @Version: 1.0
 **/
public class SubjectListener extends AnalysisEventListener<SubjectData> {

    /**
     * 因为SubjectListener不能交给spring进行管理，需要自己new，不能注入其他对象
     */
    private EduSubjectService subjectService;
    public SubjectListener() {
    }

    /**
     * 创建有参数构造，传递subjectService用于操作数据库
     * @param subjectService
     */
    public SubjectListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 一行一行读取excel内容
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {

        if(null == subjectData){
            throw new IsMeException(-1,"文件数据为空");
        }

        //添加一级分类
        EduSubject existOneCategory = this.existOneCategory(subjectData.getOneSubjectName());
        if(null == existOneCategory){
            // 没有相同名称的一级分类，进行添加一级分类
            existOneCategory = new EduSubject();
            existOneCategory.setTitle(subjectData.getOneSubjectName());
            existOneCategory.setParentId("0");
            subjectService.save(existOneCategory);
        }

        //获取一级分类id值
        String pid = existOneCategory.getId();

        // 添加二级分类
        EduSubject existTwoCategory = this.existTwoCategory(subjectData.getTwoSubjectName(), pid);
        if(null == existTwoCategory){
            // 没有相同名称的一级分类，进行添加一级分类
            existTwoCategory = new EduSubject();
            if(null != subjectData.getTwoSubjectName()){
                existTwoCategory.setTitle(subjectData.getTwoSubjectName());
                existTwoCategory.setParentId(pid);
                subjectService.save(existTwoCategory);
            }
        }
    }

    /**
     * 判断数据库中是否有相同的一级分类名称
     * @param subjectService
     * @param oneSubjectName
     * @return
     */
    private EduSubject existOneCategory(String oneSubjectName){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",oneSubjectName);
        wrapper.eq("parent_id","0");
        EduSubject oneCategory = subjectService.getOne(wrapper);
        return oneCategory;
    }

    /**
     * 判断数据库中是否有相同的二级分类名称
     * @param subjectService
     * @param oneSubjectName
     * @return
     */
    private EduSubject existTwoCategory(String twoSubjectName,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",twoSubjectName);
        wrapper.eq("parent_id",pid);
        EduSubject twoCategory = subjectService.getOne(wrapper);
        return twoCategory;
    }


    /**
     * 读取表头内容
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        System.out.println("表头" +headMap);
    }

    /**
     * 读取完成后做的事情
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}