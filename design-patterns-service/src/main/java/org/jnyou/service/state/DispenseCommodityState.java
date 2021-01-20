package org.jnyou.service.state;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * DispenseCommodityState **分发商品状态类**
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class DispenseCommodityState implements State {

  VendingMachine machine;

  public DispenseCommodityState(VendingMachine machine) {
    this.machine = machine;
  }

  @Override
  public void choose() {
    System.out.println("请及时取走您的商品！");
  }

  @Override
  public boolean payment() {
    System.out.println("请及时取走您的商品！");
    return false;
  }

  @Override
  public void dispenseCommodity() {
    System.out.println("请及时取走您的商品！");
    machine.setState(machine.getChooseGoods());
  }
}