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
import java.util.ArrayList;
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

    public void interpret(String input) {
        if (input.startsWith("(")) {
            input = input.substring(1);
            input = input.replaceAll("\\s+", " ").trim();

            String[] parts = input.split("\\s+");

            String comando = parts[0];

            switch (comando.toLowerCase()) {
                case "defun":
                    handleDefun(input);
                    break;
                case "setq":
                    handleSetq(input);
                    break;
                case "atom": 
                handleAtom(input);
                break;
                case "+":
                case "-":
                case "*":
                case "/":
                    handleAritmetica(input);
                    break;
                case "<":
                case ">":
                case "=":
                    handlePredicado(input);
                    break;
                default:
                    System.out.println("Comando no reconocido: " + comando);
                    break;
            }
        }

    }


    /**
     * Reemplaza las variables presentes en la entrada por sus valores correspondientes
     * definidos en el entorno y reconstruye la expresión con los valores reemplazados.
     *
     * @param input La entrada que puede contener variables.
     * @return Una cadena con las variables reemplazadas por sus valores.
     */
    public String handleVariable(String input) {
        String[] parts = input.replaceAll("[()]", "").trim().split("\\s+");
        for (int i = 1; i < parts.length; i++) {
            String palabra = parts[i];
            //verifica si existe en el environment
            if (environment.containsVariable(palabra)) {
                String value = environment.getVariableValue(palabra);
                parts[i] = value;
                System.out.println("Se reemplazó '" + palabra + "' por '" + value + "' en la expresión.");
            }
        }
        // Reconstruir el input con los valores reemplazados
        return String.join(" ", parts);
    }
    /**
     * Método para manejar expresiones aritméticas.
     * @param input La expresión aritmética a manejar.
     */
    public void handleAritmetica(String input) {
        input = handleVariable(input);
        try {
            LispExpression expression = parseAritmetica(input);
            if (expression != null) {
                double result = expression.evaluate(null); // Aquí pasamos 'null' porque aún no estamos usando el 'Environment'.
                System.out.println("Resultado de la expresión aritmética: " + result);
            } else {
                System.out.println("Error al parsear la expresión.");
            }
        } catch (ArithmeticException e) {
            System.out.println("Error aritmético: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error en la expresión: " + e.getMessage());
        }
    }

    private LispExpression parseAritmetica(String input) {
        input = handleVariable(input);
        input = input.trim();
        // Asume que el primer caracter es '(' y el último es ')', y los elimina.
        if (input.startsWith("(")) {
            input = input.substring(1, input.length() - 1).trim();
        }
        // Encuentra el operador, que es la primera palabra en la entrada.
        int firstSpaceIndex = input.indexOf(' ');
        String operator = input.substring(0, firstSpaceIndex);
        // El resto de la cadena contiene los operandos.
        String operandsStr = input.substring(firstSpaceIndex).trim();

        List<Object> operands = new ArrayList<>();
        while (!operandsStr.isEmpty()) {
            if (operandsStr.startsWith("(")) {
                int endIndex = findClosingParenthesis(operandsStr);
                String subExpr = operandsStr.substring(0, endIndex + 1);
                operands.add(parseAritmetica(subExpr));  // Recursivamente parsea la subexpresión.
                operandsStr = operandsStr.substring(endIndex + 1).trim();
            } else {
                // Encuentra el próximo espacio para separar el número actual del resto.
                int nextSpaceIndex = operandsStr.indexOf(' ');
                if (nextSpaceIndex == -1) {  // Es el último número.
                    operands.add(Double.parseDouble(operandsStr));
                    break;
                } else {
                    String numberStr = operandsStr.substring(0, nextSpaceIndex);
                    operands.add(Double.parseDouble(numberStr));
                    operandsStr = operandsStr.substring(nextSpaceIndex).trim();
                }
            }
        }

        return new LispExpression(operator, operands.toArray());
    }


    private Object[] extractOperands(String str) {
        List<Object> operands = new ArrayList<>();
        while (!str.isEmpty()) {
            if (str.startsWith("(")) {
                int endIndex = findClosingParenthesis(str);
                String subExpr = str.substring(0, endIndex + 1);
                operands.add(parseAritmetica(subExpr));  // Recursivamente parsea la subexpresión.
                str = str.substring(endIndex + 1).trim();
            } else {
                // Encuentra el próximo espacio para separar el número actual del resto.
                int nextSpaceIndex = str.indexOf(' ');
                if (nextSpaceIndex == -1) {  // Es el último número.
                    operands.add(Double.parseDouble(str));
                    break;
                } else {
                    String numberStr = str.substring(0, nextSpaceIndex);
                    operands.add(Double.parseDouble(numberStr));
                    str = str.substring(nextSpaceIndex).trim();
                }
            }
        }
        return operands.toArray(new Object[0]);
    }

    private int findClosingParenthesis(String str) {
        int open = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') open++;
            if (str.charAt(i) == ')') open--;
            if (open == 0) return i;  // Encontramos el paréntesis de cierre correspondiente.
        }
        return -1;  // No debería ocurrir si la entrada está bien formada.
    }


    /**
     * Método para manejar definiciones de funciones.
     * @param input La definición de la función.
     */
    public void handleDefun(String input) {
        input = handleVariable(input);

        String[] parts = input.replaceAll("\\(", "").replaceAll("\\)", "").split("\\s+");
        if (parts[0].equalsIgnoreCase("defun") && parts.length > 3) {
            String functionName = parts[1];
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
        input = handleVariable(input);

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
            if (i + 1 < palabras.length) {
                String variable = palabras[i];
                String valor = palabras[i + 1];
                // Verificar si la variable ya está en el environment con hashmap
                if (environment.containsVariable(variable)) {
                    System.out.println("La variable '" + variable + "' ya está definida");
                } else {
                    // Agregar la variable al HashMap
                    environment.defineVariable(variable, valor);
                    System.out.println("Variable '" + variable + "' definida  con valor '" + valor );
                }
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
public void handleAtom(String input) {
    input = handleVariable(input);
    // Eliminar paréntesis y espacios
    input = input.replaceAll("[()]", "").trim();
    
    // Verificar si 'x' es una función o un átomo
    boolean esAtomo = isAtom(input);
    
    // Imprimir el resultado
    System.out.println("Resultado del predicado ATOM para '" + input + "': " + (esAtomo ? "T" : "NIL"));
}

/**
 * Método para verificar si un objeto es una lista.
 * @param obj El objeto a verificar.
 * @return true si el objeto es una lista, false de lo contrario.
 */
private boolean isList(String obj) {
    // Una lista es cualquier cosa que comience y termine con "(" y ")"
    return obj.startsWith("(") && obj.endsWith(")");
}

/**
 * Método para verificar si un objeto es un átomo.
 * @param obj El objeto a verificar.
 * @return true si el objeto es un átomo, false de lo contrario.
 */
private boolean isAtom(String obj) {
    // Un átomo es cualquier cosa que no sea una lista
    return !isList(obj);
}



}