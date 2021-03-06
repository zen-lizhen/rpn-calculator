package demo.entity.operator;

import org.junit.Test;

import demo.entity.IEntity;
import demo.entity.operand.Operand;
import demo.exception.RpnException;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

public class SubtractionTest {
  static Random rand = new Random();

  @Test public void testConstructor() {
    var subtraction = new Subtraction();

    assertTrue("Subtraction should be instance of Subtraction", subtraction instanceof Subtraction);
    assertTrue("Subtraction should be instance of Operator", subtraction instanceof Operator);
    assertTrue("Subtraction should be instance of IEntity", subtraction instanceof IEntity);
  }

  @Test public void testNotion() {
    var subtraction = new Subtraction();

    assertEquals("Subtraction should have correct notion", subtraction.getNotion(), Subtraction.NOTION);
  }

  @Test public void testArgNums() {
    var subtraction = new Subtraction();

    assertEquals("Subtraction should take correct number of arguments", subtraction.getNumArgs(), Subtraction.NUM_ARGS);
  }

  @Test public void testPerform() throws RpnException {
    var subtraction = new Subtraction();
    assertEquals("subtractioon's result of perform without args should be itself", subtraction, subtraction.perform());

    var v1 = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt()).setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var v2 = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt()).setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var expectedResultValue = v1.subtract(v2, new MathContext(Math.min(v1.precision(), v2.precision())));
    var operand1 = new Operand(v1);
    var operand2 = new Operand(v2);
    var result = subtraction.perform(new Operand[]{operand1, operand2});
    
    assertTrue("Subtraction's result should be instance of Operand", result instanceof Operand);
    assertTrue("Subtraction's result should be instance of IEntity", result instanceof IEntity);
    assertEquals("Subtraction's result should hold correct value", ((Operand)result).getValue(), expectedResultValue);
  }

  @Test public void testParse() throws RpnException {
    var subtraction1 = Operator.parse(Subtraction.NOTION);
    assertTrue("subtraction should be instance of Subtraction", subtraction1 instanceof Subtraction);

    var subtraction2 = Operator.parse("-");
    assertTrue("subtraction should be instance of Subtraction", subtraction2 instanceof Subtraction);
  }
}