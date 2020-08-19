package demo.calculator.processor;

import java.util.Collection;

public interface IProcessor {
  public Collection<?> process(Collection<?> inputs) throws Exception;
  public void reset();
}