import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Checking if the correct number of arguments is provided
        if (args.length != 2) {
            System.out.println("Usage: iengine.bat <filename> <method>");
            System.exit(1);
        }

        // Extracting filename and method from command-line arguments
        String filename = args[0];
        String method = args[1].toUpperCase();

        try {
            // Parsing the knowledge base and query from the provided file
            List<Sentence> knowledgeBase = KnowledgeBase.parseKnowledgeBase(filename);
            Sentence query = KnowledgeBase.parseQuery(filename);

            // Creating instances of different inference methods
            ForwardChaining forwardChaining = new ForwardChaining(knowledgeBase, query);
            BackwardChaining backwardChaining = new BackwardChaining(knowledgeBase, query);
            TruthTable truthTable = new TruthTable(knowledgeBase, query);

            // Executing the chosen inference method based on the provided command-line argument
            if (method.equals("FC")) {
                // Forward Chaining method
                if (forwardChaining.entailed()) {
                    System.out.println("YES: " + forwardChaining.getKnown());
                } else {
                    System.out.println("NO");
                }
            } else if (method.equals("BC")) {
                // Backward Chaining method
                if (backwardChaining.entailed()) {
                    System.out.println("YES: " + backwardChaining.getEntailedSymbols());
                } else {
                    System.out.println("NO");
                }
            } else if (method.equals("TT")) {
                // Truth Table method
                int trueModelsCount = truthTable.countTrueModels();
                if (trueModelsCount > 0) {
                    System.out.println("YES: " + trueModelsCount);
                } else {
                    System.out.println("NO");
                }
            } else {
                // Handling invalid search methods
                System.out.println("Invalid search method: " + method);
            }

        } catch (IOException e) {
            // Handling IO exceptions by printing the stack trace
            e.printStackTrace();
        }
    }
}
