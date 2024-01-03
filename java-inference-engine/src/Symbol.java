import java.util.*;

// Class representing a Symbol sentence
public class Symbol extends Sentence {
    private String name;

    // Constructor initializing the Symbol object with a name
    public Symbol(String name) {
        this.name = name;
    }

    // Method to evaluate the truth value of the Symbol based on the given model
    @Override
    public boolean evaluate(Map<String, Boolean> model) {
        // Check if the model contains the symbol
        if (model.containsKey(name.trim())) {
            // Return the truth value of the symbol from the model
            return model.get(name);
        } else {
            // If the symbol is not found in the model, return false
            return false;
        }
    }

    // Method to retrieve the formula representing the Symbol
    @Override
    public String formula() {
        return name;
    }

    // Method to retrieve the set of symbols present in the Symbol
    @Override
    public Set<String> symbols() {
        // Create a set to hold the symbol's name
        Set<String> symbols = new HashSet<>();
        symbols.add(name); // Add the symbol's name to the set
        return symbols; // Return the set containing the symbol's name
    }
}