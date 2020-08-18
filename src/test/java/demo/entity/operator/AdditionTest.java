package demo.entity.operator;

import org.junit.Test;

import demo.entity.IEntity;
import demo.entity.operand.Operand;
import demo.exception.RpnException;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

public class AdditionTest {
  static Random rand = new Random();

  @Test public void testConstructor() {
    var addition = new Addition();

    assertTrue("Addition should be instance of Addition", addition instanceof Addition);
    assertTrue("Addition should be instance of Operator", addition instanceof Operator);
    assertTrue("Addition should be instance of IEntity", addition instanceof IEntity);
  }

  @Test public void testNotion() {
    var addition = new Addition();

    assertEquals("Addition should have correct notion", addition.getNotion(), Addition.NOTION);
  }

  @Test public void testArgNums() {
    var addition = new Addition();

    assertEquals("addition should take correct number of arguments", addition.getNumArgs(), Addition.NUM_ARGS);
  }

  @Test public void testPerform() throws RpnException {
    var addition = new Addition();
    assertEquals("addition's result of perform without args should be itself", addition, addition.perform());

    var v1 = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt()).setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var v2 = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt()).setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var expectedResultValue = v1.add(v2, new MathContext(Math.min(v1.precision(), v2.precision())));
    var operand1 = new Operand(v1);
    var operand2 = new Operand(v2);    
    var result = addition.perform(new Operand[]{operand1, operand2});
    
    assertTrue("addition's result should be instance of Operand", result instanceof Operand);
    assertTrue("addition's result should be instance of IEntity", result instanceof IEntity);
    assertEquals("addition's result should hold correct value", ((Operand)result).getValue(), expectedResultValue);
  }

  @Test public void testParse() throws RpnException {
    var addition1 = Operator.parse(Addition.NOTION);
    assertTrue("addition should be instance of Addition", addition1 instanceof Addition);

    var addition2 = Operator.parse("+");
    assertTrue("addition should be instance of Addition", addition2 instanceof Addition);
  }
}