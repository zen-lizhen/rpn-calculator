package demo.entity.operator;

import org.junit.Test;

import demo.entity.IEntity;
import demo.entity.operand.Operand;
import demo.exception.RpnException;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

public class DivisionTest {
  static Random rand = new Random();

  @Test public void testConstructor() {
    var division = new Division();

    assertTrue("Division should be instance of Division", division instanceof Division);
    assertTrue("Division should be instance of Operator", division instanceof Operator);
    assertTrue("Division should be instance of IEntity", division instanceof IEntity);
  }

  @Test public void testNotion() {
    var division = new Division();

    assertEquals("Division should have correct notion", division.getNotion(), Division.NOTION);
  }

  @Test public void testArgNums() {
    var division = new Division();

    assertEquals("Division should take correct number of arguments", division.getNumArgs(), Division.NUM_ARGS);
  }

  @Test public void testPerform() throws RpnException {
    var division = new Division();
    assertEquals("division's result of perform without args should be itself", division, division.perform());

    var v1 = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt()).setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var v2 = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt()).setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var expectedResultValue = v1.divide(v2, new MathContext(Math.min(v1.precision(), v2.precision())));
    var operand1 = new Operand(v1);
    var operand2 = new Operand(v2);   
    var result = division.perform(new Operand[]{operand1, operand2});
    
    assertTrue("Division's result should be instance of Operand", result instanceof Operand);
    assertTrue("Division's result should be instance of IEntity", result instanceof IEntity);
    assertEquals("Division's result should hold correct value", ((Operand)result).getValue(), expectedResultValue);
  }

  @Test public void testParse() throws RpnException {
    var division1 = Operator.parse(Division.NOTION);
    assertTrue("division should be instance of Division", division1 instanceof Division);

    var division2 = Operator.parse("/");
    assertTrue("division should be instance of Division", division2 instanceof Division);
  }
}