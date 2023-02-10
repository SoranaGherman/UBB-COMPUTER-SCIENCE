package Model.Adt;

import Model.Value.IntValue;
import Model.Value.Value;

import java.util.HashMap;

public interface ILockTable {
    void put(int key, int Value);
    int get(int key);
    boolean containsKey(int key);
    void update(int key, int Value);

    void setContent(HashMap<Integer, Integer> lockTable);
    HashMap<Integer, Integer> getContent();
    int getFreeValue();
}
