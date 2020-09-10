package unit;

import java.util.Objects;

public abstract class Unit {

  public static void assertEquals(char expected, char result) {
    if (Objects.equals(expected, result)) {
      return;
    }
    System.err.println("test failure, expected " + expected + " but " + result);
  }

  public static void assertEquals(boolean expected, boolean result) {
    if (Objects.equals(expected, result)) {
      return;
    }
    System.err.println("test failure, expected " + expected + " but " + result);
  }

  public static void assertEquals(String expected, String result) {
    if (Objects.equals(expected, result)) {
      return;
    }
    System.err.println("test failure, expected " + expected + " but " + result);
  }

  public static void assertEquals(int expected, int result) {
    if (Objects.equals(expected, result)) {
      return;
    }
    System.err.println("test failure, expected " + expected + " but " + result);
  }

  public static void assertEquals(long expected, long result) {
    if (Objects.equals(expected, result)) {
      return;
    }
    System.err.println("test failure, expected " + expected + " but " + result);
  }

  public static void assertEquals(byte expected, byte result) {
    if (Objects.equals(expected, result)) {
      return;
    }
    System.err.println("test failure, expected " + expected + " but " + result);
  }
}
