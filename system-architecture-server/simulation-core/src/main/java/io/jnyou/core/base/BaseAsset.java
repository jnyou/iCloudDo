package io.jnyou.core.base;

import io.jnyou.core.type.AssetKind;
import io.jnyou.core.type.AssetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseAsset<T extends AssetType> implements Asset {

    protected Integer id;
    protected Integer parent;
    protected String name;
    protected String caption;
    protected T type;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public T getType() {
        return type;
    }

    @Override
    public AssetKind getKind() {
        return type.getKind();
    }

    public void setType(T type) {
        this.type = type;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}