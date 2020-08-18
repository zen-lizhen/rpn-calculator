package demo.entity.operator;

import java.math.BigDecimal;
import java.math.MathContext;

import demo.entity.IEntity;
import demo.entity.operand.Operand;

public class Multiplication extends Operator {
  public static final String NOTION = "*";
  public static final int NUM_ARGS = 2;
  
  public Multiplication() {
    super(Multiplication.NOTION, Multiplication.NUM_ARGS);
  }

  public IEntity perform(Operand... args) {
    var superResult = super.perform(args);
    if (superResult instanceof Operand) {
      return superResult;
    }

    var multiplicend = args[0].getValue().setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var multiplicand = args[1].getValue().setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var precision = Math.min(multiplicend.precision(), multiplicand.precision());
    
    BigDecimal resultValue = multiplicend.multiply(multiplicand, new MathContext(precision));
    return new Operand(resultValue);
  }
}