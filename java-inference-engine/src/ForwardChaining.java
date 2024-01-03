import java.util.*;

public class ForwardChaining {
    private final List<Sentence> knowledgeBase;
    private final Sentence query;
    private final Set<String> known;

    public ForwardChaining(List<Sentence> knowledgeBase, Sentence query) {
        this.knowledgeBase = knowledgeBase;
        this.query = query;
        this.known = new LinkedHashSet<>();
    }

    // Performs forward chaining to check if query is entailed by the knowledge base
    public boolean entailed() {
        // Initialize 'known' with symbols known from the start
        for (Sentence sentence : knowledgeBase) {
            if (sentence instanceof Symbol) {
                Symbol symbol = (Symbol) sentence;
                known.add(symbol.formula());
            }
        }

        // Continue forward chaining until query is evaluated true or no more changes occur
        while (!evaluateQuery(query, known)) {
            for (Sentence sentence : knowledgeBase) {
                if (sentence instanceof Implication) {
                    Implication implication = (Implication) sentence;
                    Sentence antecedent = implication.getAntecedent();

                    // Evaluate the antecedent sentence based on the 'known' symbols
                    boolean antecedentSatisfied = evaluateQuery(antecedent, known);

                    if (antecedentSatisfied) {
                        Symbol consequent = (Symbol) implication.getConsequent();

                        // Add consequent to 'known' if not already present
                        if (!known.contains(consequent.formula())) {
                            known.add(consequent.formula());
                        }
                    }
                }

                // If the query is evaluated true, return true
                if (evaluateQuery(query, known)) {
                    return true;
                }
            }
        }

        return false; // Query is not entailed by the knowledge base
    }

    // Evaluate the query using the 'known' symbols
    public boolean evaluateQuery(Sentence query, Set<String> known) {
        Map<String, Boolean> model = new HashMap<>();
        for (String symbol : known) {
            model.put(symbol, true);
        }

        return query.evaluate(model);
    }

    // Get the symbols that are known after forward chaining
    public String getKnown() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String symbol : known) {
            stringBuilder.append(symbol).append(", ");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2); // Remove the last comma and space
        }
        return stringBuilder.toString();
    }
}
