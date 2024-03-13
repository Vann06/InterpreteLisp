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
    private double operand1;
    private double operand2;

    /**
     * Constructor de la clase LispExpression.
     * @param operator El operador de la expresión.
     * @param operand1 El primer operando de la expresión.
     * @param operand2 El segundo operando de la expresión.
     */
    public LispExpression(String operator, double operand1, double operand2) {
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    /**
     * Evalúa la expresión y devuelve el resultado.
     * @param env El entorno en el que se evalúa la expresión (actualmente no se utiliza).
     * @return El resultado de la evaluación de la expresión.
     * @throws ArithmeticException Si se intenta dividir por cero.
     * @throws IllegalArgumentException Si el operador no es reconocido.
     */
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
