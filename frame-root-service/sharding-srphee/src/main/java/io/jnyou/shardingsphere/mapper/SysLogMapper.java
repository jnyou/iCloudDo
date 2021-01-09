package io.jnyou.shardingsphere.mapper;

import io.jnyou.shardingsphere.entity.SysLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author JnYou
 * @version 1.0.0
 */
@Component
public interface SysLogMapper {

    List<SysLog> rangeSearch(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);

    void newLog(@Param("log") SysLog sysLog);

    List<SysLog> page(@Param("curIndex") Long curIndex, @Param("size") Long size);

    List<SysLog> pageById(@Param("lastId") Long lastId, @Param("size") Long size);
}
