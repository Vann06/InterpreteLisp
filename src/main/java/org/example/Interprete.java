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
 * Clase que representa un intérprete simple para un lenguaje Lisp.
 */
import java.util.Stack;
import java.util.List;
import java.util.Arrays;
public class Interprete {

    private Environment environment;

    /**
     * Constructor de la clase Interprete.
     * Inicializa el entorno del intérprete.
     */
    public Interprete() {
        this.environment = new Environment();
    }

    /**
     * Método para interpretar una entrada.
     * Este método actualmente solo imprime la entrada recibida.
     * @param input La entrada a interpretar.
     */
    public void interpret(String input) {
        // Aquí irá la lógica para parsear y evaluar la entrada.
        System.out.println("Usted ingresó: " + input);
        // (Aquí iría el proceso de análisis y evaluación que implementaremos más adelante)
    }

    /**
     * Método para manejar expresiones aritméticas.
     * @param input La expresión aritmética a manejar.
     */
    public void handleAritmetica(String input) {
        LispExpression expression = parseAritmetica(input);
        Object result = expression.evaluate(null);  // Pasamos null porque este ejemplo no usa el entorno aún.
        System.out.println("Resultado de la expresión aritmética: " + result);
    }

    private LispExpression parseAritmetica(String input) {
        // Elimina los paréntesis y divide el input por espacios
        String[] tokens = input.trim().replaceAll("[()]", "").split("\\s+");
        if (tokens.length != 3) {
            System.out.println("Error: Formato de expresión aritmética incorrecto.");
            return null;  // Deberías manejar este caso de error adecuadamente.
        }

        String operador = tokens[0];
        double op1 = Double.parseDouble(tokens[1]);
        double op2 = Double.parseDouble(tokens[2]);

        return new LispExpression(operador, op1, op2);
    }

    /**
     * Método para manejar definiciones de funciones.
     * @param input La definición de la función.
     */
    public void handleDefun(String input) {
        // Parsear la entrada y extraer el nombre de la función, parámetros y cuerpo.
        // Este es un ejemplo simple y no maneja errores ni casos complejos.
        String[] parts = input.replaceAll("\\(", "").replaceAll("\\)", "").split("\\s+");
        if (parts[0].equalsIgnoreCase("defun") && parts.length > 3) {
            String functionName = parts[1];
            // Aquí, podrías tener un método para analizar la lista de parámetros y el cuerpo de la función
            // Por simplicidad, supongamos que siempre hay un solo parámetro y una sola operación en el cuerpo.
            List<String> parameters = Arrays.asList(parts[2]); // Esto es un supuesto simplificado
            String body = input.substring(input.indexOf(parts[3])); // Esto también es simplificado

            LispFunction function = new LispFunction(functionName, parameters, body);
            environment.defineFunction(functionName, function);
            System.out.println("Función '" + functionName + "' definida correctamente.");
        } else {
            System.out.println("Error al definir la función. Asegúrese de que el formato sea el correcto.");
        }
    }

    /**
     * Método para manejar predicados.
     * @param input La entrada que representa un predicado.
     */
    public void handlePredicado(String input) {
        // Parseamos la entrada y separamos el operador y los operandos.
        // Suponemos que la entrada es de la forma "(operador op1 op2)"
        String[] parts = input.replaceAll("\\(", "").replaceAll("\\)", "").trim().split("\\s+");
        if (parts.length != 3) {
            System.out.println("Formato de predicado incorrecto. Debe ser '(operador op1 op2)'.");
            return;
        }

        String operador = parts[0];
        Double op1 = Double.parseDouble(parts[1]);
        Double op2 = Double.parseDouble(parts[2]);

        boolean resultado = false;
        switch (operador) {
            case "<":
                resultado = op1 < op2;
                break;
            case ">":
                resultado = op1 > op2;
                break;
            case "=":
                resultado = op1.equals(op2);
                break;
            default:
                System.out.println("Operador desconocido: " + operador);
                return;
        }

        System.out.println("Resultado del predicado: " + (resultado ? "T" : "NIL"));
    }

    /**
     * Método para verificar el balance de paréntesis en una cadena de código.
     * @param codigo La cadena de código que se va a verificar.
     * @return true si los paréntesis están balanceados, false en caso contrario.
     */
    public boolean parenthesisBalanced(String codigo) {
        int count = 0;
        for (char c : codigo.toCharArray()) {
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
                if (count < 0) {
                    return false;
                }
            }
        }
        return count == 0;
    }

    /**
     * Método para manejar instrucciones setq.
     * @param input La instrucción setq.
     */
    public void handleSetq(String input) {
        String[] palabras = input.replaceAll("[()]", "").trim().split("\\s+");

        for (int i = 1; i < palabras.length; i += 2) {
            // i = 0 es el setq
            // Asegura que hay un par variable-valor antes de intentar acceder al array
            if (i + 1 < palabras.length) {
                String variable = palabras[i];
                String valor = palabras[i + 1];
                System.out.println(variable + " = " + valor);
            } else {
                System.out.println("Error: número impar de elementos en la instrucción setq.");
                break;
            }
        }
    }

    /**
     * Método para manejar el predicado ATOM.
     * @param input La entrada a evaluar.
     */
    public void handleAtom(String input){
        if (input.startsWith("(") && input.endsWith(")")) {
            System.out.println("Resultado del predicado ATOM: NIL"); 
        } else {
            System.out.println("Resultado del predicado ATOM: T");
        }
    }


}
