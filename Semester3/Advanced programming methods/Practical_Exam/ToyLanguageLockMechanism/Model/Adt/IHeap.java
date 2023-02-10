package Model.Adt;

import Model.Value.Value;

import java.util.HashMap;

public interface IHeap {
    void put(int key, Value Value);
    Value get(int key);
    boolean containsKey(int key);
    void update(int key, Value Value);

    int getFreeAddress();
    void setContent(HashMap<Integer, Value> heap);
    HashMap<Integer, Value> getContent();
}
