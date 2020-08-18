package demo.entity.operand;

public class NegativeInfinity extends Operand {
  public static final String NOTION = "-Infinity";

  public NegativeInfinity() {
    this.notion = NOTION;
    this.value = null;
  }
}