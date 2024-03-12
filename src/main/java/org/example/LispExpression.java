package org.example;

public class LispExpression {
    private String operator;
    private double operand1;
    private double operand2;

    public LispExpression(String operator, double operand1, double operand2) {
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public double evaluate(Environment env) {  // Aunque env no se usa aquí, podría ser útil para futuras extensiones.
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("División por cero.");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Operador desconocido: " + operator);
        }
    }
}
