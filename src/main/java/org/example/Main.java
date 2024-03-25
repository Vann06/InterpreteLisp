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

import java.util.Scanner;

public class Main {
    /**
     * menu principal  del interprete, muestra las opciones 
     * disponibles y llama a las funciones correspondientes
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Interprete interprete = new Interprete();

        System.out.println("Bienvenido al intérprete de Lisp simplificado.");
        System.out.println("NOTA! Todos los elementos deben estar separados por un espacio!!!" );
        System.out.println("Puede ingresar los siguientes comandos:");
        System.out.println("1. Operaciones aritméticas: (+, -, *, /)");
        System.out.println("2. DEFUN: (defun nombre_funcion (parametros) (cuerpo_funcion) ");
        System.out.println("3. SETQ: (setq nombre_variable valor)");
        System.out.println("4. Predicados: (ATOM, LIST, EQUAL, <, >)");
        System.out.println("5. COND: (cond (predicado1 expresion1) (predicado2 expresion2) ...)");
        System.out.println("6. Salir");

        while (true) {
            System.out.print("\nIngrese su comando Lisp: ");
            String inputLine = scanner.nextLine();

            if (inputLine.equalsIgnoreCase("salir")) {
                System.out.println("Saliendo del intérprete.");
                break;
            }

            interprete.interpret(inputLine);
        }

        scanner.close();
    }
}
