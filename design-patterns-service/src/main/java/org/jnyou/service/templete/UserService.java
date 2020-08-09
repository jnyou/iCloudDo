package org.jnyou.service.templete;

import org.apache.ibatis.annotations.Mapper;
import org.jnyou.entity.Goods;
import org.jnyou.entity.User;

/**
 * @ClassName UserService
 * @Description:
 * @Author: jnyou
 **/
public interface UserService {

    /**
     * 根据商品id查询商品
    * @return
     */
    User getUserById(Integer id);


    /**
     * 使用模板模式加载
     * @param id
     * @return
     */
    User getUserByIdTemplate(Integer id);
}