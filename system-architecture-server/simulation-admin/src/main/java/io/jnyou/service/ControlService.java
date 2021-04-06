package io.jnyou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.jnyou.core.base.BaseAsset;
import io.jnyou.core.dos.ControlDO;
import io.jnyou.core.dtos.ControlDTO;
import io.jnyou.core.vo.AssetVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-12-28
 */
public interface ControlService extends IService<ControlDO> {

    BaseAsset getControl(Integer id);

    List<ControlDTO> listControl();

    ControlDTO transControlDTO(ControlDO controlDO);

    AssetVo queryDeviceById(Integer controlId);
}
