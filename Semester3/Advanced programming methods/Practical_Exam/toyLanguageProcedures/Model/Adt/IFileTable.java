package Model.Adt;

import java.util.HashMap;

public interface IFileTable <T1,T2>{
    T2 lookup(T1 str);
    boolean isDefined(T1 str);
    void update(T1 str, T2 val);
    void add(T1 string, T2 val);

    HashMap<T1, T2> getContent();

    void delete(T1 s);
}
