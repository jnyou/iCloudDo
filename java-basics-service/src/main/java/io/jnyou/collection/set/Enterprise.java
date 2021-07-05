package io.jnyou.collection.set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Enterprise
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enterprise /*implements Comparable<Enterprise>*/{

    private Long id;
    private String name;
    private Long parentId;
    private Long deptId;

//    @Override
//    public int compareTo(Enterprise o) {
//        return (int) (this.id - o.id);
//    }
}