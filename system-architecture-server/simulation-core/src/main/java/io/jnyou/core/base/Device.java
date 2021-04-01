package io.jnyou.core.base;

public interface Device extends CompoundAsset, AssetGrade{

    void addChild(Device child);
}
