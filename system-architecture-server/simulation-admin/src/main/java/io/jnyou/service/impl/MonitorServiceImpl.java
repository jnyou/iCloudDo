package io.jnyou.service.impl;

import io.jnyou.core.base.BaseAsset;
import io.jnyou.service.ControlService;
import io.jnyou.service.DeviceService;
import io.jnyou.service.MonitorService;
import io.jnyou.service.ProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    ProbeService probeService;
    @Autowired
    ControlService controlService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    CompoundAssetServiceImpl<BaseAsset> compoundAssetService;

    @Override
    public BaseAsset getMonitor(Integer id) {
        BaseAsset asset = probeService.getProbe(id);
        if (asset == null) {
            asset = controlService.getControl(id);
        }
        return asset;
    }

    /**
     * 资产：空间，设备，服务，监测器
     * 监测器：传感器，控制器
     *
     * 获取监测器信息
     *
     * @return java.util.List<io.jnyou.core.base.BaseAsset>
     */
    @Override
    public List<BaseAsset> listMonitors() {
        // 传感器
        List<? extends BaseAsset> probes = probeService.listProbe();
        // 控制器
        List<? extends BaseAsset> controls = controlService.listControl();
        List<BaseAsset> monitors = new ArrayList<>();
        monitors.addAll(probes);
        monitors.addAll(controls);
        return monitors;
    }

    @Override
    public Map<Integer, BaseAsset> mapMonitors() {
        Map<Integer, BaseAsset> assetMap = new HashMap<>();
        for (BaseAsset asset : this.listMonitors()) {
            assetMap.put(asset.getId(), asset);
        }
        return assetMap;
    }

    @Override
    public List<BaseAsset> listChildrenTree() {
        return compoundAssetService.listChildrenTree(this.mapMonitors());
    }

    @Override
    public List<BaseAsset> listChildrenTree(Integer parentId) {
        return compoundAssetService.listChildrenTree(parentId, this.mapMonitors());
    }

    @Override
    public List<BaseAsset> listChildrenTree(Set<Integer> parentId) {
        return compoundAssetService.listChildrenTree(parentId, this.mapMonitors());
    }

}