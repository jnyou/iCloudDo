package org.jnyou.service.state;

import java.util.Random;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * PaymentState **付款状态类**
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class PaymentState implements State {

  VendingMachine machine;

  public PaymentState(VendingMachine machine) {
    this.machine = machine;
  }

  @Override
  public void choose() {
    System.out.println("商品已选购完成请勿重复挑选");
  }

  @Override
  public boolean payment() {
    Random random = new Random();
    int num = random.nextInt(10);
    if(num % 2 == 0){
      System.out.println("付款成功！");
      machine.setState(machine.getDispenseCommodityState());
      return true;
    }
    System.out.println("付款失败，请重新支付！");
    return false;
  }

  @Override
  public void dispenseCommodity() {
    System.out.println("请先完成支付！");
  }
}