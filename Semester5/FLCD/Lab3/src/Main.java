import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath1 = "io/p1.txt";
        String filePath2 = "io/p2.txt";
        String filePath3 = "io/p3.txt";

        SymbolTable symbolTableIndentifiers = new SymbolTable(100);
        SymbolTable symbolTableConstants = new SymbolTable(100);
        Pif pif = new Pif();

        Scanner scanner = new Scanner(filePath3, symbolTableConstants, symbolTableIndentifiers, pif);
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
}