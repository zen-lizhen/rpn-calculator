package demo.calculator.processor;

import demo.calculator.input.RpnInput;
import demo.entity.operand.Operand;
import demo.entity.operator.Operator;
import demo.entity.control.Clear;
import demo.entity.control.Control;
import demo.entity.control.Undo;
import demo.entity.control.Redo;
import demo.exception.RpnException;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class RpnProcessor implements IProcessor {

  Stack<demo.calculator.input.RpnInput> todoStack, resultStack, opStack, argStack, undoneStack;

  public RpnProcessor() {
    this.todoStack = new Stack<RpnInput>();
    this.resultStack = new Stack<RpnInput>();
    this.opStack = new Stack<RpnInput>();
    this.argStack = new Stack<RpnInput>();
    this.undoneStack = new Stack<RpnInput>();
  }

  public Stack<RpnInput> getResult() {
    return resultStack;
  }

  public void process() throws RpnException {
    process(null);
  }

  public void process(List<RpnInput> input) throws RpnException {
    if (input == null || input.size() == 0) {
      return;
    }
    for (demo.calculator.input.RpnInput item : input) {
      if (item.getEntity() instanceof Control) {
        rpnProcess();
        rpnControl(item);
      }
      else {
        if (resultStack.size() > 0) {
          Stack<RpnInput> temp = new Stack<RpnInput>();
          while (resultStack.size() > 0) {
            temp.push(resultStack.pop());
          }
          while (temp.size() > 0) {
            todoStack.push(temp.pop());
          }
        }
        todoStack.push(item);
      }
    }
    rpnProcess();
  }

  private void rpnControl(RpnInput item) {
    if (item.getEntity() instanceof Undo) {
      undo();
    }
    else if (item.getEntity() instanceof Clear) {
      clear();
    }
    else if (item.getEntity() instanceof Redo) {
      redo();
    }
    else {
      ;
    }
  }

  private void rpnProcess() throws RpnException {
    while (!todoStack.isEmpty()) {
      var item = todoStack.pop();
      if (item.getEntity() instanceof Operand) {
        argStack.push(item);
      }
      else if (item.getEntity() instanceof Operator) {
        opStack.push(item);
      }
      else {
        ;
      }
      perform();
    }

    if (opStack.size() != 0) {
      var op = opStack.peek();
      var rpnException = new RpnException("InsufficientParameters");
      rpnException.addMsgArg(op.getEntity().getNotion());
      rpnException.addMsgArg(op.getPosition());
      throw rpnException;
    }

    while (!argStack.isEmpty()) {
      resultStack.push(argStack.pop());
    }
  }

  private void undo() {
    if (resultStack.size() == 0) {
      return;
    }
    undoneStack.push(resultStack.pop());
  }

  private void redo() {
    if (undoneStack.size() == 0) {
      return;
    }
    resultStack.push(undoneStack.pop());
  }

  private void clear() {
    resultStack.clear();
  }

  private void perform() {
    if (opStack.size() == 0) {
      return;
    }
    var item = opStack.peek().getEntity();
    var requiredNumArgs = ((Operator)item).getNumArgs();
    var itemPosition = opStack.peek().getPosition();
    if (argStack.size() < requiredNumArgs) {
      return;
    }
    List<RpnInput> args = new ArrayList<RpnInput>();
    while (args.size() < requiredNumArgs && argStack.peek().getPosition() < itemPosition) {
      args.add(0, argStack.pop());
    }
    if (args.size() < requiredNumArgs) {
      for (RpnInput arg : args) {
        argStack.push(arg);
      }
      return;
    }
    Operand[] arguments = new Operand[args.size()];
    for (int i = 0; i < arguments.length; i++) {
      arguments[i] = (Operand)args.get(arguments.length - 1 - i).getEntity();
    }
    todoStack.push(new RpnInput(((Operator)opStack.pop().getEntity()).perform(arguments), -1));
  }
}