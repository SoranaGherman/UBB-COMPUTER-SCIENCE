package Model.Adt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2> {

    private HashMap<T1, T2> dic;

    public MyDictionary(HashMap<T1, T2> dic) {
        this.dic = dic;
    }

    public MyDictionary() {
        this.dic = new HashMap<T1, T2>();
    }

    @Override
    public T2 lookup(T1 str) {
        return this.dic.get(str);
    }

    @Override
    public boolean isDefined(T1 str) {
        if(this.dic.get(str) != null)
            return true;
        return false;
    }

    @Override
    public void update(T1 str, T2 val) {
        if(isDefined(str))
            this.dic.put(str, val);
    }

    @Override
    public void add(T1 string, T2 val) {
        if(!isDefined(string))
            this.dic.put(string, val);
    }

    @Override
    public HashMap<T1, T2> getContent() {
        return this.dic;
    }

    @Override
    public MyIDictionary<T1, T2> deepCopy() {
        MyIDictionary<T1, T2> newDictionary = new MyDictionary<T1, T2>();
        for (Map.Entry<T1, T2> Entry: dic.entrySet())
            newDictionary.add(Entry.getKey(), Entry.getValue());

        return newDictionary;
    }

    @Override
    public Collection<T2> values() {
        synchronized (this){
            return this.dic.values();
        }
    }

    @Override
    public String toString() {
        return dic.toString();
    }
}
