import java.util.*;

public class Biconditional extends Sentence{
    private Sentence left;
    private Sentence right;

    public Biconditional(Sentence left, Sentence right) {
        validate(left);
        validate(right);
        this.left = left;
        this.right = right;
    }

    public Sentence getLeft() {
        return left;
    }

    public Sentence getRight() {
        return right;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> model) {
        boolean leftValue = left.evaluate(model);
        boolean rightValue = right.evaluate(model);
        return (leftValue && rightValue) || (!leftValue && !rightValue);
    }

    @Override
    public String formula() {
        return parenthesize(left.formula()) + " <=> " + parenthesize(right.formula());
    }

    @Override
    public Set<String> symbols() {
        Set<String> symbols = new HashSet<>();
        symbols.addAll(left.symbols());
        symbols.addAll(right.symbols());
        return symbols;
    }
}
