import java.io.*;
import java.util.*;

// Abstract class representing a logical sentence
public abstract class Sentence {

    // Method to evaluate the sentence based on a truth assignment
    public abstract boolean evaluate(Map<String, Boolean> model);

    // Method to retrieve the formula representing the sentence
    public abstract String formula();

    // Method to retrieve the set of symbols present in the sentence
    public abstract Set<String> symbols();


    // Method to validate if the provided object is a logical sentence
    public static void validate(Sentence sentence) {
        if (!(sentence instanceof Sentence)) {
            throw new IllegalArgumentException("Must be a logical sentence");
        }
    }

    // Method to parenthesize an expression
    public static String parenthesize(String s) {
        if (s.length() == 0 || s.matches("[a-zA-Z]+")
                || (s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')'
                && balanced(s.substring(1, s.length() - 1)))) {
            return s;
        } else {
            return "(" + s + ")";
        }
    }

    // Method to check if parentheses are balanced in a string
    private static boolean balanced(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                count++;
            } else if (c == ')') {
                if (count <= 0) {
                    return false;
                }
                count--;
            }
        }
        return count == 0;
    }
}
