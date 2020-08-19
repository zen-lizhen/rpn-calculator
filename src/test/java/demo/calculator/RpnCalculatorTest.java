package demo.calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RpnCalculatorTest {
  private final RpnCalculator calculator = new RpnCalculator();
  
  @Test public void TestValidCaseOne() {
    var result = "";
    result = calculator.process("1 2");
    assertEquals("1 2 ", result);

    result = calculator.process("+");
    assertEquals("3 ", result);
    
    result = calculator.process("1 +");
    assertEquals("4 ", result);
    
    result = calculator.process("sqrt");
    assertEquals("2 ", result);

    result = calculator.process("sqrt");
    assertEquals("1.4142135624 ", result);
    
    result = calculator.process("7 8 9 10");
    assertEquals("1.4142135624 7 8 9 10 ", result);
    
    result = calculator.process("undo undo");
    assertEquals("1.4142135624 7 8 ", result);
    
    result = calculator.process("*");
    assertEquals("1.4142135624 56 ", result);
    
    result = calculator.process("clear");
    assertEquals("", result);
    
    result = calculator.process("1 2");
    assertEquals("1 2 ", result);
    
    result = calculator.process("/");
    assertEquals("0.5 ", result);
  }

  @Test public void TestExample1() {
    var result = "";
    result = calculator.process("5 2");
    assertEquals("5 2 ", result);
  }

  @Test public void TestExample2() {
    var result = "";
    result = calculator.process("2 sqrt ");
    assertEquals("1.4142135624 ", result);

    result = calculator.process("clear 9 sqrt");
    assertEquals("3 ", result);
  }

  @Test public void TestExample3() {
    var result = "";
    result = calculator.process("5 2 -");
    assertEquals("3 ", result);

    result = calculator.process("3 -");
    assertEquals("0 ", result);
    
    result = calculator.process("clear");
    assertEquals("", result);
  }

  @Test public void TestExample4() {
    var result = "";
    result = calculator.process("5 4 3 2");
    assertEquals("5 4 3 2 ", result);

    result = calculator.process("undo undo *");
    assertEquals("20 ", result);
    
    result = calculator.process("5 *");
    assertEquals("100 ", result);
    
    result = calculator.process("undo");
    assertEquals("20 5 ", result);
  }

  @Test public void TestExample5() {
    var result = "";
    result = calculator.process("7 12 2 /");
    assertEquals("7 6 ", result);

    result = calculator.process("*");
    assertEquals("42 ", result);
    
    result = calculator.process("4 /");
    assertEquals("10.5 ", result);
  }

  @Test public void TestExample6() {
    var result = "";
    result = calculator.process("1 2 3 4 5");
    assertEquals("1 2 3 4 5 ", result);

    result = calculator.process("*");
    assertEquals("1 2 3 20 ", result);
    
    result = calculator.process("clear 3 4 -");
    assertEquals("-1 ", result);
  }

  @Test public void TestExample7() {
    var result = "";
    result = calculator.process("1 2 3 4 5");
    assertEquals("1 2 3 4 5 ", result);

    result = calculator.process("* * * *");
    assertEquals("120 ", result);
  }

  @Test public void TestExceptionInteractions() {
    var result = "";
    result = calculator.process("1 -");
    assertEquals("Operator - (position: 2): Insufficient Parameters", result);
    
    // We can continue with new valid commands
    result = calculator.process("1 2 3");
    assertEquals("1 2 3 ", result);
    
    result = calculator.process("ttt bbb");
    assertEquals("Entity ttt (position: 6): Is not a valid entity", result);
  }

  @Test public void TestExample8() {
    var result = "";
    result = calculator.process("1 2 3 * 5 + * * 6 5");
    assertEquals("Operator * (position: 14): Insufficient Parameters", result);
  }
}