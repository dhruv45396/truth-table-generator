package com.example.truthtable.controller;

import com.example.truthtable.logic.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class TruthTableController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String expression, Model model) {

        // 🔥 Validation
        if(expression == null || expression.trim().isEmpty()) {
            model.addAttribute("error", "Expression cannot be empty!");
            return "index";
        }

        expression = expression.replaceAll("\\s+", "");

        List<Character> vars = ExpressionParser.getVariables(expression);
        List<boolean[]> table = TruthTableGenerator.generate(vars.size());

        List<String> results = new ArrayList<>();

        // 🔥 Postfix conversion
        List<Character> postfix = ExpressionParser.toPostfix(expression);

        for (boolean[] row : table) {

            Map<Character, Boolean> values = new HashMap<>();

            for (int i = 0; i < vars.size(); i++) {
                values.put(vars.get(i), row[i]);
            }

            boolean result = ExpressionParser.evaluatePostfix(postfix, values);
            results.add(result ? "T" : "F");
        }

        // 🔥 Send data to UI
        model.addAttribute("vars", vars);
        model.addAttribute("table", table);
        model.addAttribute("results", results);
        model.addAttribute("postfix", postfix);
        model.addAttribute("expression", expression);

        return "result";
    }
}