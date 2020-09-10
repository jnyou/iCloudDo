package util;

import static unit.Unit.assertEquals;

import java.util.BitSet;

public class BitSetTest {

  public static void main(String[] args) {
    test_distinct();
  }

  private static void test_distinct() {
    BitSet bs = new BitSet(1000000);
    bs.set(4599, 5100);
    bs.set(6190, 6290);
    bs.set(100002);
    bs.set(180000, 200000);
    bs.set(888888, 889999);

    BitSet bs2 = new BitSet(1000000);
    bs2.set(100002);
    bs2.set(6190, 6290);
    bs2.set(101002);

    int cardinality = bs2.cardinality();
    assertEquals(102, cardinality);

    // bs2 - bs , 主要用来去重
    bs2.andNot(bs);

    int ret = 1;
    int lastOne = 0;
    while (ret != -1) {
      ret++;
      ret = bs2.nextSetBit(ret);
      if (ret != -1) {
        lastOne = ret;
      }
    }
    assertEquals(101002, lastOne);
  }
}
