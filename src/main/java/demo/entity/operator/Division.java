package demo.entity.operator;

import java.math.BigDecimal;
import java.math.MathContext;

import demo.entity.IEntity;
import demo.entity.operand.Operand;

public class Division extends Operator {
  public static final String NOTION = "/";
  public static final int NUM_ARGS = 2;
  
  public Division() {
    super(Division.NOTION, Division.NUM_ARGS);
  }

  public IEntity perform(Operand... args) {
    var superResult = super.perform(args);
    if (superResult instanceof Operand) {
      return superResult;
    }
    
    var dividend = args[0].getValue().setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var divisor = args[1].getValue().setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var precision = Math.min(dividend.precision(), divisor.precision());
    
    BigDecimal resultValue = dividend.divide(divisor, new MathContext(precision));
    return new Operand(resultValue);
  }
}