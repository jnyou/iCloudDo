package io.jnyou.core.type;

public abstract class AssetType {

    private String name;
    private AssetKind kind;

    public AssetType(AssetKind kind, String name) {
        this.kind = kind;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(AssetKind kind) {
        this.kind = kind;
    }

    public final AssetKind getKind() {
        return kind;
    }

    public final boolean isCompound() {
        return kind == AssetKind.SPACE || kind == AssetKind.DEVICE;
    }

    public final boolean isMonitor() {
        return kind == AssetKind.CONTROL || kind == AssetKind.PROBE;
    }

}