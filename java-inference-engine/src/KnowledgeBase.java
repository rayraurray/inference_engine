import java.io.*;
import java.util.*;

public class KnowledgeBase {
    // Parses the knowledge base from the given file
    public static List<Sentence> parseKnowledgeBase(String filename) throws IOException {
        List<Sentence> knowledgeBase = new ArrayList<>();
        boolean isParsingKnowledgeBase = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.equalsIgnoreCase("TELL")) {
                    isParsingKnowledgeBase = true;
                } else if (line.equalsIgnoreCase("ASK")) {
                    isParsingKnowledgeBase = false;
                } else if (isParsingKnowledgeBase && !line.isEmpty()) {
                    // Parse Horn clauses separated by semicolons
                    String[] clauses = line.split(";");
                    for (String clause : clauses) {
                        Sentence hornClause = parseClause(clause);
                        knowledgeBase.add(hornClause);
                    }
                }
            }
        }

        return knowledgeBase;
    }

    // Parses the query sentence from the given file
    public static Sentence parseQuery(String filename) throws IOException {
        Sentence query = null;
        boolean parsingQuery = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.equalsIgnoreCase("ASK")) {
                    parsingQuery = true;
                } else if (parsingQuery && !line.isEmpty()) {
                    query = parseClause(line);
                }
            }
        }

        return query;
    }

    // Parses the given clause to create a logical sentence
    public static Sentence parseClause(String clause) {
        if (clause.contains("<=>")) {
            String[] parts = clause.split("<=>");
            Sentence leftPart = parseClause(parts[0].trim());
            Sentence rightPart = parseClause(parts[1].trim());
            return new Biconditional(leftPart, rightPart);
        } else if (clause.contains("=>")) {
            String[] parts = clause.split("=>");
            Sentence antecedent = parseClause(parts[0].trim());
            Sentence consequent = parseClause(parts[1].trim());
            return new Implication(antecedent, consequent);
        } else if (clause.contains("||")) {
            return parseOrClause(clause);
        } else if (clause.contains("&")) {
            return parseAndClause(clause);
        } else {
            return parseSymbol(clause); // Parsing individual symbols
        }
    }

    // Parses clauses separated by AND operation to create an 'And' sentence
    public static Sentence parseAndClause(String clause) {
        String[] conjuncts = clause.split("&");
        List<Sentence> antecedents = new ArrayList<>();
        for (String conjunct : conjuncts) {
            antecedents.add(parseClause(conjunct.trim()));
        }
        return new And(antecedents.toArray(new Sentence[0]));
    }

    // Parses clauses separated by OR operation to create an 'Or' sentence
    public static Sentence parseOrClause(String clause) {
        String[] disjuncts = clause.split("\\|\\|");
        List<Sentence> sentences = new ArrayList<>();
        for (String disjunct : disjuncts) {
            sentences.add(parseClause(disjunct.trim()));
        }
        return new Or(sentences.toArray(new Sentence[0]));
    }

    // Parses symbols and creates Symbol or Not(Symbol) based on the presence of '~'
    public static Sentence parseSymbol(String symbol) {
        symbol = symbol.trim();
        if (symbol.startsWith("~")) {
            return new Not(new Symbol(symbol.substring(1)));
        } else {
            return new Symbol(symbol);
        }
    }
}