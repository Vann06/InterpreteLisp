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
    private Object[] operands;

    public LispExpression(String operator, Object[] operands) {
        this.operator = operator;
        this.operands = operands;
    }

    public double evaluate(Environment env) {
        if (operands.length < 2) {
            throw new IllegalArgumentException("La expresión debe tener al menos dos operandos.");
        }

        double result = evaluateOperator((String) operator, operands, env);
        return result;
    }

    private double evaluateOperator(String operator, Object[] operands, Environment env) {
        double result = 0.0;
        double op1 = getOperandValue(operands[0], env);
        double op2 = getOperandValue(operands[1], env);

        switch (operator) {
            case "+":
                result = op1 + op2;
                break;
            case "-":
                result = op1 - op2;
                break;
            case "*":
                result = op1 * op2;
                break;
            case "/":
                if (op2 == 0) {
                    throw new ArithmeticException("División por cero.");
                }
                result = op1 / op2;
                break;
            default:
                throw new IllegalArgumentException("Operador desconocido: " + operator);
        }

        return result;
    }

    private double getOperandValue(Object operand, Environment env) {
        if (operand instanceof Double) {
            return (Double) operand;
        } else if (operand instanceof LispExpression) {
            return ((LispExpression) operand).evaluate(env);
        } else {
            throw new IllegalArgumentException("Tipo de operando no válido: " + operand.getClass().getSimpleName());
        }
    }
}


