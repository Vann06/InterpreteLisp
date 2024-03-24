package org.example;

import java.util.Arrays;

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

        double result = evaluateOperator(operator, operands, env);
        return result;
    }

    private double evaluateOperator(String operator, Object[] operands, Environment env) {
        switch (operator) {
            case "+":
                return sumarOperandos(operands, env);
            case "-":
                return restarOperandos(operands, env);
            case "*":
                return multiplicarOperandos(operands, env);
            case "/":
                return dividirOperandos(operands, env);
            default:
                throw new IllegalArgumentException("Operador desconocido: " + operator);
        }
    }

    private double sumarOperandos(Object[] operands, Environment env) {
        double resultado = 0.0;
        for (Object operand : operands) {
            resultado += obtenerValorOperando(operand, env);
        }
        return resultado;
    }

    private double restarOperandos(Object[] operands, Environment env) {
        double resultado = obtenerValorOperando(operands[0], env);
        for (int i = 1; i < operands.length; i++) {
            resultado -= obtenerValorOperando(operands[i], env);
        }
        return resultado;
    }

    private double multiplicarOperandos(Object[] operands, Environment env) {
        double resultado = 1.0;
        for (Object operand : operands) {
            resultado *= obtenerValorOperando(operand, env);
        }
        return resultado;
    }

    private double dividirOperandos(Object[] operands, Environment env) {
        double resultado = obtenerValorOperando(operands[0], env);
        for (int i = 1; i < operands.length; i++) {
            double divisor = obtenerValorOperando(operands[i], env);
            if (divisor == 0) {
                throw new ArithmeticException("División por cero.");
            }
            resultado /= divisor;
        }
        return resultado;
    }

    private double obtenerValorOperando(Object operand, Environment env) {
        if (operand instanceof Double) {
            return (Double) operand;
        } else if (operand instanceof LispExpression) {
            return ((LispExpression) operand).evaluate(env);
        } else if (operand instanceof String) {
            // Si el operando es una cadena, intenta interpretarla como una expresión aritmética
            Interprete interprete = new Interprete();
            double result = interprete.handleAritmetica((String) operand);
            return result;
        } else {
            throw new IllegalArgumentException("Tipo de operando no válido: " + operand.getClass().getSimpleName());
        }
    }

    /**
     * Método para obtener el cuerpo de una expresión.
     * @param input La expresión de la que se quiere obtener el cuerpo.
     * @return El cuerpo de la expresión, o null si no se encuentra.
     */
    public String getBodyExpression(String input) {
        // Eliminar paréntesis y espacios
        input = input.replaceAll("[()]", "").trim();
        
        // Dividir el input en palabras
        String[] palabras = input.split("\\s+");
        
        // Obtener el cuerpo de la expresión
        String cuerpo = "";
        for (int i = 1; i < palabras.length; i++) {
            cuerpo += palabras[i] + " ";
        }
        
        // Eliminar el espacio final
        cuerpo = cuerpo.trim();
        
        // Devolver el cuerpo de la expresión
        return cuerpo;
    }
    
    public boolean isOperator(String input) {
        // Definir los operadores válidos
        String[] operadores = {"+", "-", "*", "/", "=", "<", ">", "and", "or", "not", "atom", "car", "cdr", "cons", "list", "cond", "lambda", "let", "let*", "quote"};
        
        // Verificar si el input es un operador válido
        for (String operador : operadores) {
            if (input.equals(operador)) {
                return true;
            }
        }
        
        // Si no se encontró ningún operador válido, devolver false
        return false;
    }
}
