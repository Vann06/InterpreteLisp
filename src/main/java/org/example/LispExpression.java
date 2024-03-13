package org.example;
/**
 * CC2016 - Algoritmos y Estructuras de Datos
 * Departamento de Ciencias de la Computación
 * Facultad de Ingeniería
 * Sección 31
 *
 * Jorge Luis Felipe Aguilar Portillo - 23195
 * Ricardo Arturo Godinez Sanchéz - 23247
 * Vianka Vanessa Castro Ordoñez - 23201
 *
 * Clase que representa una expresión Lisp.
 */
public class LispExpression {
    private String operator;
    private Object operand1;  // Puede ser Double o LispExpression
    private Object operand2;  // Puede ser Double o LispExpression

    public LispExpression(String operator, Object operand1, Object operand2) {
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public double evaluate(Environment env) {
        double op1 = operand1 instanceof LispExpression ? ((LispExpression) operand1).evaluate(env) : ((Double) operand1);
        double op2 = operand2 instanceof LispExpression ? ((LispExpression) operand2).evaluate(env) : ((Double) operand2);

        switch (operator) {
            case "+":
                return op1 + op2;
            case "-":
                return op1 - op2;
            case "*":
                return op1 * op2;
            case "/":
                if (op2 == 0) throw new ArithmeticException("División por cero.");
                return op1 / op2;
            default:
                throw new IllegalArgumentException("Operador desconocido: " + operator);
        }
    }
}

