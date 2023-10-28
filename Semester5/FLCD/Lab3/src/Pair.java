public class Pair<First, Second> {
    private final First first;
    private final Second second;

    public Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    public First getFirst() {
        return this.first;
    }

    public Second getSecond() {
        return this.second;
    }

    @Override
    public String toString() {
        return "( " + first +
                ", " + second +
                " )";
    }
}
