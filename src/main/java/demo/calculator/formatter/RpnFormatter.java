package demo.calculator.formatter;

import demo.calculator.unit.RpnUnit;

import java.util.Collection;

public class RpnFormatter implements IFormatter {
  
  @Override
  public String format(Collection<?> result) {
    if (result == null || result.size() == 0) {
      return "";
    }
    var resultItems = result.stream().map(item -> ((RpnUnit) item).getNotion()).toArray();
    StringBuffer sb = new StringBuffer();
    for (Object item : resultItems) {
      sb.append(item.toString());
      sb.append(" ");
    }
    return sb.toString();
  }
}