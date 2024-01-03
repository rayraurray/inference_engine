import java.util.*;

public class Not extends Sentence {
    private Sentence operand;

    // Constructor accepting the operand for NOT operation
    public Not(Sentence operand) {
        validate(operand); // Validate the operand sentence
        this.operand = operand; // Assign the operand sentence
    }

    // Method to evaluate the truth value of the NOT operation based on the given model
    @Override
    public boolean evaluate(Map<String, Boolean> model) {
        return !operand.evaluate(model); // Return the negation of the operand's truth value
    }

    // Method to retrieve the formula representing the NOT sentence
    @Override
    public String formula() {
        return "~" + parenthesize(operand.formula()); // Return the formula with the negation symbol
    }

    // Method to retrieve the set of symbols present in the NOT sentence
    @Override
    public Set<String> symbols() {
        return operand.symbols(); // Return the symbols present in the operand
    }
}
