package io.commchina.service;

import org.springframework.stereotype.Service;

@Service
public class LeviServiceImpl implements LeviService {

    @Override
    public boolean problem1(int i) {

//      System.out.println("problem1 访问开始时间：" + System.currentTimeMillis());

         System.out.println("执行业务逻辑。。。" + i);

//       System.out.println("problem1 访问结束时间：" + System.currentTimeMillis());

         return true;

    }

   
    @Override
    public boolean problem2() {

//       System.out.println("problem2 访问开始时间：" + System.currentTimeMillis());

         System.out.println("执行业务逻辑。。。");

//       System.out.println("problem2 访问结束时间：" + System.currentTimeMillis());

         return true;
    }

 
    @Override
    public int myException(int i) throws Exception {

         System.out.println("执行业务逻辑。。。" + i);

         if(i == 5) {

             throw new Exception("抛出异常了...");

         }

         return 1;

    }

}