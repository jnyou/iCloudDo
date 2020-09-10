package lang;

import static unit.Unit.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;

public class StringTest {

  public static void main(String[] args) {
    test();

    test_reflection();
  }

  // 改变字符串
  private static void test_reflection() {
    String test = "test";
    try {
      Field filed = String.class.getDeclaredField("value");
      filed.setAccessible(true);
      char[] val = (char[]) filed.get(test);
      val[0] = 'T';
      assertEquals("Test", test);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public static void test() {
    int length = "test".length();
    assertEquals(4, length);

    boolean bc = "test".contains("tes");
    assertEquals(true, bc);

    boolean bs = "test".startsWith("tes");
    assertEquals(true, bs);

    boolean be = "test".endsWith("st");
    assertEquals(true, be);

    char cc = "test".charAt(3);
    assertEquals('t', cc);

    int ii = "test".indexOf('e');
    assertEquals(1, ii);

    String trim = " test ".trim();
    assertEquals("test", trim);

    String[] es = "test".split("e");
    assertEquals(2, es.length);
    assertEquals("t", es[0]);
    assertEquals("st", es[1]);

    String substring = "test".substring(1, 2);
    assertEquals("e", substring);

    String uc = "test".toUpperCase();
    assertEquals("TEST", uc);

    String lc = "tEst".toLowerCase();
    assertEquals("test", lc);

    String replace = "test".replace("te", "T");
    assertEquals("Tst", replace);

    String ra = "stest".replaceAll(".{2}t", "T");
    assertEquals("stT", ra);

    String x = "test";
    char[] chars = x.toCharArray();
    chars[1] = 'E';
    char xc = x.charAt(1);
    assertEquals('e', xc); // 说明 x.toCharArray 的返回值是成员变量 value 的副本。 

    String test = String.join(",", "x", "y", "z");
    assertEquals("x,y,z", test);

    String j2 = String.join(",", Arrays.asList("x", "y", "z"));
    assertEquals("x,y,z", j2);
  }
}
