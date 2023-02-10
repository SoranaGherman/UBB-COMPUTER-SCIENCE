package Model.Adt;

import java.util.HashMap;

public interface ILatchTable {

    void put(int key, int Value);
    int get(int key);
    boolean containsKey(int key);
    void update(int key, int Value);

    void setContent(HashMap<Integer, Integer> latchTable);
    HashMap<Integer, Integer> getContent();
    int getFreeValue();
}
