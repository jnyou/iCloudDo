package org.jnyou.service.state;

import lombok.Data;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * VendingMachine
 * 自动售货机 => Context角色
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
public class VendingMachine {
  // 表示当前状态
  private State state = null;
  // 商品数量
  private int count = 0;
  private State chooseGoods = new ChooseGoods(this);
  private State paymentState = new PaymentState(this);
  private State dispenseCommodityState = new DispenseCommodityState(this);
  private State emptyState = new EmptyState(this);

  public VendingMachine(int count) {
    this.count = count;
    this.state = this.getChooseGoods();
  }

  // 购买商品
  public void purchase() {
    // 挑选商品
    state.choose();
    // 支付成功
    if (state.payment()) {
      // 分发商品
      state.dispenseCommodity();
    }
  }
  
  // 获取商品后将商品减一
  public int getCount() {
    return count--;
  }
  
  // get和set方法 ...

}