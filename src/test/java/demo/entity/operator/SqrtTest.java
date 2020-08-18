package demo.entity.operator;

import org.junit.Test;

import demo.entity.IEntity;
import demo.entity.operand.Nan;
import demo.entity.operand.Operand;
import demo.exception.RpnException;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

public class SqrtTest {
  static Random rand = new Random();

  @Test public void testConstructor() {
    var sqrt = new Sqrt();

    assertTrue("Sqrt should be instance of Sqrt", sqrt instanceof Sqrt);
    assertTrue("Sqrt should be instance of Operator", sqrt instanceof Operator);
    assertTrue("Sqrt should be instance of IEntity", sqrt instanceof IEntity);
  }

  @Test public void testNotion() {
    var sqrt = new Sqrt();

    assertEquals("Sqrt should have correct notion", sqrt.getNotion(), Sqrt.NOTION);
  }

  @Test public void testArgNums() {
    var sqrt = new Sqrt();

    assertEquals("sqrt should take correct number of arguments", sqrt.getNumArgs(), Sqrt.NUM_ARGS);
  }

  @Test public void testPerform() throws RpnException {
    var sqrt = new Sqrt();
    assertEquals("sqrt's result of perform without args should be itself", sqrt, sqrt.perform());

    var v = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt()).setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    BigDecimal expectedResultValue = null;
    if (v.compareTo(BigDecimal.ZERO) != -1) {
     expectedResultValue = v.sqrt(new MathContext(v.precision()));
    }
    var operand = new Operand(v);
    var result = sqrt.perform(new Operand[]{operand});
    
    assertTrue("sqrt's result should be instance of Operand", result instanceof Operand);
    assertTrue("sqrt's result should be instance of IEntity", result instanceof IEntity);
    if (expectedResultValue == null) {
      assertTrue("sqrt's result should be instance of Nan", result instanceof Nan);
    }
    else {
      assertEquals("sqrt's result should hold correct value", ((Operand)result).getValue(), expectedResultValue);
    }
  }

  @Test public void testParse() throws RpnException {
    var sqrt1 = Operator.parse(Sqrt.NOTION);
    assertTrue("sqrt should be instance of Sqrt", sqrt1 instanceof Sqrt);

    var sqrt2 = Operator.parse("sqrt");
    assertTrue("sqrt should be instance of Sqrt", sqrt2 instanceof Sqrt);
  }
}