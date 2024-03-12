package org.example;

public class LispVariable {
    private String name;
    private Object value;

    public LispVariable(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
