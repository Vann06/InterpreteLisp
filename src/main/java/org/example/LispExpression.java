package org.example;

public class LispExpression {
    private String operator;
    private Object operand1;
    private Object operand2;

    public LispExpression(String operator, Object operand1, Object operand2) {
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public LispExpression() {
        // Constructor vacío por defecto
    }

    public Object evaluate(Environment environment) {
        // Necesitas implementar la lógica de evaluación de la expresión aquí.
        // La siguiente es una implementación simplificada que solo evalúa operaciones aritméticas binarias.

        double op1 = Double.valueOf(environment.getVariable((String)operand1).getValue().toString());
        double op2 = Double.valueOf(environment.getVariable((String)operand2).getValue().toString());

        switch (operator) {
            case "+":
                return op1 + op2;
            case "-":
                return op1 - op2;
            case "*":
                return op1 * op2;
            case "/":
                if (op2 == 0) throw new ArithmeticException("División por cero");
                return op1 / op2;
            default:
                throw new IllegalArgumentException("Operador desconocido: " + operator);
        }
    }

    // Este es un método de parseo ficticio para el ejemplo
    public static LispExpression parse(String input) {
        // Suponemos que la entrada es algo como "(+ x y)"
        input = input.trim();
        input = input.substring(1, input.length() - 1);  // Remover paréntesis
        String[] tokens = input.split("\\s+");
        String operator = tokens[0];
        Object operand1 = tokens[1];
        Object operand2 = tokens[2];

        return new LispExpression(operator, operand1, operand2);
    }
}
