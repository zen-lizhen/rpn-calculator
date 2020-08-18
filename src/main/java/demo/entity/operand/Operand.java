package demo.entity.operand;

import java.math.BigDecimal;
import java.math.RoundingMode;

import demo.entity.IEntity;

public class Operand implements IEntity {
  String notion;
  BigDecimal value;

  public static final int SCALE = 10;
  public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

  public Operand() { }

  public Operand(BigDecimal value) {
    var notionVal = value.setScale(SCALE, ROUNDING_MODE).stripTrailingZeros();
    this.notion = notionVal.toPlainString();   
    this.value = value;
  }

  public String getNotion() {
    return this.notion;
  }

  public BigDecimal getValue() {
    return this.value;
  }

  public IEntity perform() {
    return this;
  }

  public static Operand parse(String notion) throws NumberFormatException {
    var negativeInfinityNotion = NegativeInfinity.NOTION;
    var infinityNotion = Infinity.NOTION;
    var nanNotion = Nan.NOTION;
    
    if (notion.equals(negativeInfinityNotion)) {
      return new NegativeInfinity();
    }

    if (notion.equals(infinityNotion)) {
      return new Infinity();
    }

    if (notion.equals(nanNotion)) {
      return new Nan();
    }
    
    return new Operand(new BigDecimal(notion));
  }
}