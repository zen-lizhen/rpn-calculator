package demo.calculator;

import demo.entity.control.Control;
import demo.entity.control.Clear;
import demo.entity.control.Redo;
import demo.entity.control.Undo;
import demo.entity.operand.Operand;
import demo.entity.operator.Operator;
import demo.entity.operator.Addition;
import demo.entity.operator.Division;
import demo.entity.operator.Multiplication;
import demo.entity.operator.Sqrt;
import demo.entity.operator.Subtraction;

import java.util.Random;
import java.lang.reflect.InvocationTargetException;

public class RpnTest {
  private static Random rand = new Random();
  public static final Class<?>[] SUPPORTED_OPERATOR_CLASS = new Class<?>[] {
    Addition.class,
    Subtraction.class,
    Multiplication.class,
    Division.class,
    Sqrt.class,
  };

  public static final Class<?>[] SUPPORTED_CONTROL_CLASS = new Class<?>[] {
    Clear.class,
    Undo.class,
    Redo.class,
  };

  public static final double[] SUPPORTED_OPERAND_VALUE = new double[] {
    Double.NaN,
    Double.parseDouble("Infinity"),
    Double.parseDouble("-Infinity"),
    rand.nextDouble() * rand.nextLong(),
  };

  public static Control getRandomControl() {
    Control control = null;
    try {
      Class<?> cls = RpnTest.SUPPORTED_CONTROL_CLASS[rand.nextInt(RpnTest.SUPPORTED_CONTROL_CLASS.length)];
      control = (Control) cls.getDeclaredConstructor().newInstance();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return control;
  }

  public static Operator getRandomOperator() {
    Operator operator = null;
    try {
      Class<?> cls = RpnTest.SUPPORTED_OPERATOR_CLASS[rand.nextInt(RpnTest.SUPPORTED_OPERATOR_CLASS.length)];
      operator = (Operator) cls.getDeclaredConstructor().newInstance();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return operator;
  }

  public static Operand getRandomOperand(boolean includeSpecialValue) {
    if (includeSpecialValue) {
      double val = RpnTest.SUPPORTED_OPERAND_VALUE[rand.nextInt(RpnTest.SUPPORTED_OPERAND_VALUE.length)];
      Operand operand = new Operand(val);
      return operand;
    }
    else {
      return new Operand(rand.nextDouble() * rand.nextInt());
    }
  }
}