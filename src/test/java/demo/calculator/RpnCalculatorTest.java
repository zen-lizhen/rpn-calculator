package demo.calculator;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RpnCalculatorTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream out = System.out;

  private final RpnCalculator calculator = new RpnCalculator();

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void tearDown() {
    System.setOut(out);
  }
  
  @Test public void TestValidInteractions() {
    calculator.process("1 2");
    assertEquals("1 2 \n", outContent.toString());
    outContent.reset();

    calculator.process("+");
    assertEquals("3 \n", outContent.toString());
    outContent.reset();

    calculator.process("1 +");
    assertEquals("4 \n", outContent.toString());
    outContent.reset();

    calculator.process("sqrt");
    assertEquals("2 \n", outContent.toString());
    outContent.reset();

    calculator.process("sqrt");
    assertEquals("1.4142135624 \n", outContent.toString());
    outContent.reset();

    calculator.process("7 8 9 10");
    assertEquals("1.4142135624 7 8 9 10 \n", outContent.toString());
    outContent.reset();

    calculator.process("undo undo");
    assertEquals("1.4142135624 7 8 \n", outContent.toString());
    outContent.reset();

    calculator.process("*");
    assertEquals("1.4142135624 56 \n", outContent.toString());
    outContent.reset();

    calculator.process("clear");
    assertEquals("\n", outContent.toString());
    outContent.reset();

    calculator.process("1 2");
    assertEquals("1 2 \n", outContent.toString());
    outContent.reset();

    calculator.process("/");
    assertEquals("0.5 \n", outContent.toString());
    outContent.reset();
  }

  @Test public void TestExceptionInteractions() {
    calculator.process("1 -");
    assertEquals("Operator - (position: 2): Insufficient Parameters\n", outContent.toString());
    outContent.reset();

    // We can continue with new valid commands
    calculator.process("1 2 3");
    assertEquals("1 2 3 \n", outContent.toString());
    outContent.reset();

    calculator.process("ttt bbb");
    assertEquals("Entity ttt (position: 6): Is not a valid entity\n", outContent.toString());
    outContent.reset();
  }
}