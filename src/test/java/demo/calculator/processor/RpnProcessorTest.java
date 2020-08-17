package demo.calculator.processor;

import org.junit.Test;

import demo.calculator.RpnTest;
import demo.calculator.input.RpnInput;

import demo.entity.control.Clear;
import demo.entity.control.Undo;
import demo.entity.control.Redo;
import demo.entity.operator.Operator;
import demo.entity.operand.Operand;
import demo.exception.RpnException;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class RpnProcessorTest {
  static Random rand = new Random();

  @Test public void testBase() throws RpnException {
    var input = RpnInput.parse("");
    var processor = new RpnProcessor();
    processor.process(input);

    assertTrue("todo stack should be empty", processor.todoStack.size() == 0);
    assertTrue("result stack should be empty", processor.resultStack.size() == 0);
    assertTrue("op stack should be empty", processor.opStack.size() == 0);
    assertTrue("args stack should be empty", processor.argStack.size() == 0);
    assertTrue("undone stack should be empty", processor.undoneStack.size() == 0);
  }

  @Test public void testInductionValidPlusControl() throws RpnException {
    var input = generateValidInput(rand.nextGaussian() > 0.95);
    var prev = new RpnProcessor();
    try {
      prev.process(RpnInput.parse(input));
    }
    catch (RpnException rpnException) {
      fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), input));
    }

    var control = RpnTest.getRandomControl();
    var inputPlusControl = input + " " + control.getNotion();
    var curr = new RpnProcessor();
    try {
      curr.process(RpnInput.parse(inputPlusControl));
    }
    catch (RpnException rpnException) {
      fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), inputPlusControl));
    }
    
    if (control instanceof Clear) {
      assertTrue(String.format("todo stack should be empty. input=%s", inputPlusControl), curr.todoStack.size() == 0);
      assertTrue(String.format("result stack should be empty. input=%s", inputPlusControl), curr.resultStack.size() == 0);
      assertTrue(String.format("op stack should be empty. input=%s", inputPlusControl), curr.opStack.size() == 0);
      assertTrue(String.format("args stack should be empty. input=%s", inputPlusControl), curr.argStack.size() == 0);
      assertTrue(String.format("undone stack should be of same size as previous. input=%s", inputPlusControl), curr.undoneStack.size() == prev.undoneStack.size());
      var currUndonNotions = curr.undoneStack.stream().map(e -> e.getNotion()).toArray();
      var prevUndonNotions = prev.undoneStack.stream().map(e -> e.getNotion()).toArray();
      for (int i = 0; i < currUndonNotions.length; i++) {
        assertEquals(String.format("undone stack should contain same notion as previous. input=%s", inputPlusControl), prevUndonNotions[i], currUndonNotions[i]);
      }
    }
    else if (control instanceof Undo) {
      assertTrue(String.format("todo stack should be empty. input=%s, current todoStack size=%d", inputPlusControl, curr.todoStack.size()), curr.todoStack.size() == 0);
      assertTrue(String.format("result stack should be 1 element smaller unless it was empty. input=%s, previous resultStack size=%d, current resultStack size=%d", inputPlusControl, prev.resultStack.size(), curr.resultStack.size()), prev.resultStack.size() == 0 ? curr.resultStack.size() == 0 : curr.resultStack.size() == prev.resultStack.size() - 1);
      assertTrue(String.format("op stack should be empty. input=%s, current opStack size=%d", inputPlusControl, curr.opStack.size()), curr.opStack.size() == 0);
      assertTrue(String.format("args stack should be empty. input=%s, current argStack size=%d", inputPlusControl, curr.argStack.size()), curr.argStack.size() == 0);
      assertTrue(String.format("undone stack should be 1 element larger unless it was empty. input=%s, previous resultStack size=%d, previous undoneStack size=%d, current undoneStack size=%d", inputPlusControl, prev.resultStack.size(), prev.undoneStack.size(), curr.undoneStack.size()), prev.resultStack.size() == 0 ? curr.undoneStack.size() == prev.undoneStack.size() : curr.undoneStack.size() == prev.undoneStack.size() + 1);
      var currUndonNotions = curr.undoneStack.stream().map(e -> e.getNotion()).toArray();
      var prevUndonNotions = prev.undoneStack.stream().map(e -> e.getNotion()).toArray();
      for (int i = 0; i < prevUndonNotions.length; i++) {
        assertEquals(String.format("undone stack should contain same notion as previous. input=%s", inputPlusControl), prevUndonNotions[i], currUndonNotions[i]);
      }
      assertEquals(String.format("undone stack top should be the same as previous result stack top. input=%s, previous resultStack top=%s, current undoneStack top=%s", inputPlusControl, prev.resultStack.peek().getNotion(), curr.undoneStack.peek().getNotion()), curr.undoneStack.peek().getNotion(), prev.resultStack.peek().getNotion());
    }
    else if (control instanceof Redo) {
      assertTrue(String.format("todo stack should be empty. input=%s, current todoStack size=%d", inputPlusControl, curr.todoStack.size()), curr.todoStack.size() == 0);
      assertTrue(String.format("result stack should be 1 element larger unless undostack was empty. input=%s, previous undoneStack size=%d, previous resultStack size=%d, current resultStack size=%d", inputPlusControl, prev.undoneStack.size(), prev.resultStack.size(), curr.resultStack.size()), prev.undoneStack.size() == 0 ? curr.resultStack.size() == prev.resultStack.size() : curr.resultStack.size() == prev.resultStack.size() + 1);
      assertTrue(String.format("op stack should be empty. input=%s, current opStack size=%d", inputPlusControl, curr.opStack.size()), curr.opStack.size() == 0);
      assertTrue(String.format("args stack should be empty. input=%s, current argStack size=%d", inputPlusControl, curr.argStack.size()), curr.argStack.size() == 0);
      assertTrue(String.format("undone stack should be 1 element smaller unless it was empty. input=%s, previous undoneStack size=%d, current undoneStack size=%d", inputPlusControl, prev.undoneStack.size(), curr.undoneStack.size()), prev.undoneStack.size() == 0 ? curr.undoneStack.size() == 0 : curr.undoneStack.size() == prev.undoneStack.size() - 1);
      var currResultNotions = curr.resultStack.stream().map(e -> e.getNotion()).toArray();
      var prevResultNotions = prev.resultStack.stream().map(e -> e.getNotion()).toArray();
      for (int i = 0; i < prevResultNotions.length; i++) {
        assertEquals(String.format("result stack should contain same notion as previous. input=%s", inputPlusControl), prevResultNotions[i], currResultNotions[i]);
      }
      if (!prev.undoneStack.isEmpty()) {
        assertEquals(String.format("result stack top should be the same as previous undone stack top. input=%s, previous undoneStack top=%s, current resultStack top=%s", inputPlusControl, prev.undoneStack.peek().getNotion(), curr.resultStack.peek().getNotion()), curr.resultStack.peek().getNotion(), prev.undoneStack.peek().getNotion());
      }
    }
  }

  @Test public void testInductionValidPlusOperator() throws RpnException {
    var input = generateValidInput(rand.nextGaussian() > 0.95);
    var prev = new RpnProcessor();
    try {
      prev.process(RpnInput.parse(input));
    }
    catch (RpnException rpnException) {
      fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), input));
    }

    var operator = RpnTest.getRandomOperator();
    var inputPlusOperator = input + " " + operator.getNotion();
    var curr = new RpnProcessor();
    if (prev.resultStack.size() < operator.getNumArgs()) {
      assertThrows(String.format("should throw exception as previous result is less than number of required arguments. input=%s", inputPlusOperator), RpnException.class, () -> { curr.process(RpnInput.parse(inputPlusOperator)); });
    }
    else {
      try {
        curr.process(RpnInput.parse(inputPlusOperator));
      }
      catch (RpnException rpnException) {
        fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), inputPlusOperator));
      }
      assertTrue(String.format("todo stack should be empty. input=%s", inputPlusOperator), curr.todoStack.size() == 0);
      assertTrue(String.format("op stack should be empty. input=%s", inputPlusOperator), curr.opStack.size() == 0);
      assertTrue(String.format("args stack should be empty. input=%s", inputPlusOperator), curr.argStack.size() == 0);
      assertTrue(String.format("undone stack should be of same size as previous. input=%s", inputPlusOperator), curr.undoneStack.size() == prev.undoneStack.size());
      
      List<RpnInput> args = new ArrayList<RpnInput>();
      while (args.size() < operator.getNumArgs()) {
        args.add(0, prev.resultStack.pop());
      }
      Operand[] arguments = new Operand[args.size()];
      for (int i = 0; i < arguments.length; i++) {
        arguments[i] = (Operand)args.get(i).getEntity();
      }
      prev.resultStack.push(new RpnInput(operator.perform(arguments), -1));

      var currResultNotions = curr.resultStack.stream().map(e -> e.getNotion()).toArray();
      var prevResultNotions = prev.resultStack.stream().map(e -> e.getNotion()).toArray();
      assertEquals("actual result stack is of same size as expected", currResultNotions.length, prevResultNotions.length);
      for (int i = 0; i < prevResultNotions.length; i++) {
        assertEquals(String.format("result stack should contain same notion as previous. input=%s", inputPlusOperator), prevResultNotions[i], currResultNotions[i]);
      }
    }
  }

  @Test public void testInductionValidPlusOperand() throws RpnException {
    var input = generateValidInput(rand.nextGaussian() > 0.95);
    var prev = new RpnProcessor();
    try {
      prev.process(RpnInput.parse(input));
    }
    catch (RpnException rpnException) {
      fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), input));
    }

    var operand = RpnTest.getRandomOperand(rand.nextGaussian() > 0.95);
    var inputPlusOperand = input + " " + operand.getNotion();
    var curr = new RpnProcessor();
    try {
      curr.process(RpnInput.parse(inputPlusOperand));
    }
    catch (RpnException rpnException) {
      fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), inputPlusOperand));
    }
    
    assertTrue(String.format("todo stack should be empty. input=%s", inputPlusOperand), curr.todoStack.size() == 0);
    assertTrue(String.format("op stack should be empty. input=%s", inputPlusOperand), curr.opStack.size() == 0);
    assertTrue(String.format("args stack should be empty. input=%s", inputPlusOperand), curr.argStack.size() == 0);
    assertTrue(String.format("undone stack should be of same size as previous. input=%s", inputPlusOperand), curr.undoneStack.size() == prev.undoneStack.size());

    prev.resultStack.push(new RpnInput(operand.perform(), -1));

    var currResultNotions = curr.resultStack.stream().map(e -> e.getNotion()).toArray();
    var prevResultNotions = prev.resultStack.stream().map(e -> e.getNotion()).toArray();
    assertEquals("actual result stack is of same size as expected", currResultNotions.length, prevResultNotions.length);
    for (int i = 0; i < prevResultNotions.length; i++) {
      assertEquals(String.format("result stack should contain same notion as previous. input=%s", inputPlusOperand), prevResultNotions[i], currResultNotions[i]);
    }
  }

  private String generateValidInput(boolean includeSpecialValue) {
    var depth = rand.nextInt(5) + 1;
    var count = rand.nextInt(2);
    return combineValid(includeSpecialValue, depth, count);
  }

  private String baseValid(boolean includeSpecialValue) {
    return RpnTest.getRandomOperand(includeSpecialValue).getNotion();
  }

  private String combineValid(boolean includeSpecialValue, int depth, int count) {
    if (count == 0) {
      return expandValid(includeSpecialValue, depth);
    }
    else {
      var s = "";
      for (int i = 0; i < count; i++) {
        s += expandValid(includeSpecialValue, depth) + " " + RpnTest.getRandomControl().getNotion() + " ";
      }
      return s;
    }
  }

  private String expandValid(boolean includeSpecialValue, int depth) {
    if (depth == 0) {
      return baseValid(includeSpecialValue);
    }
    else {
      if (rand.nextBoolean()) {
        return expandValid(includeSpecialValue, depth - 1) + " " + expandValid(includeSpecialValue, depth - 1) + " ";
      }
      else {
        Operator operator = RpnTest.getRandomOperator();
        String s = "";
        for (int i = 0; i < operator.getNumArgs(); i++) {
          s += expandValid(includeSpecialValue, depth - 1) + " ";
        }
        s += operator.getNotion() + " ";
        return s;
      }
    }
  }
}