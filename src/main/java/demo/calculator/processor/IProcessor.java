package demo.calculator.processor;

import java.util.Collection;

public interface IProcessor {
  public void process() throws Exception;
  public Collection<?> getResult();
}