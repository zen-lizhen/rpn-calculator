package demo.calculator;

import demo.calculator.formatter.IFormatter;
import demo.calculator.processor.IProcessor;
import demo.calculator.formatter.RpnFormatter;
import demo.calculator.input.RpnInput;
import demo.calculator.processor.RpnProcessor;

import java.util.Stack;

public class RpnCalculator implements IProcessor, IFormatter {
  private IProcessor rpnProcessor;
  private IFormatter rpnFormatter;
  private String input;

  public RpnCalculator() {
    rpnProcessor = new RpnProcessor();
    rpnFormatter = new RpnFormatter();
    this.input = "";
  }

  public void process() {
    try {
      ((RpnProcessor)rpnProcessor).process(RpnInput.parse(this.input));
      print(getResultString());
    } catch (Exception exception) {
      clearInput();
      rpnFormatter.print(exception.getMessage());
    }
  }

  public void process(String input) {
    takeInput(input);
    process();
  }

  public void print(String s) {
    clearInput();
    takeInput(s);
    rpnFormatter.print(s);
  }

  public Stack<RpnInput> getResult() {
    return ((RpnProcessor)rpnProcessor).getResult();
  }

  private String getResultString() {
    var resultStack = getResult();
    var results = resultStack.stream().map(item -> item.getNotion()).toArray();
    StringBuffer sb = new StringBuffer();
    for (Object result : results) {
      sb.append(result.toString());
      sb.append(" ");
    }
    resultStack.clear();
    return sb.toString();
  }

  private void takeInput(String input) {
    this.input += input;
  }

  private void clearInput() {
    this.input = "";
  }
}