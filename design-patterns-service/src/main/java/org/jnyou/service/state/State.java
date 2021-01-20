package org.jnyou.service.state;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * State 状态接口
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public interface State {
  /**
   * 挑选商品
   */
  void choose();
  /**
   * 付款
   */
  boolean payment();
  /**
   * 分发商品
   */
  void dispenseCommodity();
}