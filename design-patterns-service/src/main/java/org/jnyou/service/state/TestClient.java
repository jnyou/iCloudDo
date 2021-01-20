package org.jnyou.service.state;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * TestClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class TestClient {

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine(1);
        for (int i = 1; i < 4; i++) {
            System.out.println("第" + i + "次购买。");
            machine.purchase();
        }
    }

}