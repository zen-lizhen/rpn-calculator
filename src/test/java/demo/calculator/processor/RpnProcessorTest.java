package demo.calculator.processor;

import org.junit.Test;

import demo.calculator.RpnTest;
import demo.calculator.unit.RpnUnit;
import demo.entity.operator.Operator;
import demo.entity.operand.Operand;
import demo.exception.RpnException;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class RpnProcessorTest {
  static Random rand = new Random();

  @Test public void testBase() throws Exception {
    var input = RpnUnit.parse("");
    var processor = new RpnProcessor();
    var result = processor.process(input);

    assertTrue("todo stack should be empty", processor.todoStack.size() == 0);
    assertTrue("result stack should be empty", processor.resultStack.size() == 0);
    assertTrue("op stack should be empty", processor.opStack.size() == 0);
    assertTrue("args stack should be empty", processor.argStack.size() == 0);
    assertTrue("result should be empty", result.size() == 0);
  }

  // @Test public void testInductionValidPlusControl() throws Exception {
  //   var input = generateValidInput(rand.nextGaussian() > 0.95);
  //   var prev = new RpnProcessor();
  //   try {
  //     prev.process(RpnUnit.parse(input));
  //   }
  //   catch (RpnException rpnException) {
  //     fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), input));
  //   }

  //   var control = RpnTest.getRandomControl();
  //   var inputPlusControl = input + " " + control.getNotion();
  //   var curr = new RpnProcessor();
  //   try {
  //     curr.process(RpnUnit.parse(inputPlusControl));
  //   }
  //   catch (RpnException rpnException) {
  //     fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), inputPlusControl));
  //   }
    
  //   if (control instanceof Clear) {
  //     assertTrue(String.format("todo stack should be empty. input=%s", inputPlusControl), curr.todoStack.size() == 0);
  //     assertTrue(String.format("result stack should be empty. input=%s", inputPlusControl), curr.resultStack.size() == 0);
  //     assertTrue(String.format("op stack should be empty. input=%s", inputPlusControl), curr.opStack.size() == 0);
  //     assertTrue(String.format("args stack should be empty. input=%s", inputPlusControl), curr.argStack.size() == 0);
  //   }
  //   else if (control instanceof Undo) {
  //     assertTrue(String.format("todo stack should be empty. input=%s, current todoStack size=%d", inputPlusControl, curr.todoStack.size()), curr.todoStack.size() == 0);
  //     assertTrue(String.format("op stack should be empty. input=%s, current opStack size=%d", inputPlusControl, curr.opStack.size()), curr.opStack.size() == 0);
  //     assertTrue(String.format("args stack should be empty. input=%s, current argStack size=%d", inputPlusControl, curr.argStack.size()), curr.argStack.size() == 0);
  //     assertTrue(String.format("result stack should be 1 element smaller unless it was empty. input=%s, previous resultStack size=%d, previous undoneStack size=%d, current undoneStack size=%d", inputPlusControl, prev.resultStack.size(), prev.resultStack.size(), curr.resultStack.size()), prev.resultStack.size() == 0 ? curr.resultStack.size() == 0 : curr.resultStack.size() == prev.resultStack.size() - 1);
  //     var currResultNotions = curr.resultStack.stream().map(e -> e.getNotion()).toArray();
  //     var prevResultNotions = prev.resultStack.stream().map(e -> e.getNotion()).toArray();
  //     for (int i = 0; i < currResultNotions.length; i++) {
  //       assertEquals(String.format("result stack should contain same notion as previous. input=%s", inputPlusControl), prevResultNotions[i], currResultNotions[i]);
  //     }
  //   }
  //   else if (control instanceof Redo) {
  //     assertTrue(String.format("todo stack should be empty. input=%s, current todoStack size=%d", inputPlusControl, curr.todoStack.size()), curr.todoStack.size() == 0);
  //     assertTrue(String.format("op stack should be empty. input=%s, current opStack size=%d", inputPlusControl, curr.opStack.size()), curr.opStack.size() == 0);
  //     assertTrue(String.format("args stack should be empty. input=%s, current argStack size=%d", inputPlusControl, curr.argStack.size()), curr.argStack.size() == 0);
  //   }
  // }

  @Test public void testInductionValidPlusOperator() throws Exception {
    var input = generateValidInput(rand.nextGaussian() > 0.95);
    var prev = new RpnProcessor();
    try {
      prev.process(RpnUnit.parse(input));
    }
    catch (RpnException rpnException) {
      fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), input));
    }

    var operator = RpnTest.getRandomOperator();
    var inputPlusOperator = input + " " + operator.getNotion();
    var curr = new RpnProcessor();
    if (prev.resultStack.size() < operator.getNumArgs()) {
      assertThrows(String.format("should throw exception as previous result is less than number of required arguments. input=%s", inputPlusOperator), RpnException.class, () -> { curr.process(RpnUnit.parse(inputPlusOperator)); });
    }
    else {
      try {
        curr.process(RpnUnit.parse(inputPlusOperator));
      }
      catch (RpnException rpnException) {
        fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), inputPlusOperator));
      }
      assertTrue(String.format("todo stack should be empty. input=%s", inputPlusOperator), curr.todoStack.size() == 0);
      assertTrue(String.format("op stack should be empty. input=%s", inputPlusOperator), curr.opStack.size() == 0);
      assertTrue(String.format("args stack should be empty. input=%s", inputPlusOperator), curr.argStack.size() == 0);
      
      List<RpnUnit> args = new ArrayList<RpnUnit>();
      while (args.size() < operator.getNumArgs()) {
        args.add(0, prev.resultStack.pop());
      }
      Operand[] arguments = new Operand[args.size()];
      for (int i = 0; i < arguments.length; i++) {
        arguments[i] = (Operand)args.get(i).getEntity();
      }
      prev.resultStack.push(new RpnUnit(operator.perform(arguments), -1));

      var currResultNotions = curr.resultStack.stream().map(e -> e.getNotion()).toArray();
      var prevResultNotions = prev.resultStack.stream().map(e -> e.getNotion()).toArray();
      assertEquals("actual result stack is of same size as expected", currResultNotions.length, prevResultNotions.length);
      for (int i = 0; i < prevResultNotions.length; i++) {
        assertEquals(String.format("result stack should contain same notion as previous. input=%s", inputPlusOperator), prevResultNotions[i], currResultNotions[i]);
      }
    }
  }

  @Test public void testInductionValidPlusOperand() throws Exception {
    var input = generateValidInput(rand.nextGaussian() > 0.95);
    var prev = new RpnProcessor();
    try {
      prev.process(RpnUnit.parse(input));
    }
    catch (RpnException rpnException) {
      fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), input));
    }

    var operand = RpnTest.getRandomOperand(rand.nextGaussian() > 0.95);
    var inputPlusOperand = input + " " + operand.getNotion();
    var curr = new RpnProcessor();
    try {
      curr.process(RpnUnit.parse(inputPlusOperand));
    }
    catch (RpnException rpnException) {
      fail(String.format("Exception: %s encountered when process input %s", rpnException.getMessage(), inputPlusOperand));
    }
    
    assertTrue(String.format("todo stack should be empty. input=%s", inputPlusOperand), curr.todoStack.size() == 0);
    assertTrue(String.format("op stack should be empty. input=%s", inputPlusOperand), curr.opStack.size() == 0);
    assertTrue(String.format("args stack should be empty. input=%s", inputPlusOperand), curr.argStack.size() == 0);

    prev.resultStack.push(new RpnUnit(operand.perform(), -1));

    var currResultNotions = curr.resultStack.stream().map(e -> e.getNotion()).toArray();
    var prevResultNotions = prev.resultStack.stream().map(e -> e.getNotion()).toArray();
    assertEquals("actual result stack is of same size as expected", currResultNotions.length, prevResultNotions.length);
    for (int i = 0; i < prevResultNotions.length; i++) {
      assertEquals(String.format("result stack should contain same notion as previous. input=%s", inputPlusOperand), prevResultNotions[i], currResultNotions[i]);
    }
  }

  private String generateValidInput(boolean includeSpecialValue) {
    var depth = rand.nextInt(5) + 1;
    return expandValid(includeSpecialValue, depth);
  }

  private String baseValid(boolean includeSpecialValue) {
    return RpnTest.getRandomOperand(includeSpecialValue).getNotion();
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