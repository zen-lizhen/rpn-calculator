package demo.memento;

import java.util.Stack;

public class StackCareTaker<T> implements ICareTaker<T> {
  private Stack<Memento<T>> mementoStack = new Stack<Memento<T>>();

  public void save(Memento<T> state) {
    mementoStack.push(state);
  }

  public Memento<T> get() {
    if (mementoStack.size() == 0) {
      return null;
    }
    return mementoStack.pop();
  }
}