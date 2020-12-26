package org.jnyou.gmall.storageservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.storageservice.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

