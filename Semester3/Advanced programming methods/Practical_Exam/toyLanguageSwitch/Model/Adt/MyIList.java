package Model.Adt;
import java.util.List;

public interface MyIList<T> {
    void add(T val);
    List<T> getList();
}
