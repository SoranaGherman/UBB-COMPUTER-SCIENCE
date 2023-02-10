package Model.Adt;

import Model.Value.Value;

import java.util.HashMap;

public class LockTable implements ILockTable{
    private HashMap<Integer, Integer> lockTable;
    private int freeLocation = 0;

    public LockTable() {
        this.lockTable = new HashMap<>();
    }

    @Override
    public void put(int key, int Value) {
        this.lockTable.put(key, Value);
    }

    @Override
    public int get(int key) {
        return this.lockTable.get(key);
    }

    @Override
    public boolean containsKey(int key) {
        return this.lockTable.get(key) != null;
    }

    @Override
    public void update(int key, int Value) {
        this.lockTable.put(key, Value);
    }

    @Override
    public void setContent(HashMap<Integer, Integer> lockTable) {
        this.lockTable = lockTable;
    }

    @Override
    public HashMap<Integer, Integer> getContent() {
        return lockTable;
    }

    @Override
    public int getFreeValue() {
        freeLocation++;
        return freeLocation;
    }
}
