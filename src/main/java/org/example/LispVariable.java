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
 * Clase que representa una variable en el entorno del intérprete Lisp.
 * Esta clase almacena el nombre de la variable y su valor.
 * */
public class LispVariable {
    private String name;
    private Object value;
    /**
     * Constructor de la clase LispVariable.
     * @param name El nombre de la variable.
     * @param value El valor inicial de la variable.
     */
    public LispVariable(String name, Object value) {
        this.name = name;
        this.value = value;
    }
    /**
     * Obtiene el valor actual de la variable.
     * @return El valor de la variable.
     */
    public Object getValue() {
        return value;
    }
    /**
     * Establece el valor de la variable.
     * @param value El nuevo valor de la variable.
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
