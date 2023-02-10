package Model.Adt;

import Model.Value.Value;

import java.util.HashMap;

public class Heap implements IHeap{
    private HashMap<Integer, Value> heapTable;
    private int freeLocation;

    public Heap() {
        this.heapTable =  new HashMap<Integer, Value>();
        this.freeLocation = 0;
    }

    @Override
    public void put(int key, Value Value) {
        this.heapTable.put(key, Value);
    }

    @Override
    public Value get(int key) {
        return this.heapTable.get(key);
    }

    @Override
    public boolean containsKey(int key) {
        return this.heapTable.get(key) != null;
    }

    @Override
    public void update(int key, Value Value) {
        this.heapTable.put(key, Value);
    }

    @Override
    public int getFreeAddress() {
        freeLocation++;
        return freeLocation;
    }

    @Override
    public void setContent(HashMap<Integer, Value> heap) {
        this.heapTable = heap;
    }

    @Override
    public HashMap<Integer, Value> getContent() {
        return heapTable;
    }


    public int getFreeLocation() {
        return freeLocation;
    }

    public void setFreeLocation(int freeLocation) {
        this.freeLocation = freeLocation;
    }

    @Override
    public String toString() {
        return heapTable.toString();
    }
}
