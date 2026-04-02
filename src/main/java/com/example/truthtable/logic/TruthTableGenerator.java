package com.example.truthtable.logic;

import java.util.*;

public class TruthTableGenerator {

    public static List<boolean[]> generate(int n) {
        List<boolean[]> table = new ArrayList<>();

        int rows = (int) Math.pow(2, n);

        for (int i = 0; i < rows; i++) {
            boolean[] row = new boolean[n];

            for (int j = 0; j < n; j++) {
                row[j] = (i & (1 << (n - j - 1))) != 0;
            }

            table.add(row);
        }

        return table;
    }
}