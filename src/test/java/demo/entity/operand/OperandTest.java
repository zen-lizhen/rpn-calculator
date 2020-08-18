package demo.entity.operand;

import org.junit.Test;

import demo.entity.IEntity;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

public class OperandTest {
  static Random rand = new Random();

  @Test public void testConstructor() {
    var value = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt());
    var operand = new Operand(value);

    assertTrue("Operand should be instance of Operand", operand instanceof Operand);
    assertTrue("Operand should be instance of IEntity", operand instanceof IEntity);
    assertTrue("Operand should have correct value", operand.getValue() == value);
  }

  @Test public void testNotion() {
    var value = new BigDecimal(rand.nextInt() / rand.nextInt() + rand.nextDouble() * rand.nextInt());
    var operand = new Operand(value);

    var notion = operand.getNotion();
    var notionValue = new BigDecimal(notion);
    var notionIntegralPart = notionValue.setScale(0, RoundingMode.DOWN);
    var notionFractionPart = notionValue.subtract(notionIntegralPart);

    var operandIntegralPart = operand.getValue().setScale(0, RoundingMode.DOWN);
    var operandFractionPart = operand.getValue().subtract(operandIntegralPart).setScale(Operand.SCALE, Operand.ROUNDING_MODE).stripTrailingZeros();
    
    assertEquals("Operand should have correct notation integration part", notionIntegralPart, operandIntegralPart);
    assertEquals("Operand should have correct notation fractional part", notionFractionPart, operandFractionPart);
  }

  @Test public void testValue() {
    var value = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt());
    var operand = new Operand(value);

    assertEquals("Operand should have exact value", value, operand.getValue());
  }

  @Test public void testPerform() {
    var value = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt());
    var operand = new Operand(value);

    assertEquals("Operand should return itself when performed", operand, operand.perform());
  }

  @Test public void testParse() {
    var value = new BigDecimal(rand.nextLong() / rand.nextInt() + rand.nextDouble() * rand.nextInt());
    var operand = new Operand(value);
    
    var notion = operand.getNotion();
    var notionValue = Operand.parse(notion).getValue();

    var operandValue = operand.getValue().setScale(Operand.SCALE, Operand.ROUNDING_MODE);

    assertEquals("Operand should have same value after parsing its notion", notionValue, operandValue);
  }

  @Test public void testSpecialValues() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
      SecurityException {
    List<Operand> specialValNotions = List.of(
      new Nan(),
      new Infinity(),
      new NegativeInfinity()
    );
    
    for (Operand operand : specialValNotions) {
      assertEquals("Operand should have null value", operand.getValue(), null);
      assertEquals("Operand should return itself when performed", operand, operand.perform());
      var notion = operand.getNotion();
      assertEquals("Operand should have same notion", notion, operand.getClass().getField("NOTION").get(null));
    }
  }
}