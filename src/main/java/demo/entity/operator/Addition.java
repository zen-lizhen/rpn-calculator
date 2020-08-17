package demo.entity.operator;

import demo.entity.IEntity;
import demo.entity.operand.Operand;

public class Addition extends Operator {
  public static final String NOTION = "+";
  public static final int NUM_ARGS = 2;
  
  public Addition() {
    super(Addition.NOTION, Addition.NUM_ARGS);
  }

  public IEntity perform(Operand... args) {
    // super.perform(argList);

    double resultValue = args[0].getValue() + args[1].getValue();
    return new Operand(resultValue);
  }
}