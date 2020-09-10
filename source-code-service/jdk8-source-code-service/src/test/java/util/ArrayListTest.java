package util;

import java.util.ArrayList;

import static unit.Unit.assertEquals;

public class ArrayListTest {

  public static void main(String[] args) {
    test_toArray();
  }

  public static void test_toArray() {
    ArrayList<Integer> list = new ArrayList<>();
    list.add(1);

    Object[] obj = list.toArray();
    assertEquals(Object[].class.getName(), obj.getClass().getName());

    for (Object o : obj) {
      assertEquals(Integer.class.getName(), o.getClass().getName());
    }

    Number[] arr = new Number[list.size()];
    list.toArray(arr);
    assertEquals(Number[].class.getName(), arr.getClass().getName());
    for (Object o : arr) {
      assertEquals(Integer.class.getName(), o.getClass().getName());
    }
  }
}
