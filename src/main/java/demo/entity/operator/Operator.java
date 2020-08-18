package demo.entity.operator;

import demo.entity.IEntity;
import demo.entity.operand.Operand;
import demo.exception.RpnException;

public abstract class Operator implements IEntity {
  final String notion;
  final int numArgs;
  public static final int SCALE = 15;

  public Operator(String notion, int numArgs) {
    this.notion = notion;
    this.numArgs = numArgs;
  }

  public String getNotion() {
    return this.notion;
  }

  public int getNumArgs() {
    return this.numArgs;
  }

  public IEntity perform() {
    return this;
  }

  public IEntity perform(Operand... args) {
    for (Operand arg : args) {
      if (arg.getValue() == null) {
        return arg;
      }
    }

    return this;
  }

  public static Operator parse(String notion) throws RpnException {
    if (notion.equals(Addition.NOTION)) {
      return new Addition();
    }
    else if (notion.equals(Subtraction.NOTION)) {
      return new Subtraction();
    }
    else if (notion.equals(Multiplication.NOTION)) {
      return new Multiplication();
    }
    else if (notion.equals(Division.NOTION)) {
      return new Division();
    }
    else if (notion.equals(Sqrt.NOTION)) {
      return new Sqrt();
    }
    else {
      return null;
    }
  }
}