import java.util.ArrayList;
import java.util.Objects;

public class SymbolTable {
    private final Integer size;
    private final HashTable hashtable;

    public SymbolTable(Integer size) {
        this.size = size;
        this.hashtable = new HashTable(this.size);
    }
    public String findSymbolByPosition(Pair pos) {
        return this.hashtable.findSymbolByPosition(pos);
    }

    public Pair findPositionOfSymbol(String symbol) {
        return this.hashtable.findPositionOfSymbol(symbol);
    }

    public boolean containsSymbol(String symbol) {
        return this.hashtable.containsSymbol(symbol);
    }

    public boolean addSymbol(String symbol) {
       return this.hashtable.addSymbol(symbol);
    }


    public Integer getSize() {
        return this.hashtable.getSize();
    }

    public ArrayList<ArrayList<String>> getTable() {
        return this.hashtable.getTable();
    }

    @Override
    public String toString(){
        return this.hashtable.toString();
    }
}
