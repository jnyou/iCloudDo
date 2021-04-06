package io.jnyou.service.impl;

import io.jnyou.core.base.BaseAsset;
import io.jnyou.service.AssetService;
import io.jnyou.service.CompoundAssetService;
import io.jnyou.service.DeviceService;
import io.jnyou.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    MonitorService monitorService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    CompoundAssetService<BaseAsset> compoundAssetService;

    @Override
    public BaseAsset findAsset(Integer id) {
        BaseAsset asset = deviceService.getDevice(id);
        if (asset == null) {
            asset = monitorService.getMonitor(id);
        }
        return asset;
    }

    @Override
    public List<BaseAsset> listAssets() {
        List<? extends BaseAsset> devices = deviceService.listDevice();
        List<BaseAsset> assets = new ArrayList<>();
        assets.addAll(devices);
        assets.addAll(monitorService.listMonitors());
        return assets;
    }

    @Override
    public Map<Integer, BaseAsset> mapAssets() {
        Map<Integer, BaseAsset> assetMap = new HashMap<>();
        for (BaseAsset asset : this.listAssets()) {
            assetMap.put(asset.getId(), asset);
        }
        return assetMap;
    }

    @Override
    public List<BaseAsset> listChildrenTree(Integer parentId) {
        return compoundAssetService.listChildrenTree(parentId, this.mapAssets());
    }

    @Override
    public List<BaseAsset> listChildrenTree(Set<Integer> parentIds) {
        return compoundAssetService.listChildrenTree(parentIds, this.mapAssets());
    }

    @Override
    public List<BaseAsset> listChildrenTree() {
        return compoundAssetService.listChildrenTree(this.mapAssets());
    }

}