package demo.entity.operand;

import org.junit.Test;

import demo.entity.IEntity;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

public class OperandTest {
  static Random rand = new Random();

  @Test public void testConstructor() {
    var value = rand.nextDouble() * rand.nextInt(100) - rand.nextDouble() * rand.nextInt(100);
    var operand = new Operand(value);

    assertTrue("Operand should be instance of Operand", operand instanceof Operand);
    assertTrue("Operand should be instance of IEntity", operand instanceof IEntity);
    assertTrue("Operand should have correct value", operand.getValue() == value);
  }

  @Test public void testNotion() {
    var value = rand.nextDouble() * rand.nextInt(100) - rand.nextDouble() * rand.nextInt(100);
    var operand = new Operand(value);

    var notion = operand.getNotion();
    var notionValue = Double.parseDouble(notion);
    var iNotionPart = (long) notionValue;
    var fNotionPart = notionValue - iNotionPart;
    var iValuePart = (long) value;
    var fValuePart = value - iValuePart;
    
    assertTrue("Operand should have correct notation integration part", iNotionPart == iValuePart);
    assertEquals("Operand should have correct notation fractional part", fNotionPart, fValuePart, 0.00000000005);
  }

  @Test public void testValue() {
    var value = rand.nextDouble() * rand.nextInt(100) - rand.nextDouble() * rand.nextInt(100);
    var operand = new Operand(value);

    assertTrue("Operand should have exact value", value == operand.getValue());
  }

  @Test public void testPerform() {
    var value = rand.nextDouble() * rand.nextInt(100) - rand.nextDouble() * rand.nextInt(100);
    var operand = new Operand(value);

    assertEquals("Operand should return itself when performed", operand, operand.perform());
  }

  @Test public void testParse() {
    var value = rand.nextDouble() * rand.nextInt(100) - rand.nextDouble() * rand.nextInt(100);
    var operand = new Operand(value);
    var notion = operand.getNotion();
    var notionValue = Operand.parse(notion).getValue();

    assertEquals("Operand should have same value after parsing its notion", value, notionValue, 0.00000000005);
  }

  @Test public void testSpecialValues() {
    List<Double> specialValues = List.of(
      Double.parseDouble("NaN"),
      Double.parseDouble("Infinity"),
      Double.parseDouble("-NaN"),
      Double.parseDouble("-Infinity")
    );
    
    for (double value : specialValues) {
      var operand = new Operand(value);
      assertEquals("Operand should have exact value", value, operand.getValue(), 0.00000000005);
      assertEquals("Operand should return itself when performed", operand, operand.perform());
      var notion = operand.getNotion();
      var notionValue = Operand.parse(notion).getValue();
      assertEquals("Operand should have same value after parsing its notion", value, notionValue, 0.00000000005);
    }
  }
}