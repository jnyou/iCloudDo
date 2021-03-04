package io.jnyou.service;

import io.jnyou.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu>{


    List<SysMenu> getMenusByUserId(Long userId);
}
