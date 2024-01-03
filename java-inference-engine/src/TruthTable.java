import java.util.*;

public class TruthTable {
    private final List<Sentence> knowledgeBase;
    private final Sentence query;
    public final List<Map<String, Boolean>> models;

    public TruthTable(List<Sentence> knowledgeBase, Sentence query) {
        this.knowledgeBase = knowledgeBase;
        this.query = query;
        this.models = generateModels(knowledgeBase);
    }

    // Counts the number of true models where the knowledge base implies the query
    public int countTrueModels() {
        int trueModelsCount = 0;

        // Constructing a new sentence that is the conjunction of the knowledge base
        Sentence kbAndSentence = new And(knowledgeBase.toArray(new Sentence[0]));
        Implication implication = new Implication(kbAndSentence, query);

        // Evaluate each model and count those where the implication holds true
        for (Map<String, Boolean> model : models) {
            if (isModelConsistent(model)) {
                if (kbAndSentence.evaluate(model) & query.evaluate(model)) {
                    trueModelsCount++;
                }
            }
        }
        return trueModelsCount;
    }

    // Generates all possible models based on the knowledge base's symbols
    private List<Map<String, Boolean>> generateModels(List<Sentence> knowledgeBase) {
        List<Map<String, Boolean>> models = new ArrayList<>();
        List<String> symbols = extractSymbols(knowledgeBase);

        int numModels = (int) Math.pow(2, symbols.size());

        // Generate all possible combinations of truth values for the symbols
        for (int i = 0; i < numModels; i++) {
            Map<String, Boolean> model = new LinkedHashMap<>();
            for (int j = 0; j < symbols.size(); j++) {
                String symbol = symbols.get(j);
                boolean value = ((i >> j) & 1) == 1;
                model.put(symbol, value);
            }
            models.add(model);
        }
        return models;
    }

    // Extracts unique symbols used in the knowledge base
    private List<String> extractSymbols(List<Sentence> knowledgeBase) {
        Set<String> symbolsSet = new HashSet<>();
        for (Sentence sentence : knowledgeBase) {
            symbolsSet.addAll(sentence.symbols());
        }
        return new ArrayList<>(symbolsSet);
    }

    // Checks if a given model is consistent with the knowledge base
    private boolean isModelConsistent(Map<String, Boolean> model) {
        for (Sentence sentence : knowledgeBase) {
            if (!sentence.evaluate(model)) {
                return false; // Model inconsistent with KB
            }
        }
        return true; // Model consistent with KB
    }
}
