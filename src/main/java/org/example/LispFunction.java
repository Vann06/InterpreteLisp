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
 * Clase que representa una función en el intérprete Lisp.
 * Esta clase almacena el nombre de la función, sus parámetros y su cuerpo.
 * */
import java.util.List;


public class LispFunction {
    private String name;
    private List<String> parameters;
    private String body; // Esto puede ser una cadena o alguna estructura más compleja
    /**
     * Constructor de la clase LispFunction.
     * @param name El nombre de la función.
     * @param parameters La lista de parámetros de la función.
     * @param body El cuerpo de la función.
     */
    public LispFunction(String name, List<String> parameters, String body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    public List<String> getParameters() {
        return parameters;
    }
    
    public String getName() {
        return name;
    }
    
    public String getBody() {
        return body;
    }

}
