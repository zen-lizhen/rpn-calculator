package demo.entity.operator;

import java.math.BigDecimal;
import java.math.MathContext;

import demo.entity.IEntity;
import demo.entity.operand.Operand;

public class Addition extends Operator {
  public static final String NOTION = "+";
  public static final int NUM_ARGS = 2;
  
  public Addition() {
    super(Addition.NOTION, Addition.NUM_ARGS);
  }

  public IEntity perform(Operand... args) {
    var superResult = super.perform(args);
    if (superResult instanceof Operand) {
      return superResult;
    }

    var addend = args[0].getValue().setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var augend = args[1].getValue().setScale(Operator.SCALE, Operand.ROUNDING_MODE);
    var precision = Math.min(addend.precision(), augend.precision());

    BigDecimal resultValue = addend.add(augend, new MathContext(precision));
    return new Operand(resultValue);
  }
}