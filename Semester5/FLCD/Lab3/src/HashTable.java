import java.util.ArrayList;
import java.util.Objects;

public class HashTable {
    private final Integer size;
    private final ArrayList<ArrayList<String>> table;

    public HashTable(Integer size) {
        this.size = size;
        this.table = new ArrayList<>();

        for (int i = 0; i < size; i ++) {
            this.table.add(new ArrayList<>());
        }
    }

    public Integer hashFunction(String symbol) {

        int charactersSum = 0;
        for (int i = 0; i < symbol.length(); i++) {
            charactersSum += symbol.charAt(i);
        }

        return charactersSum % this.size;
    }

    public String findSymbolByPosition(Pair pos) {
        if (this.table.get((Integer) pos.getFirst()).isEmpty()) {
            return null;
        }

        if (this.table.get((Integer) pos.getFirst()).get((Integer) pos.getSecond()) == null) {
            return null;
        }

        return this.table.get((Integer) pos.getFirst()).get((Integer) pos.getSecond());
    }

    public Pair findPositionOfSymbol(String symbol) {
        int pos = hashFunction(symbol);

        if (!this.table.get(pos).isEmpty()) {
            for (int i = 0; i < this.table.get(pos).size(); i++) {
                if (Objects.equals(this.table.get(pos).get(i), symbol)) {
                    return new Pair(pos, i);
                }
            }
        }
        return null;
    }

    public boolean containsSymbol(String symbol) {
        for (ArrayList<String> strings : this.table) {
            if (strings.contains(symbol)) {
                return true;
            }
        }
        return false;
    }

    public boolean addSymbol(String symbol) {
        if (this.containsSymbol(symbol)) {
            return false;
        }

        int pos = hashFunction(symbol);

        this.table.get(pos).add(symbol);

        return true;
    }

    public boolean removeSymbol(String symbol) {
        if (this.containsSymbol(symbol)) {
            int pos = hashFunction(symbol);
            return this.table.get(pos).remove(symbol);
        }
        return false;
    }

    public Integer getSize() {
        return size;
    }

    public ArrayList<ArrayList<String>> getTable() {
        return table;
    }

    @Override
    public String toString() {
        StringBuilder computedString = new StringBuilder();
        for(int i = 0; i < this.table.size(); i++){
            if(this.table.get(i).size() > 0){
                computedString.append(i);
                computedString.append(" - ");
                computedString.append(this.table.get(i));
                computedString.append("\n");
            }
        }
        return computedString.toString();
    }
}
