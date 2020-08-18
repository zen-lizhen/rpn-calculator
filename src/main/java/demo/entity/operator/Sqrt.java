package demo.entity.operator;

import java.math.BigDecimal;
import java.math.MathContext;

import demo.entity.IEntity;
import demo.entity.operand.Nan;
import demo.entity.operand.Operand;

public class Sqrt extends Operator {
  public static final String NOTION = "sqrt";
  public static final int NUM_ARGS = 1;
  
  public Sqrt() {
    super(Sqrt.NOTION, Sqrt.NUM_ARGS);
  }

  public IEntity perform(Operand... args) {
    var superResult = super.perform(args);
    if (superResult instanceof Operand) {
      return superResult;
    }
    
    var rootend = args[0].getValue().setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var precision = rootend.precision();

    if (rootend.compareTo(BigDecimal.ZERO) == -1) {
      return new Nan();
    }
    
    BigDecimal resultValue = rootend.sqrt(new MathContext(precision));
    return new Operand(resultValue);
  }
}