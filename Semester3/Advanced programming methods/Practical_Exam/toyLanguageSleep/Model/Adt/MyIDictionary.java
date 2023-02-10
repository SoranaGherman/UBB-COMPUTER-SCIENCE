package Model.Adt;

import Model.Value.Value;

import java.util.Collection;
import java.util.HashMap;

public interface MyIDictionary<T1, T2>{
    T2 lookup(T1 str);
    boolean isDefined(T1 str);
    void update(T1 str, T2 val);
    void add(T1 string, T2 val);
    HashMap<T1, T2> getContent();

    MyIDictionary<T1, T2> deepCopy();

    Collection<T2> values();
}
