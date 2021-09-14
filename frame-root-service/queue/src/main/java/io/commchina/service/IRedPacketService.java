package io.commchina.service;

import io.commchina.common.entity.RedPacket;
import io.commchina.common.entity.Result;

public interface IRedPacketService {

    /**
     * 获取红包
     * @param redPacketId
     * @return
     */
    RedPacket get(long redPacketId);
	/**
	 * 抢红包业务实现
	 * @param redPacketId
	 * @return
	 */
	Result startSeckil(long redPacketId, int userId);

    /**
     * 微信抢红包业务实现
     * @param redPacketId
     * @param userId
     * @return
     */
    Result startTwoSeckil(long redPacketId,int userId);

}
