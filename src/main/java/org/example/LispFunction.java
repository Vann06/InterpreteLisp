package org.example;

import java.util.List;


public class LispFunction {
    private String name;
    private List<String> parameters;
    private String body; // Esto puede ser una cadena o alguna estructura más compleja

    public LispFunction(String name, List<String> parameters, String body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    // Aquí añadirías métodos para evaluar la función, etc.
}
