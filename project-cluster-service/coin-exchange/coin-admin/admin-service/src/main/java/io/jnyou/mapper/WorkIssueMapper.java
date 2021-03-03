package io.jnyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.jnyou.domain.WorkIssue;

public interface WorkIssueMapper extends BaseMapper<WorkIssue> {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkIssue record);

    int insertSelective(WorkIssue record);

    WorkIssue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkIssue record);

    int updateByPrimaryKey(WorkIssue record);
}