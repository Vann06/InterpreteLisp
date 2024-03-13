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
 * Clase Main
 * */
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
                    try {
                        System.out.println("Ingrese la expresión aritmética en formato Lisp (ejemplo: (+ 3 4)):");
                        String aritmeticaInput = scanner.nextLine();
                        if (!(aritmeticaInput.startsWith("(+") || aritmeticaInput.startsWith("(-") ||
                                aritmeticaInput.startsWith("(/") || aritmeticaInput.startsWith("(*")) && !interprete.parenthesisBalanced(aritmeticaInput)) {
                            System.out.println("El código LISP proporcionado no es compatible con aritmética");
                        } else {
                            interprete.handleAritmetica(aritmeticaInput);
                        }
                    } catch (Exception e) {
                        System.out.println("Ingrese un dato válido por favor");
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Ingrese la definición de la función en formato Lisp (ejemplo: (defun suma (a b) (+ a b))");
                        String defunInput = scanner.nextLine();
                        if (!defunInput.startsWith("(defun") && !interprete.parenthesisBalanced(defunInput)) {
                            System.out.println("El codigo LISP proporcionado no es compatible");
                        }
                        interprete.handleDefun(defunInput);
                    } catch (Exception e) {
                        System.out.println("Ingrese un dato válido por favor");
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Ingrese el predicado en formato Lisp (ejemplo: (> 3 2)):");
                        String predicadoInput = scanner.nextLine();
                        if (!interprete.parenthesisBalanced(predicadoInput)) {
                            System.out.println("El codigo LISP proporcionado no es compatible");
                        }
                        interprete.handlePredicado(predicadoInput);
                    } catch (Exception e) {
                        System.out.println("Ingrese un dato válido por favor");
                    }
                    break;

                case 4:
                    System.out.println("Ingrese el átomo en formato Lisp:");
                    String atomInput = scanner.nextLine();
                    interprete.handleAtom(atomInput);
                    break;
                case 5:
                    try {
                        System.out.println("Ingrese el codigo de Lisp (ejemplo: (setq x 2))");
                        String setQInput = scanner.nextLine();
                        if (!setQInput.startsWith("(setq") && !interprete.parenthesisBalanced(setQInput)) {
                            System.out.println("El codigo LISP proporcionado no es compatible con setq");
                        } else {
                            interprete.handleSetq(setQInput);
                        }
                    } catch (Exception e) {
                        System.out.println("Ingrese un dato válido por favor");
                    }
                    break;
                case 6:
                    System.out.println("Saliendo del intérprete.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    break;
            }
        }
    }
}
