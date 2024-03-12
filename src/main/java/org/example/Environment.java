package org.example;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, LispVariable> variables;
    private Map<String, LispFunction> functions;

    public Environment() {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
    }

    public void defineVariable(String name, Object value) {
        variables.put(name, new LispVariable(name, value));
    }

    public LispVariable getVariable(String name) {
        return variables.get(name);
    }

    public void defineFunction(String name, LispFunction function) {
        functions.put(name, function);
    }

    public LispFunction getFunction(String name) {
        return functions.get(name);
    }
}
