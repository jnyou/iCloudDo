package io.jnyou.service;



import io.jnyou.core.base.BaseAsset;
import io.jnyou.core.erasable.Erasable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CompoundAssetService<T extends BaseAsset> {

    List<T> listChildrenTree(Map<Integer, T> assetMap);

    List<T> listChildrenTree(Integer parentId, Map<Integer, T> assetMap);

    List<T> listChildrenTree(Set<Integer> parentIds, Map<Integer, T> assetMap);

    List<T> listChildrenTree(Erasable erasable, Map<Integer, T> assetMap);

    void mapChildrenTree(Erasable erasable, Map<Integer, T> assetMap);

    void buildAssetTree(Map<Integer, T> assetDict);

    void cleanTree(Erasable erasable, Map<Integer, T> assetDict);

}