import java.util.ArrayList;

public class Pif {
    private ArrayList<Pair<Integer, Pair<Integer, Integer>>> tokenAndPosition;

    public Pif() {
        this.tokenAndPosition = new ArrayList<>();
    }

    public void add(Pair<Integer, Pair<Integer, Integer>> newPair){
        this.tokenAndPosition.add(newPair);
    }

    @Override
    public String toString(){
        StringBuilder computedString = new StringBuilder();
        for(int i = 0; i < this.tokenAndPosition.size(); i++) {
            computedString.append(this.tokenAndPosition.get(i).getFirst())
                    .append(" | ")
                    .append(this.tokenAndPosition.get(i).getSecond())
                    .append("\n");
        }

        return computedString.toString();
    }
}
