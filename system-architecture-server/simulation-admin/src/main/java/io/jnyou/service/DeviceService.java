package io.jnyou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.jnyou.core.dos.DeviceDO;
import io.jnyou.core.dtos.DeviceDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-12-21
 */
public interface DeviceService extends IService<DeviceDO> {

    DeviceDTO getDevice(Integer id);

    List<DeviceDTO> listDevice();

    Map<Integer, DeviceDTO> mapDevice();

    Map<Integer, DeviceDTO> mapDevice(List<DeviceDO> deviceDOs);

    List<DeviceDTO> listChildrenTree(DeviceDTO DeviceDtO);

    List<DeviceDTO> listChildrenTree(Set<Integer> parentId);

    DeviceDTO transDeviceDTO(DeviceDO DeviceDO);

    List<DeviceDTO> transDeviceDTOList(List<DeviceDO> deviceDOs);

    List<DeviceDTO> listDeviceByCatelogsId(List<Integer> catalogs);

    List<Integer> getDeviceId(Set<Long> spaceIdList);

    List<DeviceDTO> simpleDeviceListAndChildDevice(Set<Long> deviceIdList);

}
