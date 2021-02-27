package org.jnyou.eduservice.service;

import org.jnyou.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.eduservice.entity.subject.OneSubjectCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-06
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 保存课程分类
     * @param file
     * @param subjectService
     */
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    /**
     * 查询课程分类的树
     * @return
     */
    List<OneSubjectCategory> getSubjectCategoryListTree();
}
