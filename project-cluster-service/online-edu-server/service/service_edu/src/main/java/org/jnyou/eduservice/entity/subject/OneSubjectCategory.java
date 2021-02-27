package org.jnyou.eduservice.entity.subject;

import lombok.Data;

import java.util.List;

/**
 * @ClassName yjn
 * @Description:
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Data
public class OneSubjectCategory {

    private String id;

    private String title;

    private List<TwoSubjectCategory> children;

}