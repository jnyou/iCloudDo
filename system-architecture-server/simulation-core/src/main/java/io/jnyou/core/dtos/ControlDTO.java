package io.jnyou.core.dtos;

import io.jnyou.core.base.BaseAsset;
import io.jnyou.core.type.AssetKind;
import io.jnyou.core.type.ControlType;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ControlDTO extends BaseAsset<ControlType> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer catalog;
    private Integer source;
    private String timeInterval;
    private Integer refreshDelay;
    private String transform;
    private String warnCond;
    private String memo;
    private Float minValue;
    private Float maxValue;
    /**
     * 分类
     */
    @Builder.Default
    private Integer kindeCode = AssetKind.caption.CONTROL.getCode();

    @Builder.Default
    private String kindStr = AssetKind.caption.CONTROL.getMsg();

    @Builder.Default
    private int grade = 2;

    @Override
    public Integer getId(){
        return this.id;
    }

    @Override
    public Integer getParent() {
        return this.parent;
    }

}

