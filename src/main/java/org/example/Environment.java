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
    private Map<String, Object> variables;
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
     * Define una función en el entorno.
     * @param name El nombre de la función.
     * @param function La función a definir.
     */
    public void defineFunction(String name, LispFunction function) {
        functions.put(name, function);
    }
    /**
     * Define una variable en el entorno.
     * @param name El nombre de la variable.
     * @param value El valor de la variable.
     */
    public void defineVariable(String name, Object value) {
        variables.put(name, value);
    }

    /**
     * Obtiene una variable del entorno.
     * @param name El nombre de la variable a obtener.
     * @return La variable si existe, o null si no está definida.

    public LispVariable getVariable(String name) {
        return variables.get(name);
    }
        */


    /**
     * Obtiene una función del entorno.
     * @param name El nombre de la función a obtener.
     * @return La función si existe, o null si no está definida.
     */
    public LispFunction getFunction(String name) {
        return functions.get(name);
    }

    public boolean containsVariable(String name){
        return variables.containsKey(name);
    }

    public String getVariableValue(String name){
        Object value = variables.get(name);
        if(value instanceof Double){
            return Double.toString((Double) value);
        }else{
            return value.toString();
        }
    }

    public boolean containsFunction(String name) {
        return functions.containsKey(name);
    }

    public Environment(Environment parent) {
        this.variables = new HashMap<>(parent.variables);
        this.functions = new HashMap<>(parent.functions);
    }
    
    public void setVariableValue(String variableName, Object value) {

        variables.put(variableName, value);

    }

    

}
