package io.jnyou.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.constant.NumberConstant;
import io.jnyou.core.dos.DeviceDO;
import io.jnyou.core.dtos.DeviceDTO;
import io.jnyou.core.enums.AssetState;
import io.jnyou.core.type.DeviceType;
import io.jnyou.mapper.DeviceMapper;
import io.jnyou.service.DeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jnyou
 * @since 2020-12-21
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, DeviceDO> implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public DeviceDTO getDevice(Integer id) {
        return this.transDeviceDTO(this.getById(id));
    }

    @Override
    public List<DeviceDTO> listDevice() {
        return this.transDeviceDTOList(this.list());
    }

    @Override
    public Map<Integer, DeviceDTO> mapDevice() {
        return this.mapDevice(this.list());
    }

    @Override
    public Map<Integer, DeviceDTO> mapDevice(List<DeviceDO> deviceDOs) {
        Map<Integer, DeviceDTO> deviceDTOMap = new HashMap<>();
        for (DeviceDO deviceDO : deviceDOs) {
            deviceDTOMap.put(deviceDO.getId(), this.transDeviceDTO(deviceDO));
        }
        return deviceDTOMap;
    }

    @Override
    public List<DeviceDTO> listChildrenTree(DeviceDTO DeviceDtO) {
        return null;
    }

    @Override
    public List<DeviceDTO> listChildrenTree(Set<Integer> parentId) {
        return null;
    }


    @Override
    public DeviceDTO transDeviceDTO(DeviceDO deviceDO) {
        return DeviceDTO.builder()
                .id(deviceDO.getId())
                .parent(deviceDO.getParent())
                .type(new DeviceType(deviceDO.getType()))
                .catalog(deviceDO.getCatalog().intValue())
                .name(deviceDO.getName())
                .caption(deviceDO.getCaption())
                .enabled(deviceDO.getEnabled())
                .showInClient(deviceDO.getShowInClient())
                .children(new ArrayList<>())
                .build();
    }

    @Override
    public List<DeviceDTO> transDeviceDTOList(List<DeviceDO> deviceDOs) {
        List<DeviceDTO> deviceDTOs = new ArrayList<>();
        for (DeviceDO deviceDO : deviceDOs) {
            deviceDTOs.add(this.transDeviceDTO(deviceDO));
        }
        return deviceDTOs;
    }

    private static final String DFWL_CAMERA_SERVER_IP_STATIC = "${ServerHost}";
    private static final String DFWL_CAMERA_NUMBER_IP_STATIC = "${DeviceNumber}";
    private static final String DFWL_CAMERA_IP_STATIC = "${Host}";
    private static final String DFWL_CAMERA_PORT_STATIC = "${Port}";
    private static final String DFWL_CAMERA_USERNAME_STATIC = "${UserName}";
    private static final String DFWL_CAMERA_PASSWORD_STATIC = "${Password}";
    private static final String DFWL_CAMERA_PLAY_BACK_YARD = "${PlayBackYard}";
    private static final String HU_FANG_PORT = "${hfPort}";

    @Override
    public List<DeviceDTO> listDeviceByCatelogsId(List<Integer> catalogs) {
        List<DeviceDO> deviceDOS = this.list(Wrappers.<DeviceDO>lambdaQuery().in(!CollectionUtils.isEmpty(catalogs), DeviceDO::getCatalog, catalogs));
        return !CollectionUtils.isEmpty(deviceDOS) ? this.transDeviceDTOList(deviceDOS) : null;
    }

    @Override
    public List<Integer> getDeviceId(Set<Long> spaceIdList) {
        List<DeviceDO> deviceDOS = this.baseMapper.selectBatchIds(spaceIdList);
        return CollectionUtils.isEmpty(deviceDOS) ? null : deviceDOS.stream().map(device -> device.getId()).collect(Collectors.toList());
    }

    private void buildChildDeviceList(List<DeviceDTO> dtos, List<DeviceDTO> deviceDTOS) {
        if (!CollectionUtils.isEmpty(dtos)) {
            List<Integer> collectDtos = dtos.stream().map(item -> item.getId()).collect(Collectors.toList());
            for (int i = 0; i < deviceDTOS.size(); i++) {
                if (collectDtos.contains(deviceDTOS.get(i).getParent())) {
                    dtos.add(deviceDTOS.get(i));
                }
            }
        }
    }

    @Override
    public List<DeviceDTO> simpleDeviceListAndChildDevice(Set<Long> deviceIdList) {
        List<DeviceDO> deviceDTOs = this.baseMapper.selectBatchIds(deviceIdList);
        // 转换
        List<DeviceDTO> dtos = this.transDeviceDTOList(deviceDTOs);
        // 获取到所有查询子设备
        List<DeviceDTO> deviceDTOS = this.listDevice();
        buildChildDeviceList(dtos, deviceDTOS);
        return dtos;
    }

}
