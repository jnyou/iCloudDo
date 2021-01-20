package org.jnyou.service.state;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * PaymentState **商品售空状态类**
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class EmptyState implements State {

  VendingMachine machine;

  public EmptyState(VendingMachine machine) {
    this.machine = machine;
  }

  @Override
  public void choose() {
    System.out.println("对不起商品已售罄！");
  }

  @Override
  public boolean payment() {
    System.out.println("对不起商品已售罄！");
    return false;
  }

  @Override
  public void dispenseCommodity() {
    System.out.println("对不起商品已售罄！");
  }
}