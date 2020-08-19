package demo.calculator.processor;

import demo.calculator.unit.RpnUnit;
import demo.entity.operand.Operand;
import demo.entity.operator.Operator;
import demo.exception.RpnException;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class RpnProcessor implements IProcessor {

  Stack<RpnUnit> todoStack, resultStack, opStack, argStack;

  public RpnProcessor() {
    todoStack = new Stack<RpnUnit>();
    resultStack = new Stack<RpnUnit>();
    opStack = new Stack<RpnUnit>();
    argStack = new Stack<RpnUnit>();
  }

  public void reset() {
    todoStack = new Stack<RpnUnit>();
    resultStack = new Stack<RpnUnit>();
    opStack = new Stack<RpnUnit>();
    argStack = new Stack<RpnUnit>();
  }

  @Override
  public Collection<?> process(Collection<?> inputs) throws Exception {
    if (inputs == null || inputs.size() == 0) {
      return resultStack;
    }
    todoStack = resultStack;
    resultStack = new Stack<RpnUnit>();
    for (var item : inputs) {
      todoStack.push((RpnUnit)item);
    }
    rpnProcess();
    return resultStack;
  }

  private void rpnProcess() throws RpnException {
    while (!todoStack.isEmpty()) {
      var item = todoStack.pop();
      if (item.getEntity() instanceof Operand) {
        argStack.push(item);
      } else if (item.getEntity() instanceof Operator) {
        opStack.push(item);
      } else {
        ;
      }
      perform();
    }

    while (!argStack.isEmpty()) {
      resultStack.push(argStack.pop());
    }

    if (opStack.size() != 0) {
      var op = opStack.peek();
      var rpnException = new RpnException("InsufficientParameters");
      rpnException.addMsgArg(op.getEntity().getNotion());
      rpnException.addMsgArg(op.getPosition());
      opStack.clear();
      resultStack.clear();
      argStack.clear();
      throw rpnException;
    }
  }

  private void perform() {
    if (opStack.size() == 0) {
      return;
    }
    var item = opStack.peek().getEntity();
    var requiredNumArgs = ((Operator) item).getNumArgs();
    var itemPosition = opStack.peek().getPosition();
    if (argStack.size() < requiredNumArgs) {
      return;
    }
    List<RpnUnit> args = new ArrayList<RpnUnit>();
    while (args.size() < requiredNumArgs && argStack.peek().getPosition() < itemPosition) {
      args.add(0, argStack.pop());
    }
    if (args.size() < requiredNumArgs) {
      for (RpnUnit arg : args) {
        argStack.push(arg);
      }
      return;
    }
    Operand[] arguments = new Operand[args.size()];
    for (int i = 0; i < arguments.length; i++) {
      arguments[i] = (Operand) args.get(arguments.length - 1 - i).getEntity();
    }
    todoStack.push(new RpnUnit(((Operator) opStack.pop().getEntity()).perform(arguments), -1));
  }

}