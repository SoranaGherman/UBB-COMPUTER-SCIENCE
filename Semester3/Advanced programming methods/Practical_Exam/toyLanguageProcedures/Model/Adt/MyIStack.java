package Model.Adt;
import java.util.List;

public interface MyIStack<T>{
    boolean isEmpty();
    T pop();
    void push(T v);
    List<T> getReversed();
    T peek();

    MyStack<T> clone();
}
