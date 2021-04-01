package io.jnyou.core.base;


import io.jnyou.core.type.AssetKind;
import io.jnyou.core.type.AssetType;

public interface Asset<T extends AssetType> {

    Integer getId();

    Integer getParent();

    String getName();

    String getCaption();

    T getType();

    AssetKind getKind();

}
