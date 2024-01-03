import java.util.*;

public class Implication extends Sentence {
    private Sentence antecedent;
    private Sentence consequent;

    // Constructor accepting antecedent and consequent sentences for implication
    public Implication(Sentence antecedent, Sentence consequent) {
        validate(consequent); // Validate the consequent sentence
        validate(antecedent); // Validate the antecedent sentence

        this.antecedent = antecedent; // Set the antecedent sentence
        this.consequent = consequent; // Set the consequent sentence
    }

    // Method to get the antecedent sentence
    public Sentence getAntecedent() {
        return antecedent;
    }

    // Method to get the consequent sentence
    public Sentence getConsequent() {
        return consequent;
    }

    // Method to evaluate the truth value of the implication based on the given model
    @Override
    public boolean evaluate(Map<String, Boolean> model) {
        boolean antecedentValue = antecedent.evaluate(model);
        boolean consequentValue = consequent.evaluate(model);

        if (!antecedentValue) {
            // If antecedent is false, return true irrespective of consequent
            return true;
        } else {
            // If antecedent is true, return the value of the consequent
            return consequentValue;
        }
    }

    // Method to retrieve the formula representing the implication sentence
    @Override
    public String formula() {
        return parenthesize(antecedent.formula() + " => " + consequent.formula());
    }

    // Method to retrieve the set of symbols present in the implication sentence
    @Override
    public Set<String> symbols() {
        Set<String> symbols = new HashSet<>();
        symbols.addAll(antecedent.symbols()); // Collect symbols from antecedent
        symbols.addAll(consequent.symbols()); // Collect symbols from consequent
        return symbols; // Return the set containing symbols from both sentences
    }
}
