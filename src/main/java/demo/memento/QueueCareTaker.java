package demo.memento;

public class QueueCareTaker<T> implements ICareTaker<T> {

  @Override
  public void save(Memento<T> state) {
    throw new UnsupportedOperationException();

  }

  @Override
  public Memento<T> get() {
    throw new UnsupportedOperationException();
  }
}