import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class Main {

    private static void printMenu() {
        System.out.println("1. Display the states");
        System.out.println("2. Display the alphabet");
        System.out.println("3. Display the initial state");
        System.out.println("4. Display the final states");
        System.out.println("5. Display the transitions");
        System.out.println("6. Display if given sequence is accepted by the DFA");
    }

    private static void FaOptions() {

        String filePathIdentifiers = "src/identifiers.txt";
        String filePathConstants = "src/integerConstants.txt";

        FiniteAutomaton automaton = new FiniteAutomaton();
//        automaton.readFromFile(filePathIdentifiers);
        automaton.readFromFile(filePathConstants);

        printMenu();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your option: ");

        String option = scanner.nextLine();

        while (true) {

            switch (option) {
                case "0":
                    return;
                case "1":
                    System.out.println("STATES: ");
                    System.out.println(automaton.getStates());
                    System.out.println();
                    break;

                case "2":
                    System.out.println("ALPHABET: ");
                    System.out.println(automaton.getAlphabet());
                    System.out.println();
                    break;

                case "3":
                    System.out.println("INITIAL STATE: ");
                    System.out.println(automaton.getInitialState());
                    System.out.println();
                    break;

                case "4":
                    System.out.println("FINAL STATES: ");
                    System.out.println(automaton.getFinalStates());
                    break;

                case "5":
                    System.out.println("TRANSITIONS: ");
                    System.out.println(automaton.getTransitions());
                    System.out.println();
                    break;

                case "6":
                    System.out.println("Enter sequence: ");
                    option = scanner.nextLine();
                    System.out.println(automaton.isSequenceAccepted(option));
                    System.out.println();
                    break;

                default:
                    System.out.println("Invalid command!");
                    break;

            }
            System.out.println();
            printMenu();
            System.out.println("Your option: ");
            option = scanner.nextLine();
        }
    }

    public static void ScanProgram() throws IOException {

        String filePath1 = "io/p1.txt";
        String filePath2 = "io/p2.txt";
        String filePath3 = "io/p3.txt";
        String filePath4= "io/p3Error.txt";
        String identifiersFa = "src/identifiers.txt";
        String integerConstantsFa = "src/integerConstants.txt";

        SymbolTable symbolTableIndentifiers = new SymbolTable(100);
        SymbolTable symbolTableConstants = new SymbolTable(100);
        Pif pif = new Pif();

        MyScanner scanner = new MyScanner(filePath4, symbolTableConstants, symbolTableIndentifiers, pif, identifiersFa,
                                          integerConstantsFa);
        scanner.scan();

        FileWriter symbolTableConstantsWriter = new FileWriter("stc.out");
        symbolTableConstantsWriter.write(symbolTableConstants.toString());
        symbolTableConstantsWriter.close();

        FileWriter symbolTableIdentifiersWriter = new FileWriter("sti.out");
        symbolTableIdentifiersWriter.write(symbolTableIndentifiers.toString());
        symbolTableIdentifiersWriter.close();

        FileWriter pifWriter = new FileWriter("pif.out");
        pifWriter.write(pif.toString());
        pifWriter.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("1. FA");
        System.out.println("2. Scanner");
        System.out.println("Your option: ");

        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                FaOptions();
                break;
            case "2":
                ScanProgram();
                break;

            default:
                System.out.println("Invalid command!");
                break;
        }
    }
}