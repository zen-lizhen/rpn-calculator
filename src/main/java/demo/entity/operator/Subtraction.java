package demo.entity.operator;

import demo.entity.IEntity;
import demo.entity.operand.Operand;

public class Subtraction extends Operator {
  public static final String NOTION = "-";
  public static final int NUM_ARGS = 2;
  
  public Subtraction() {
    super(Subtraction.NOTION, Subtraction.NUM_ARGS);
  }

  public IEntity perform(Operand... args) {
    double resultValue = args[0].getValue() - args[1].getValue();
    return new Operand(resultValue);
  }
}