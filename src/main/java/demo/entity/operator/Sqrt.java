package demo.entity.operator;

import demo.entity.IEntity;
import demo.entity.operand.Operand;

public class Sqrt extends Operator {
  public static final String NOTION = "sqrt";
  public static final int NUM_ARGS = 1;
  
  public Sqrt() {
    super(Sqrt.NOTION, Sqrt.NUM_ARGS);
  }

  public IEntity perform(Operand... args) {
    double resultValue = Math.sqrt(args[0].getValue());
    return new Operand(resultValue);
  }
}