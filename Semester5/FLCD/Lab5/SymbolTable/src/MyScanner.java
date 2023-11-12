import java.io.*;
import java.util.*;

public class MyScanner {
    private int currentLine;
    private final String filePath;
    private final SymbolTable symbolTableConstants;
    private final SymbolTable symbolTableIndentifiers;

    private final FiniteAutomaton automatonForIdentifiers;
    private final FiniteAutomaton automataForIntegerConstants;
    private final String identifierFaFilePath;
    private final String integerConstantsFilePath;
    private final Pif pif;
    private String errorLog;
    private Dictionary<String, Dictionary<String, Integer>> tokensDictionary;

    public MyScanner(String filePath, SymbolTable symbolTableConstants, SymbolTable symbolTableIndentifiers, Pif pif,
                     String identifierFaFilePath, String integerConstantsFilePath) {
        this.symbolTableConstants = symbolTableConstants;
        this.symbolTableIndentifiers = symbolTableIndentifiers;
        this.pif = pif;
        this.errorLog = "";
        this.filePath = filePath;
        this.currentLine = 0;
        this.tokensDictionary = this.readTokenFile();
        this.automatonForIdentifiers = new FiniteAutomaton();
        this.automataForIntegerConstants = new FiniteAutomaton();
        this.identifierFaFilePath = identifierFaFilePath;
        this.integerConstantsFilePath = integerConstantsFilePath;

        this.automatonForIdentifiers.readFromFile(identifierFaFilePath);
        this.automataForIntegerConstants.readFromFile(integerConstantsFilePath);
    }

    public void scan() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            String token = "";
            Integer position = 0;


            while ((line = reader.readLine()) != null) {
                this.currentLine++;
                line = line.strip();
                position = 0;

                while (position < line.length()) {
                    String character = String.valueOf(line.charAt(position));

                    if (character.equals("\"")) {
                        token += character;
                        position++;
                        character = String.valueOf(line.charAt(position));


                        while (!character.equals("\"")){
                            token += character;
                            position++;

                            if (position >= line.length()){
                                throw  new ScannerException("Invalid token " + token + " at line " + this.currentLine);
                            }

                            character = String.valueOf(line.charAt(position));
                        }

                        token += "\"";
                        this.processToken(token);
                        position++;
                        token = "";
                        continue;
                    }

                    if (character.equals("'")) {
                        token += character;
                        position++;
                        character = String.valueOf(line.charAt(position));


                        while (!character.equals("'")){
                            token += character;
                            position++;

                            if (position >= line.length()){
                                throw  new ScannerException("Invalid token " + token + " at line " + this.currentLine);
                            }
                            character = String.valueOf(line.charAt(position));
                        }

                        token += "'";
                        this.processToken(token);
                        position++;
                        token = "";
                        continue;
                    }

                    if (isSeparator(character)) {

                        if (!token.equals("")) {
                            this.processToken(token);
                            token = "";
                        }

                        this.processSeparator(character);
                        position++;
                        continue;
                    }

                    if (isOperator(character)) {
                        if (!token.equals("")) {
                            this.processToken(token);
                            token = "";
                        }

                        while (isOperator(character)) {
                            token += character;
                            position++;

                            if (position >= line.length()) {
                                break;
                            }

                            character = String.valueOf(line.charAt(position));
                        }

                        this.processOperator(token);
                        token = "";
                        continue;
                    }

                    token += character;
                    position++;
                }

                if (!token.equals("")) {
                    this.processToken(token);
                }
            }
        }
        catch (ScannerException e){
            this.errorLog += e;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.writeErrorLogToFile();
    }

    public boolean isSeparator(String token) {
        return this.tokensDictionary.get("separators").get(token) != null;
    }

    public boolean isOperator(String token) {
        return this.tokensDictionary.get("operators").get(token) != null;
    }

    public boolean isReservedWord(String token) {
        return this.tokensDictionary.get("reserved_words").get(token) != null;
    }

    public boolean isConstant(String token) {
        return token.matches("^'[^']'|\"[^\"]*\"$");
    }

    public void processToken(String token) throws ScannerException {
        if (isReservedWord(token)) {
            Integer index = this.tokensDictionary.get("reserved_words").get(token);
            this.pif.add(new Pair<>(index, new Pair<>(-1, -1)));
            return;
        }

        if (this.automatonForIdentifiers.isSequenceAccepted(token)) {
            processIdentifier(token);
            return;
        }

        if (this.automataForIntegerConstants.isSequenceAccepted(token)) {
            processConstant(token);
            return;
        }

        if(isConstant(token)) {
            processConstant(token);
            return;
        }

        throw new ScannerException("Invalid token " + token + " at line " + this.currentLine);

    }

    public void processSeparator(String token) {
        if (token.equals(" ")) {
            return;
        }

        Integer index = this.tokensDictionary.get("separators").get(token);
        this.pif.add(new Pair<>(index, new Pair<>(-1, -1)));
    }

    public void processOperator(String token) {
        Integer index = this.tokensDictionary.get("operators").get(token);
        this.pif.add(new Pair<>(index, new Pair<>(-1, -1)));
    }

    public void processIdentifier(String token) {
        this.symbolTableIndentifiers.addSymbol(token);
        Pair pos = this.symbolTableIndentifiers.findPositionOfSymbol(token);
        this.pif.add(new Pair<>(1, new Pair<>((Integer)pos.getFirst(), (Integer)pos.getSecond())));
    }

    public void processConstant(String token) {
        this.symbolTableConstants.addSymbol(token);
        Pair pos = this.symbolTableConstants.findPositionOfSymbol(token);
        this.pif.add(new Pair<>(2, new Pair<>((Integer)pos.getFirst(), (Integer)pos.getSecond())));
    }

    public Dictionary<String,Dictionary<String, Integer>> readTokenFile() {

        Dictionary<String, Dictionary<String, Integer>> tokens = new Hashtable<String, Dictionary<String, Integer>>();

        for (String s : Arrays.asList("separators", "operators", "reserved_words")) {
            tokens.put(s,new Hashtable<String, Integer>());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("src/token.in"))) {
            String line;
            String key = "";
            Integer index = 3;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("!!")) {
                    key = line.substring(2);
                    continue;
                }

                line = line.replaceAll("\n", "");
                if (line.equals("space")){
                    line = " ";
                }
                tokens.get(key).put(line, index);
                index++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tokens;
    }
    private void writeErrorLogToFile() {
        try (PrintWriter errorWriter = new PrintWriter("lex_error_log.txt")) {
            if (!Objects.equals(errorLog, "")){
                errorWriter.print(errorLog);
            }
            else {
                errorWriter.print("lexically correct");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printTokens() {
        Enumeration<Dictionary<String, Integer>> d = this.tokensDictionary.elements();
        while (d.hasMoreElements()) {
            Dictionary<String, Integer> e = d.nextElement();
            Enumeration<String> k = e.keys();
            while (k.hasMoreElements()) {
                String key = k.nextElement();
                System.out.println( key + " " + e.get(key));
            }
        }
    }
}

