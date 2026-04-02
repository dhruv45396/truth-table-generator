package com.example.truthtable.logic;

import java.util.*;

public class ExpressionParser {

    // Get variables
    public static List<Character> getVariables(String expr) {
        Set<Character> vars = new LinkedHashSet<>();

        for (char ch : expr.toCharArray()) {
            if (Character.isLetter(ch)) {
                vars.add(ch);
            }
        }
        return new ArrayList<>(vars);
    }

    // Operator precedence
    private static int precedence(char op) {
        switch(op) {
            case '¬': return 3;
            case '∧': return 2;
            case '∨': return 1;
            case '→': return 0;
            case '↔': return -1;
        }
        return -2;
    }

    // Convert infix → postfix
    public static List<Character> toPostfix(String expr) {
        Stack<Character> stack = new Stack<>();
        List<Character> output = new ArrayList<>();

        for (char ch : expr.toCharArray()) {

            if (Character.isLetter(ch)) {
                output.add(ch);
            }

            else if (ch == '(') {
                stack.push(ch);
            }

            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.add(stack.pop());
                }
                stack.pop();
            }

            else {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch)) {
                    output.add(stack.pop());
                }
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }

    // Evaluate postfix
    public static boolean evaluatePostfix(List<Character> postfix, Map<Character, Boolean> values) {
        Stack<Boolean> stack = new Stack<>();

        for (char ch : postfix) {

            if (Character.isLetter(ch)) {
                stack.push(values.get(ch));
            }

            else if (ch == '¬') {
                boolean a = stack.pop();
                stack.push(!a);
            }

            else {
                boolean b = stack.pop();
                boolean a = stack.pop();

                switch(ch) {
                    case '∧': stack.push(a && b); break;
                    case '∨': stack.push(a || b); break;
                    case '→': stack.push(!a || b); break;
                    case '↔': stack.push(a == b); break;
                }
            }
        }

        return stack.pop();
    }
}