package io.jnyou.core.base;

import io.jnyou.core.type.AssetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseCompoundAsset<T extends AssetType> extends BaseAsset<T> implements CompoundAsset {

    protected List<BaseAsset> children;

    public List<BaseAsset> getChildren() {
        return this.children;
    }

    @Override
    public void addChild(BaseAsset child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }
}