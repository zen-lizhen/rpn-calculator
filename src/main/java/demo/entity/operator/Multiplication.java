package demo.entity.operator;

import demo.entity.IEntity;
import demo.entity.operand.Operand;

public class Multiplication extends Operator {
  public static final String NOTION = "*";
  public static final int NUM_ARGS = 2;
  
  public Multiplication() {
    super(Multiplication.NOTION, Multiplication.NUM_ARGS);
  }

  public IEntity perform(Operand... args) {
    double resultValue = args[0].getValue() * args[1].getValue();
    return new Operand(resultValue);
  }
}