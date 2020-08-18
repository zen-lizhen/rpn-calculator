package demo.calculator.input;

import org.junit.Test;

import demo.entity.IEntity;
import demo.entity.operand.Operand;
import demo.exception.RpnException;

import demo.calculator.RpnTest;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.List;
import java.util.LinkedList;

public class RpnInputTest {
  static Random rand = new Random();

  @Test public void testEmpty() throws RpnException {
    String emptyInput = "";
    assertTrue("empty string should not result null input list", RpnInput.parse(emptyInput) != null);
    assertTrue("empty string should result empty input list", RpnInput.parse(emptyInput).size() == 0);

    String nullInput = null;
    assertTrue("null string should not result null input list", RpnInput.parse(nullInput) != null);
    assertTrue("null string should result empty input list", RpnInput.parse(nullInput).size() == 0);
  }

  @Test public void testParse() throws RpnException {
    var length = rand.nextInt(100) + 1;
    List<IEntity> expectedEntities = new LinkedList<IEntity>();
    String inputs = "";
    while (expectedEntities.size() < length) {
      if (expectedEntities.size() % 3 == 0) {
        var operator = RpnTest.getRandomOperator();
        expectedEntities.add(operator);
        inputs += operator.getNotion() + " ";
      }
      else if (expectedEntities.size() % 3 == 1) {
        var control = RpnTest.getRandomControl();
        expectedEntities.add(control);
        inputs += control.getNotion() + " ";
      }
      else {
        var operand = RpnTest.getRandomOperand(true);
        expectedEntities.add(operand);
        inputs += operand.getNotion() + " ";
      }
    }
    
    List<RpnInput> actualInputs = RpnInput.parse(inputs);
    assertTrue("actualInputs should not be null", actualInputs != null);
    assertEquals("actualInputs should have same size as expected", actualInputs.size(), expectedEntities.size());
    var idx = rand.nextInt(actualInputs.size());
    var actualEntity = actualInputs.get(idx).getEntity();
    var expectedEntity = expectedEntities.get(idx);
    assertTrue("actual entity should be of same type as expected", actualEntity.getClass() == expectedEntity.getClass());
    if (actualEntity instanceof Operand) {
      assertEquals("actual operand should have same value as expected", ((Operand)actualEntity).getValue(), ((Operand)expectedEntity).getValue());
    }
    var actualPosition = actualInputs.get(idx).getPosition();
    var expectedNotion = inputs.substring(actualPosition, actualPosition + expectedEntity.getNotion().length());
    assertEquals("substring at actual entity's position should be same as expected", actualEntity.getNotion(), expectedNotion);
    var position = 0;
    for (int i = 0; i < idx; i++) {
      position += expectedEntities.get(i).getNotion().length() + 1;
    }
    assertTrue("actual position should be larger than length of all previous entitities", actualPosition >= position);
  }
}