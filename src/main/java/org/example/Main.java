package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Interprete interprete = new Interprete();

        while (true) {
            System.out.println("\nQué opción desea realizar?");
            System.out.println("1. Aritmética");
            System.out.println("2. Defun");
            System.out.println("3. Predicado (<,>,=)");
            System.out.println("4. ATOM");
            System.out.println("5. SETQ");
            System.out.println("6. Salir");
            System.out.print("Ingrese su elección: ");

            String inputLine = scanner.nextLine(); // Lee toda entrada como texto
            int choice;
            try {
                choice = Integer.parseInt(inputLine); // Intenta convertir la entrada de texto a un número
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número de opción.");
                continue; // Continúa el bucle si la entrada no se puede convertir a número
            }

            switch (choice) {
                case 1:
                    System.out.println("Ingrese la expresión aritmética en formato Lisp (ejemplo: (+ 3 4)):");
                    String aritmeticaInput = scanner.nextLine();
                    interprete.handleAritmetica(aritmeticaInput);
                    break;
                case 2:
                    System.out.println("Ingrese la definición de la función en formato Lisp");
                    String defunInput = scanner.nextLine();
                    interprete.handleDefun(defunInput);


                    break;
                case 3:
                    System.out.println("Ingrese el predicado en formato Lisp (ejemplo: (> 3 2)):");
                    String predicadoInput = scanner.nextLine();
                    interprete.handlePredicado(predicadoInput);
                    break;

                case 4:
                    System.out.println("Ingrese el átomo en formato Lisp:");
                    String atomInput = scanner.nextLine();
                    interprete.handleAtom(atomInput);
                    break;
                case 5:
                    // Manejo de SETQ
                    break;
                case 6:
                    System.out.println("Saliendo del intérprete.");
                    scanner.close(); // Es importante cerrar el Scanner antes de salir
                    return; // Sale del programa
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    break;
            }
        }
    }
}
