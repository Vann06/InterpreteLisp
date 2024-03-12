package org.example;
import java.util.Stack;
import java.util.List;
import java.util.Arrays;

public class Interprete {

    private Environment environment;

    public Interprete() {
        this.environment = new Environment();
    }

    public void interpret(String input) {
        // Aquí irá la lógica para parsear y evaluar la entrada.
        System.out.println("Usted ingresó: " + input);
        // (Aquí iría el proceso de análisis y evaluación que implementaremos más adelante)
    }

    public void handleAritmetica(String input) {
        LispExpression expression = parseAritmetica(input);
        // Supongamos que LispExpression puede evaluar su propio valor
        Object result = expression.evaluate(environment);
        System.out.println("Resultado de la expresión aritmética: " + result);
    }

    private LispExpression parseAritmetica(String input) {
        // Simplificaremos y asumiremos que solo hay operaciones binarias de la forma (+ 1 2)
        String[] tokens = input.replaceAll("\\(", "").replaceAll("\\)", "").split("\\s+");
        String operator = tokens[0];
        Double operand1 = Double.valueOf(tokens[1]);
        Double operand2 = Double.valueOf(tokens[2]);

        return new LispExpression(operator, operand1, operand2);
    }

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




}
