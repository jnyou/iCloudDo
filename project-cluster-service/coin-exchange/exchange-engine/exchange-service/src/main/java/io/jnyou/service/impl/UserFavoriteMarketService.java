package io.jnyou.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import io.jnyou.domain.UserFavoriteMarket;

public interface UserFavoriteMarketService extends IService<UserFavoriteMarket>{


    /**
     * 用户取消收藏
     * @param marketId
     * @param userId
     * @return
     */
    boolean deleteUserFavoriteMarket(Long marketId, Long userId);
}
