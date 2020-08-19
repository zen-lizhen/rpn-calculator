package demo.calculator;

import demo.memento.ICareTaker;
import demo.memento.Memento;
import demo.memento.StackCareTaker;

public class RpnMemento {
  ICareTaker<String> doneMementoCareTaker;

  public RpnMemento() {
    doneMementoCareTaker = new StackCareTaker<>();
  }

  public void saveToDone(String state) {
    doneMementoCareTaker.save(new Memento<String>(state));
  }

  public String getLastDone() {
    var lastDone = doneMementoCareTaker.get();
    return lastDone == null ? "" : lastDone.getState();
  }
}