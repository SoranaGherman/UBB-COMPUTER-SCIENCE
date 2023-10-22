import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int size = 10;
        SymbolTable symbolTableIdentifiers = new SymbolTable(size);
        SymbolTable symbolTableConstants = new SymbolTable(size);

        symbolTableIdentifiers.addSymbol("a");
        symbolTableIdentifiers.addSymbol("b");
        symbolTableIdentifiers.addSymbol("Ab");
        symbolTableIdentifiers.addSymbol("Ba");


        symbolTableConstants.addSymbol("'a'");
        symbolTableConstants.addSymbol("1");
        symbolTableConstants.addSymbol("'a'");
        symbolTableConstants.addSymbol("\"sorana\"");


        System.out.println("Constants Simple Table:");

        for (ArrayList<String> strings : symbolTableConstants.getTable()) {
            for (String stringAtPos : strings) {
                System.out.println(stringAtPos + " (" +  symbolTableConstants.findPositionOfSymbol(stringAtPos).getFirst()
                + ", " + symbolTableConstants.findPositionOfSymbol(stringAtPos).getSecond() + ")");
            }
        }

        System.out.println("\nIdentifiers Simple Table:");

        for (ArrayList<String> strings : symbolTableIdentifiers.getTable()) {
            for (String stringAtPos : strings) {
                System.out.println(stringAtPos + " (" + symbolTableIdentifiers.findPositionOfSymbol(stringAtPos).getFirst()
                        + ", " + symbolTableIdentifiers.findPositionOfSymbol(stringAtPos).getSecond() + ")");
            }
        }

        System.out.println("Check if 'a' is part of the constants table: "
                            + symbolTableConstants.containsSymbol("'a'"));

        System.out.println("Check if 's' is part of the constants table: "
                + symbolTableConstants.containsSymbol("'s'"));

        Pair pos = new Pair(3,1);

        System.out.println("Check identifier by pos: "
                + symbolTableIdentifiers.findSymbolByPosition(pos));

        System.out.println("Display hash table of constants size: "
                + symbolTableConstants.getSize());

        System.out.println("Display hash table of constants size: "
                + symbolTableConstants.getTable());

        System.out.println("Find pos of constant 'A': "
                + symbolTableConstants.findPositionOfSymbol("'A'"));
    }
}