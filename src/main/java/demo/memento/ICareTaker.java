package demo.memento;

public interface ICareTaker<T> {
  public void save(Memento<T> state);
  public Memento<T> get();
}