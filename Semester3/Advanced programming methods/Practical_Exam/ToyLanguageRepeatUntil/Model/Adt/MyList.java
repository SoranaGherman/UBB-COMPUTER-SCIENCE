package Model.Adt;

import Model.Adt.MyIList;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    private List<T> arr;

    public MyList(List<T> arr) {
        this.arr = arr;
    }

    public MyList() {
        this.arr = new ArrayList<T>();
    }

    @Override
    public void add(T val) {
        arr.add(val);
    }

    @Override
    public List<T> getList() {
        return this.arr;
    }

    public List<T> getArr() {
        return arr;
    }

    public void setArr(List<T> arr) {
        this.arr = arr;
    }

    @Override
    public String toString() {
        return arr.toString();
    }
}
