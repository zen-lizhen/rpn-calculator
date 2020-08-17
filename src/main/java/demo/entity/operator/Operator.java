package demo.entity.operator;

import demo.entity.IEntity;
import demo.entity.operand.Operand;
import demo.exception.RpnException;

public abstract class Operator implements IEntity {
  final String notion;
  final int numArgs;

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
    // if (argList == null || argList.size() < this.numArgs) {
    //   var e = new RpnException("InsufficientParameters");
    //   e.addMsgArg(this.notion);
    //   throw e;
    // }
    // else if (argList.size() > this.numArgs) {
    //   var e = new RpnException("TooManyParameters");
    //   e.addMsgArg(this.notion);
    //   throw e;
    // }

    // for (IEntity entity : argList) {
    //   if (!(entity instanceof Operand)) {
    //     var e = new RpnException("CannotProcessEntity");
    //     e.addMsgArg(this.notion);
    //     e.addMsgArg(entity.getNotion());
    //     throw e;
    //   }
    // }

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