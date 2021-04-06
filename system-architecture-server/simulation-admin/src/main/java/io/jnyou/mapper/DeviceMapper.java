package io.jnyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.jnyou.core.dos.DeviceDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jnyou
 * @since 2020-12-21
 */
@Repository
public interface DeviceMapper extends BaseMapper<DeviceDO> {

    List<DeviceDO> simpleDeviceListAndChildDevice(@Param("parentIds") Set<Long> parentIds);

}
