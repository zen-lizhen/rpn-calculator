package demo.entity.operand;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import demo.entity.IEntity;

public class Operand implements IEntity {
  final String notion;
  final double value;

  public static final String DECIMAL_FORMAT = "#0.##########";

  public Operand(double value) {
    this.notion = (new DecimalFormat(Operand.DECIMAL_FORMAT)).format(value);
    this.value = value;
  }

  public String getNotion() {
    return this.notion;
  }

  public double getValue() {
    return this.value;
  }

  public IEntity perform() {
    return this;
  }

  public static Operand parse(String notion) throws NumberFormatException {
    var dfs = DecimalFormatSymbols.getInstance();
    var negativeInfinityNotion = Character.toString(dfs.getMinusSign()) + dfs.getInfinity();
    var infinityNotion = dfs.getInfinity();
    var nanNotion = dfs.getNaN();
    
    if (notion.equals(negativeInfinityNotion)) {
      return new Operand(Double.parseDouble("-Infinity"));
    }

    if (notion.equals(infinityNotion)) {
      return new Operand(Double.parseDouble("Infinity"));
    }

    if (notion.equals(nanNotion)) {
      return new Operand(Double.parseDouble("NaN"));
    }

    return new Operand(Double.parseDouble(notion));
  }
}