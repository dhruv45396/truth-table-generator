package com.example.truthtable.logic;

public class LogicEvaluator {

    public static boolean evaluate(boolean a, boolean b, String op) {

        switch(op) {
            case "AND": return a && b;
            case "OR": return a || b;
            case "IMPLIES": return !a || b;
            case "BICONDITIONAL": return a == b;
        }

        return false;
    }
}