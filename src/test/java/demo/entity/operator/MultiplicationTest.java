package demo.entity.operator;

import org.junit.Test;

import demo.entity.IEntity;
import demo.entity.operand.Operand;
import demo.exception.RpnException;

import static org.junit.Assert.*;

import java.util.Random;

public class MultiplicationTest {
  static Random rand = new Random();

  @Test public void testConstructor() {
    var multiplication = new Multiplication();

    assertTrue("Multiplication should be instance of Multiplication", multiplication instanceof Multiplication);
    assertTrue("Multiplication should be instance of Operator", multiplication instanceof Operator);
    assertTrue("Multiplication should be instance of IEntity", multiplication instanceof IEntity);
  }

  @Test public void testNotion() {
    var multiplication = new Multiplication();

    assertEquals("Multiplication should have correct notion", multiplication.getNotion(), Multiplication.NOTION);
  }

  @Test public void testArgNums() {
    var multiplication = new Multiplication();

    assertEquals("Multiplication should take correct number of arguments", multiplication.getNumArgs(), Multiplication.NUM_ARGS);
  }

  @Test public void testPerform() throws RpnException {
    var multiplication = new Multiplication();
    assertEquals("Multiplication's result of perform without args should be itself", multiplication, multiplication.perform());

    var v1 = rand.nextDouble() * rand.nextInt(100);
    var v2 = rand.nextDouble() * rand.nextInt(100);
    var expectedResultValue = v1 * v2;
    var operand1 = new Operand(v1);
    var operand2 = new Operand(v2);
    var result = multiplication.perform(new Operand[]{operand1, operand2});
    
    assertTrue("Multiplication's result should be instance of Operand", result instanceof Operand);
    assertTrue("Multiplication's result should be instance of IEntity", result instanceof IEntity);
    assertEquals("Multiplication's result should hold correct value", ((Operand)result).getValue(), expectedResultValue, 0.0000000000000009);
  }

  @Test public void testParse() throws RpnException {
    var multiplication1 = Operator.parse(Multiplication.NOTION);
    assertTrue("multiplication should be instance of Multiplication", multiplication1 instanceof Multiplication);

    var multiplication2 = Operator.parse("*");
    assertTrue("multiplication should be instance of Multiplication", multiplication2 instanceof Multiplication);
  }
}