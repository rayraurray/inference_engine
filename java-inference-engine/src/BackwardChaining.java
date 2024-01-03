import java.util.*;

public class BackwardChaining {
    private final List<Sentence> knowledgeBase;
    private final Sentence query;
    private final Set<String> known;
    private final Set<String> entailedSymbols;

    public BackwardChaining(List<Sentence> knowledgeBase, Sentence query) {
        this.knowledgeBase = knowledgeBase;
        this.query = query;
        this.known = new LinkedHashSet<>();
        this.entailedSymbols = new LinkedHashSet<>();
    }

    // Checks if the query is entailed by the knowledge base using backward chaining
    public boolean entailed() {
        Queue<Sentence> agenda = new LinkedList<>();
        agenda.add(query);

        // Add symbols known initially from the knowledge base
        for (Sentence sentence : knowledgeBase) {
            if (sentence instanceof Symbol) {
                Symbol symbol = (Symbol) sentence;
                known.add(symbol.formula());
                entailedSymbols.add(query.formula());
            }
        }

        // Backward chaining process
        while (!agenda.isEmpty()) {
            Sentence p = agenda.poll();

            // Check if the query is entailed
            if (evaluateQuery(p, known)) {
                return true;
            }

            // Traverse the knowledge base to find implications
            for (Sentence sentence : knowledgeBase) {
                if (sentence instanceof Implication) {
                    Implication implication = (Implication) sentence;
                    if (implication.getConsequent().formula().equals(p.formula())) {
                        Sentence antecedent = implication.getAntecedent();

                        if (antecedent instanceof Symbol){
                            agenda.add(antecedent);
                            entailedSymbols.add(antecedent.formula());
                        } else if (antecedent instanceof And) {
                            Sentence[] conjuncts = ((And) antecedent).getConjuncts();
                            for (Sentence conjunct : conjuncts){
                                if(!known.contains(conjunct.formula())) {
                                    agenda.add(conjunct);
                                    entailedSymbols.add(conjunct.formula());
                                }
                            }
                        }

                    }
                }
            }

        }

        return false; // Query is not entailed by the knowledge base
    }

    // Evaluate the query based on the known symbols
    public boolean evaluateQuery(Sentence query, Set<String> known) {
        Map<String, Boolean> model = new HashMap<>();
        for (String symbol : known) {
            model.put(symbol, true);
        }

        boolean result = query.evaluate(model);

        return result;
    }

    // Get the symbols entailed by the query after backward chaining
    public String getEntailedSymbols() {
        List<String> reversedList = new ArrayList<>(entailedSymbols);
        Collections.reverse(reversedList);

        StringBuilder stringBuilder = new StringBuilder();
        for (String symbol : reversedList) {
            stringBuilder.append(symbol).append(", ");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2); // Remove the last comma and space
        }
        return stringBuilder.toString();
    }

}
