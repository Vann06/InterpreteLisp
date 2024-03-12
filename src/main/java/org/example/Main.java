package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Interprete interprete = new Interprete();

        boolean running = true;
        while (running) {
            System.out.println("\nQué opción desea realizar?");
            System.out.println("1. Aritmética");
            System.out.println("2. Defun");
            System.out.println("3. Predicado (<,>,=)");
            System.out.println("4. ATOM");
            System.out.println("5. SETQ");
            System.out.println("6. Salir");

            System.out.print("Ingrese su elección: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over

            switch (choice) {
                case 1:
                    System.out.println("Ingrese la expresión aritmética en formato Lisp:");
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
                    // Manejo de ATOM
                    break;
                case 5:
                    // Manejo de SETQ
                    break;
                case 6:
                    System.out.println("Saliendo del intérprete.");
                    running = false;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    break;
            }
        }

        scanner.close();
    }
}
