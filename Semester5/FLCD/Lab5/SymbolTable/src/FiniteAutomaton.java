import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FiniteAutomaton {
    private List<String> states;
    private List<String> alphabet;
    private String initialState;
    private List<String> finalStates;
    private Map<Pair<String, String>, Set<String>> transitions;
    
    public FiniteAutomaton() {
        states = new ArrayList<>();
        alphabet = new ArrayList<>();
        finalStates = new ArrayList<>();
        transitions = new HashMap<>();
    }

    public void readFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;


            while ((line = reader.readLine()) != null) {
                switch (line) {
                    case "!!states!!" -> this.states.addAll(Arrays.asList(reader.readLine().split(" ")));
                    case "!!initial_state!!" -> this.initialState = reader.readLine();
                    case "!!alphabet!!" -> this.alphabet.addAll(Arrays.asList(reader.readLine().split(" ")));
                    case "!!final_state!!" -> this.finalStates.addAll(Arrays.asList(reader.readLine().split(" ")));
                    case "!!transitions!!" -> readTransitions(reader);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTransitions(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] symbols = line.split(" ");

            if (states.contains(symbols[0]) && alphabet.contains(symbols[1]) && states.contains(symbols[2])) {

                Pair<String, String> statesOfTransitions = new Pair<>(symbols[0], symbols[1]);

                if (!transitions.containsKey(statesOfTransitions)) {
                    Set<String> transitionStatesSet = new HashSet<>();
                    transitionStatesSet.add(symbols[2]);
                    transitions.put(statesOfTransitions, transitionStatesSet);
                } else {
                    transitions.get(statesOfTransitions).add(symbols[2]);
                }

            }
        }
    }

    public boolean isDeterministic() {
        for (Pair<String, String> key: this.transitions.keySet()) {
            if(this.transitions.get(key).size() > 1) return false;
        }
        return true;
    }
    public boolean isSequenceAccepted(String seq) {

        if (!isDeterministic()) {
            return false;
        }

        String currentState = this.initialState;

        while (seq.length() > 0)  {

            Pair<String, String> currentKey = new Pair<>(currentState, String.valueOf(seq.charAt(0)));
            if (!transitions.containsKey(currentKey)) {
                return false;
            }
            currentState = this.transitions.get(currentKey).iterator().next();
            seq = seq.substring(1);
        }

        return this.finalStates.contains(currentState);
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getStates() {
        return states;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public Map<Pair<String, String>, Set<String>> getTransitions() {
        return transitions;
    }
}
