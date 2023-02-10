package Model.Adt;

import java.util.HashMap;

public class FileTable <T1, T2> implements IFileTable<T1, T2>{
    private HashMap<T1, T2> dictionary;

    public FileTable(HashMap<T1, T2> dictionary) {
        this.dictionary = dictionary;
    }

    public FileTable() {
        this.dictionary = new HashMap<T1, T2>();
    }

    public HashMap<T1, T2> getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashMap<T1, T2> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public T2 lookup(T1 str) {
        return this.dictionary.get(str);
    }

    @Override
    public boolean isDefined(T1 str) {
        if(this.dictionary.get(str) != null)
            return true;
        return false;
    }

    @Override
    public void update(T1 str, T2 val) {
        if(isDefined(str))
            this.dictionary.put(str, val);
    }

    @Override
    public void add(T1 string, T2 val) {
        if(!isDefined(string))
            this.dictionary.put(string, val);
    }

    @Override
    public HashMap<T1, T2> getContent() {
        return this.dictionary;
    }

    @Override
    public void delete(T1 s) {
        dictionary.remove(s);
    }
}
