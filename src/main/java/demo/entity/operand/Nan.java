package demo.entity.operand;

public class Nan extends Operand {
  public static final String NOTION = "NaN";

  public Nan() {
    this.notion = NOTION;
    this.value = null;
  }  
}