package org.jnyou.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.eduservice.entity.EduTeacher;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-05-30
 */
public interface EduTeacherService extends IService<EduTeacher> {

    @Override
    public boolean removeById(Serializable id);

    /**
     * 分页查询前台讲师列表
     * @param page
     * @param size
     * @return
     */
    Map<String, Object> getTeacherFrontPageList(long page, long size);
}
