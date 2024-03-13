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
 *  * Clase que representa el entorno de variables y funciones para el intérprete Lisp.
 */
import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, LispVariable> variables;
    private Map<String, LispFunction> functions;
    /**
     * Constructor de la clase Environment.
     * Inicializa los mapas de variables y funciones.
     */
    public Environment() {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
    }
    /**
     * Define una variable en el entorno.
     * @param name El nombre de la variable.
     * @param value El valor de la variable.
     */
    public void defineVariable(String name, Object value) {
        variables.put(name, new LispVariable(name, value));
    }

    /**
     * Obtiene una variable del entorno.
     * @param name El nombre de la variable a obtener.
     * @return La variable si existe, o null si no está definida.
     */
    public LispVariable getVariable(String name) {
        return variables.get(name);
    }

    /**
     * Define una función en el entorno.
     * @param name El nombre de la función.
     * @param function La función a definir.
     */
    public void defineFunction(String name, LispFunction function) {
        functions.put(name, function);
    }

    /**
     * Obtiene una función del entorno.
     * @param name El nombre de la función a obtener.
     * @return La función si existe, o null si no está definida.
     */
    public LispFunction getFunction(String name) {
        return functions.get(name);
    }
}
