package org.jnyou.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jnyou.eduservice.entity.EduSubject;
import org.jnyou.eduservice.entity.excel.SubjectData;
import org.jnyou.eduservice.entity.subject.OneSubjectCategory;
import org.jnyou.eduservice.entity.subject.TwoSubjectCategory;
import org.jnyou.eduservice.listener.SubjectListener;
import org.jnyou.eduservice.mapper.EduSubjectMapper;
import org.jnyou.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.servicebase.exception.IsMeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            // 获取上传文件的文件流
            InputStream inputStream = file.getInputStream();
            // 调用方法读取
            EasyExcel.read(inputStream, SubjectData.class,new SubjectListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
            throw new IsMeException(-1,"添加课程分类失败");
        }
    }

    @Override
    public List<OneSubjectCategory> getSubjectCategoryListTree() {
        //查询所有的一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        oneWrapper.orderByAsc("sort", "id");
        List<EduSubject> oneSubjectCategory = this.baseMapper.selectList(oneWrapper);

        // 查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        twoWrapper.orderByAsc("sort", "id");
        List<EduSubject> twoSubjectCategory = this.baseMapper.selectList(twoWrapper);

        // 返回的最终集合对象
        List<OneSubjectCategory> treeList = new ArrayList<>();
        for (EduSubject oSubject : oneSubjectCategory) {
            OneSubjectCategory oneSubject = new OneSubjectCategory();
            BeanUtils.copyProperties(oSubject,oneSubject);
            // 放入一级分类
            treeList.add(oneSubject);
            // 填充二级分类
            List<TwoSubjectCategory> twoSubjectCategories = new ArrayList<>();
            for (EduSubject tSubject : twoSubjectCategory) {
                // 一级分类的ID和二级parentId相同
                if(tSubject.getParentId().equals(oSubject.getId())){
                    TwoSubjectCategory twoSubject = new TwoSubjectCategory();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoSubjectCategories.add(twoSubject);
                }
            }
            // 将二级分类放入一级分类的集合中
            oneSubject.setChildren(twoSubjectCategories);
        }
        return treeList;
    }
}
