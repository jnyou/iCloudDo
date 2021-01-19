package org.jnyou.anoteinventoryservice.component.gencode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Demo2 {

    /**
     * 在c盘生成验证码的图片
     * @param args
     */
    public static void main(String[] args) throws IOException {
        // 定义图片的宽度和高度
        int width = 150;
        int height = 50;
        // 在内存中生成图片缓冲 第三个参数：图片的类型  TYPE_INT_RGB red green blue
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        // 获取到画笔对象
        Graphics g = image.getGraphics();
        // 向 image 上画任何内容 直线  字符串  圆  矩形
        // 使用画笔对象向image上画填充的矩形。 实心的  矩形 == image 大小 一样大
        // 给画笔设置样式
        g.setColor(Color.gray);
        // 画填充的矩形  x y
        g.fillRect(0,0,width,height);
        // ===============================================================
        // 给画笔重新设置颜色
        g.setColor(Color.green);
        // 设置字体
        g.setFont(new Font("楷体",Font.BOLD,25));
        // 准备好一些数据  20
        String words = "abcdefghijklmnABCDEFGHIJKLMN0123456789";
        // 需求：随机获取4个字符，写到图片上就OK了
        // 随机数对象
        Random random = new Random();
        int x = 25;
        int y = 25;
        // 编写for循环，循环4次
        for (int i = 0; i < 4; i++) {
            // 随机获取某个数字以内的整数  5  10  19  18
            int num = random.nextInt(words.length());
            // 以该数字为下标值，随机获取字符串指定下标的内容
            char c = words.charAt(num);
            // 把获取到字符串画到图片上就OK了
            g.drawString(c+"",x,y);
            // 让 x 累加
            x += 25;
        }

        // 把内存中图片生成到本地磁盘上
        ImageIO.write(image,"jpg",new File("C:\\demo\\abc.jpg"));
    }

}














