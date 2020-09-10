package sunmisc;

import static unit.Unit.assertEquals;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeTest {

  public static Unsafe unsafe;

  static {
    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      unsafe = (Unsafe) field.get(null);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    test_memory_byte();
    test_memory_int();
    test_memory_long();

    test_memory_address_size();
    test_memory_page_size();
  }

  public static void test_memory_byte() {
    long byteAddr = 0;
    try {
      byteAddr = unsafe.allocateMemory(8);
      byte val = (byte) 255;
      unsafe.putByte(byteAddr, val);
      byte ub = unsafe.getByte(byteAddr);

      assertEquals(val, ub);
    } finally {
      if (byteAddr != 0) {
        unsafe.freeMemory(byteAddr);
      }
    }
  }

  public static void test_memory_int() {
    long byteAddr = 0;
    try {
      byteAddr = unsafe.allocateMemory(32);
      int val = Integer.MAX_VALUE;
      unsafe.putInt(byteAddr, val);
      int ub = unsafe.getInt(byteAddr);

      assertEquals(val, ub);
    } finally {
      if (byteAddr != 0) {
        unsafe.freeMemory(byteAddr);
      }
    }
  }

  public static void test_memory_long() {
    long byteAddr = 0;
    try {
      byteAddr = unsafe.allocateMemory(Long.SIZE);
      long val = Long.MAX_VALUE;
      unsafe.putLong(byteAddr, val);
      long ub = unsafe.getLong(byteAddr);

      assertEquals(val, ub);
    } finally {
      if (byteAddr != 0) {
        unsafe.freeMemory(byteAddr);
      }
    }
  }

  // 32位系统 4 , 64位系统 8
  public static void test_memory_address_size() {
    int size = unsafe.addressSize();
    assertEquals(8, size);
  }

  // 一般都是 4k ? 系统内存页大小怎么调整？
  public static void test_memory_page_size() {
    int size = unsafe.pageSize();
    assertEquals(4096, size);
  }
}
