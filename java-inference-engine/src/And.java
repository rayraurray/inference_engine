import java.util.*;

// Class representing an AND sentence
public class And extends Sentence {
    private Sentence[] conjuncts;

    // Constructor accepting multiple conjuncts forming the AND sentence
    public And(Sentence... conjuncts) {
        // Validate each conjunct to ensure they are logical sentences
        for (Sentence conjunct : conjuncts) {
            validate(conjunct);
        }
        this.conjuncts = conjuncts; // Set the conjuncts for the AND sentence
    }

    // Method to get the array of conjuncts
    public Sentence[] getConjuncts() {
        return conjuncts;
    }

    // Method to evaluate the truth value of the AND sentence based on the given model
    @Override
    public boolean evaluate(Map<String, Boolean> model) {
        boolean result = true;
        for (Sentence conjunct : conjuncts) {
            // Evaluate each conjunct and combine the results using logical AND
            result = result && conjunct.evaluate(model);
        }
        return result; // Return the final evaluated result
    }

    // Method to retrieve the formula representing the AND sentence
    @Override
    public String formula() {
        if (conjuncts.length == 1) {
            return conjuncts[0].formula(); // If there's only one conjunct, return its formula
        }
        StringBuilder formula = new StringBuilder();
        // Iterate through the conjuncts to build the formula for the AND sentence
        for (int i = 0; i < conjuncts.length; i++) {
            formula.append(parenthesize(conjuncts[i].formula())); // Append the conjunct's formula
            if (i < conjuncts.length - 1) {
                formula.append(" & "); // Add "&" between conjuncts if more than one
            }
        }
        return formula.toString(); // Return the constructed formula
    }

    // Method to retrieve the set of symbols present in the AND sentence
    @Override
    public Set<String> symbols() {
        Set<String> symbols = new HashSet<>();
        // Iterate through conjuncts to collect symbols from each conjunct
        for (Sentence conjunct : conjuncts) {
            symbols.addAll(conjunct.symbols()); // Add symbols from each conjunct to the set
        }
        return symbols; // Return the set containing symbols from all conjuncts
    }
}
