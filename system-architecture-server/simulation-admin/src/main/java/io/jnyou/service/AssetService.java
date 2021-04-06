package io.jnyou.service;

import io.jnyou.core.base.BaseAsset;
import io.jnyou.service.impl.AssetServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AssetService {

    BaseAsset findAsset(Integer id);

    List<BaseAsset> listAssets();

    Map<Integer, BaseAsset> mapAssets();

    List<BaseAsset> listChildrenTree();

    List<BaseAsset> listChildrenTree(Integer parentId);

    List<BaseAsset> listChildrenTree(Set<Integer> parentId);

}