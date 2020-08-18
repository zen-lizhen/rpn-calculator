package demo.entity.operator;

import java.math.BigDecimal;
import java.math.MathContext;

import demo.entity.IEntity;
import demo.entity.operand.Operand;

public class Subtraction extends Operator {
  public static final String NOTION = "-";
  public static final int NUM_ARGS = 2;
  
  public Subtraction() {
    super(Subtraction.NOTION, Subtraction.NUM_ARGS);
  }

  public IEntity perform(Operand... args) {
    var superResult = super.perform(args);
    if (superResult instanceof Operand) {
      return superResult;
    }

    var subtrahand = args[0].getValue().setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var subtrahend = args[1].getValue().setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var precision = Math.min(subtrahand.precision(), subtrahend.precision());
    
    BigDecimal resultValue = subtrahand.subtract(subtrahend, new MathContext(precision));
    return new Operand(resultValue);
  }
}