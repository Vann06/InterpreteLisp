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
import java.util.Collections;
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
        if (input.isEmpty()) {
            System.out.println("No se proporcionó ninguna entrada.");
            return;
        }

        if (!input.startsWith("(") || !input.endsWith(")")) {
            System.out.println("Entrada no válida: " + input);
            return;
        }


        if (input.startsWith("(")) {
            input = input.substring(1, input.length() - 1).trim();
            input = input.replaceAll("\\s+", " ").trim();

            String comando = input.split("\\s+")[0].toLowerCase();

            switch (comando) {
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
                    double result = handleAritmetica(input);
                    System.out.println("Resultado de la expresión aritmética: " + result);
                    break;
                case "<":
                case ">":
                case "=":
                    handlePredicado(input);
                    break;
                default:
                    // Extraer el nombre de la función y los argumentos
                    String[] parts = input.split("\\s+", 2);
                    String functionName = parts[0].trim();
                    String arguments = parts[1].trim();

                    LispFunction function = environment.getFunction(functionName);
                    if (function != null) {
                        String[] functionParts = function.getBody().split("\\s+");
                        String operator = functionParts[1].trim();
                        String operands = functionParts[1].trim();

                        interpret("(" + operator + " " + arguments + ")");
                    } else {
                        System.out.println("Función '" + functionName + "' no definida.");
                    }
            }
        }
    }


    private double evaluateFunction(LispFunction function, String[] arguments) {
        Environment functionEnv = new Environment(environment);

        for (int i = 0; i < function.getParameters().size(); i++) {
            if (i < arguments.length) {
                functionEnv.defineVariable(function.getParameters().get(i), arguments[i]);
            } else {
                System.out.println("Número incorrecto de argumentos para la función '" + function.getName() + "'");
                return Double.NaN;
            }
        }

        Interprete interpreter = new Interprete();
        double result;
        String functionBody = function.getBody();

        if (functionBody.startsWith("(")) {
            LispExpression expression = interpreter.parseAritmetica(functionBody);
            result = expression.evaluate(functionEnv);
        } else {
            String resultStr = interpreter.handleVariable(functionBody);
            result = Double.parseDouble(resultStr);
        }

        return result;
    }
    
    public double testHandleAritmetica(String input) {
        return handleAritmetica(input);
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
    public double handleAritmetica(String input) {
        input = input.replaceAll("[()]", "").trim();
        input = input.replaceAll("\\s+", " ").trim();

        String[] parts = input.split("\\s+");


        // Obtener el operador al entrar
        String operator = parts[0];


        List<Object> operands = new ArrayList<>();


        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            if (part.equals("(")) {
                continue;
            }

            try {
                double operand = Double.parseDouble(part);
                operands.add(operand);
            } catch (NumberFormatException e) {
                // Si la conversión falla, asumir que es una variable y buscar su valor en el entorno
                if (environment.containsVariable(part)) {
                    System.out.println("value" + environment.getVariableValue(part));
                    double value = Double.parseDouble(environment.getVariableValue(part));
                    operands.add(value);
                }
                else {
                    // Si no es un número ni una variable, manejar la expresión anidada
                    StringBuilder remainingExpression = new StringBuilder();
                    remainingExpression.append(part);
                    for (int j = i + 1; j < parts.length; j++) {
                        remainingExpression.append(" ").append(parts[j]);
                    }
                    // Agregar la expresión anidada a la lista de operandos
                    operands.add(remainingExpression.toString());
                    // Llamar nuevamente a handleAritmetica con la expresión anidada
                    handleAritmetica("( " + remainingExpression.toString());
                    break;
                }
            }
        }
        LispExpression expression = new LispExpression(operator, operands.toArray());
        double result = expression.evaluate(environment);
        return result;
    }

    private LispExpression parseAritmetica(String input) {
        input = handleVariable(input);

        int firstSpaceIndex = input.indexOf(' ');
        String operator = input.substring(0, firstSpaceIndex);

        String operandsStr = input.substring(firstSpaceIndex).trim();

        List<Object> operands = new ArrayList<>();
        while (!operandsStr.isEmpty()) {
            if (operandsStr.startsWith("(")) {
                int endIndex = findClosingParenthesis(operandsStr);
                String subExpr = operandsStr.substring(0, endIndex + 1);
                operands.add(parseAritmetica(subExpr));
                operandsStr = operandsStr.substring(endIndex + 1).trim();
            } else {
                int nextSpaceIndex = operandsStr.indexOf(' ');
                if (nextSpaceIndex == -1) {
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
            // Dividir la entrada en partes usando el espacio como separador
            String[] parts = input.split("\\s+");

            // Verificar si la entrada tiene al menos 4 partes
            if (parts.length < 4) {
                System.out.println("Error al definir la función. La definición incompleta.");
                return;
            }

            // El primer elemento es el nombre de la función
            String functionName = parts[1];

            // Extraer los parámetros entre paréntesis
            int openParenIndex = input.indexOf("(");
            int closeParenIndex = input.indexOf(")");
            if (openParenIndex == -1 || closeParenIndex == -1 || openParenIndex >= closeParenIndex) {
                System.out.println("Error al definir la función. Paréntesis de parámetros faltantes o mal posicionados.");
                return;
            }
            String parametersStr = input.substring(openParenIndex + 1, closeParenIndex);
            // Dividir los parámetros usando la coma como separador
            List<String> parameters = Arrays.asList(parametersStr.split(","));

            // El cuerpo de la función es la parte restante después del último paréntesis cerrado
            String body = input.substring(closeParenIndex + 1).trim();

            // Crear la función y agregarla al entorno
            LispFunction function = new LispFunction(functionName, parameters, body);
            environment.defineFunction(functionName, function);
            System.out.println("Función '" + functionName + "' definida correctamente.");
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