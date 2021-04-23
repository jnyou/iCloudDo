package io.jnyou;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * BigDecimalClazz
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class BigDecimalClazz {
    public static void main(String[] args) {
        String str = "12.35";
        BigDecimal b = new BigDecimal(str);
        System.out.println(b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}

class GCSearch{
    public static void main(String[] args) {
        int []arr = {5,10,5,7,6,10};
        for(int i =0; i< arr.length ; i++){
            for (int j =0;j < arr.length - i - 1; j++) {
                int temp = arr[j +1];
                if(arr[j+1]> arr[j]){
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}

class CollectionsDemo{
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(16);
        list.add("1");
        list.add("2");
        list.add("3");
//        for (String s : list) {
//            if("2".equals(s)){
//                list.remove(s);
//            }
//        }
//        System.out.println(list);


        // 快速失败fail-fast
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            if("3".equals(iterator.next())){
                list.remove(iterator.next());
            }
        }
        System.out.println(iterator);
    }
}