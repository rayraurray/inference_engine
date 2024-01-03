import java.io.*;
import java.util.*;

public class Or extends Sentence {
    private Sentence[] disjuncts;

    // Constructor accepting disjunct sentences for OR operation
    public Or(Sentence... disjuncts) {
        for (Sentence disjunct : disjuncts) {
            validate(disjunct); // Validate each disjunct sentence
        }
        this.disjuncts = disjuncts; // Assign disjunct sentences to the disjuncts array
    }

    // Method to retrieve the disjunct sentences
    public Sentence[] getDisjuncts() {
        return disjuncts;
    }

    // Method to evaluate the truth value of the OR operation based on the given model
    @Override
    public boolean evaluate(Map<String, Boolean> model) {
        boolean result = false;
        for (Sentence disjunct : disjuncts) {
            result = result || disjunct.evaluate(model); // Evaluate each disjunct
        }
        return result; // Return the result of the OR operation
    }

    // Method to retrieve the formula representing the OR sentence
    @Override
    public String formula() {
        if (disjuncts.length == 1) {
            return disjuncts[0].formula();
        }
        StringBuilder formula = new StringBuilder();
        for (int i = 0; i < disjuncts.length; i++) {
            formula.append(parenthesize(disjuncts[i].formula())); // Parenthesize each disjunct formula
            if (i < disjuncts.length - 1) {
                formula.append(" || "); // Add " || " between disjunct formulas
            }
        }
        return formula.toString(); // Return the final formula for the OR operation
    }

    // Method to retrieve the set of symbols present in the OR sentence
    @Override
    public Set<String> symbols() {
        Set<String> symbols = new HashSet<>();
        for (Sentence disjunct : disjuncts) {
            symbols.addAll(disjunct.symbols()); // Collect symbols from each disjunct
        }
        return symbols; // Return the set containing symbols from all disjuncts
    }
}
