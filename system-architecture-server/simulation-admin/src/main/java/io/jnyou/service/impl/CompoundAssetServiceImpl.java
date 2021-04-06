package io.jnyou.service.impl;

import io.jnyou.core.base.BaseAsset;
import io.jnyou.core.base.BaseCompoundAsset;
import io.jnyou.core.erasable.Erasable;
import io.jnyou.core.erasable.IntegerErasable;
import io.jnyou.core.erasable.NotErasable;
import io.jnyou.core.erasable.SetErasable;
import io.jnyou.service.CompoundAssetService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author liujingeng
 * @description 可组合的资产业务层实现类
 * @create 2020/12/30
 */
@Service
public class CompoundAssetServiceImpl<T extends BaseAsset> implements CompoundAssetService<T> {

    @Override
    public List<T> listChildrenTree(Map<Integer, T> assetMap) {
        return this.listChildrenTree(new NotErasable(), assetMap);
    }

    @Override
    public List<T> listChildrenTree(Integer parentId, Map<Integer, T> assetMap) {
        return this.listChildrenTree(new IntegerErasable(parentId), assetMap);
    }

    @Override
    public List<T> listChildrenTree(Set<Integer> parentIds, Map<Integer, T> assetMap) {
        return this.listChildrenTree(new SetErasable(parentIds), assetMap);
    }

    @Override
    public List<T> listChildrenTree(Erasable erasable, Map<Integer, T> assetMap) {
        this.mapChildrenTree(erasable, assetMap);
        return new ArrayList<>(assetMap.values());
    }

    @Override
    public void mapChildrenTree(Erasable erasable, Map<Integer, T> assetMap) {
        this.buildAssetTree(assetMap);
        this.cleanTree(erasable, assetMap);
    }

    @Override
    public void buildAssetTree(Map<Integer, T> assetDict) {
        for (Map.Entry<Integer, T> entry : assetDict.entrySet()) {
            BaseAsset asset = entry.getValue();
            final int parentId = asset.getParent();
            if (assetDict.containsKey(parentId)) {
                BaseAsset parent = assetDict.get(parentId);
                if (parent.getType().isCompound()) {
                    ((BaseCompoundAsset) parent).addChild(asset);
                }
            }
        }
    }

    @Override
    public void cleanTree(Erasable erasable, Map<Integer, T> assetDict) {
        Iterator<? extends Map.Entry<Integer, T>> iterator = assetDict.entrySet().iterator();
        Set<Integer> removed = new HashSet<>();
        while (iterator.hasNext()) {
            final int parentId = iterator.next().getValue().getParent();
            if (assetDict.containsKey(parentId) || removed.contains(parentId)) {
                removed.add(parentId);
                iterator.remove();
            } else if (erasable.expression(parentId)) {
                iterator.remove();
            }
        }
    }
}