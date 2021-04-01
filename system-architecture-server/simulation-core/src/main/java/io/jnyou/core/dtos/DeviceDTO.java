package io.jnyou.core.dtos;

import io.jnyou.core.base.BaseDevice;
import io.jnyou.core.base.Device;
import io.jnyou.core.type.AssetKind;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceDTO extends BaseDevice implements Serializable, Device {

    private static final long serialVersionUID = 1L;

    private Integer catalog;
    private String vendor;
    private Date purchaseDate;
    private Date warrantyDate;
    private Integer enabled;
    private String memo;
    private String qrcode;
    private Integer showInClient;
    private Integer moduleId;
    // 健康度
    private String healthIndex;
    // 分类
    @Builder.Default
    private Integer kindCode = AssetKind.caption.DEVICE.getCode();
    @Builder.Default
    private String kindStr = AssetKind.caption.DEVICE.getMsg();
    // 节点的状态
    private Integer status;

    private boolean bind;
    // 摄像头的属性。
    private String username;
    private String password;
    private String host;
    private int port;


}
