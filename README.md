# Interpretador de Lisp Simplificado

Este es un interpretador de Lisp simplificado desarrollado en Java. Permite realizar operaciones aritméticas básicas, definir funciones, asignar variables, evaluar predicados y más.

## Características

- **Operaciones Aritméticas:** Suma (+), resta (-), multiplicación (*) y división (/).
- **Definición de Funciones:** Utiliza la forma `(defun nombre_funcion (parametros) cuerpo_funcion)` para definir nuevas funciones.
- **Asignación de Variables:** Utiliza `(setq nombre_variable valor)` para asignar valores a variables.
- **Predicados:** Soporta los predicados `ATOM`, `LIST`, `EQUAL`, `<`, `>`, entre otros.
- **Evaluación Condicional:** Utiliza la forma `(cond (predicado1 expresion1) (predicado2 expresion2) ...)` para realizar evaluaciones condicionales.
- **Interfaz de Línea de Comandos:** Interactúa con el interpretador a través de una interfaz de línea de comandos simple.

## ! **Nota:** Todos los comandos deben estar separados por espacios.

## Requisitos del Sistema

- Java 8 o superior.

## Uso

1. Clona el repositorio o descarga el código fuente.
2. Compila el código usando tu entorno de desarrollo Java preferido o utilizando el siguiente comando:

# Java Collections Framework

En nuestro proyecto de intérprete de Lisp en Java, hemos utilizado las siguientes estructuras de datos del Java Collection Framework:

- ## ArrayList:

Es una implementación de la interfaz List que utiliza un array dinámico para almacenar los elementos. Se utiliza para manejar dinámicamente colecciones de objetos, como expresiones Lisp, argumentos de funciones, o cualquier lista de elementos que necesite cambiar de tamaño durante la ejecución del programa. ArrayList es ideal para casos donde se requiere un acceso rápido a los elementos mediante índices.

- ## HashMap:

Es una implementación de la interfaz Map que almacena elementos en pares de clave-valor. Se emplea para mapear claves a valores de una manera que permite encontrar y acceder rápidamente a valores específicos a través de claves únicas. En el contexto de tu intérprete, HashMap se puede usar para almacenar variables (SETQ) y funciones (DEFUN) definidas por el usuario, donde el nombre de la variable o función actúa como la clave, y el valor o cuerpo de la función como el valor en el mapa.

Razones para su uso:

- ## ArrayList:

Por su eficiencia en operaciones de acceso y modificación de listas de elementos, particularmente útiles para manejar las listas de argumentos y expresiones Lisp.

- ## HashMap:

Empleado para el almacenamiento eficiente y acceso rápido a variables y funciones definidas, aprovechando la búsqueda basada en clave para implementar el entorno de ejecución del intérprete.

## Contribuciones

Este proyecto fue desarrollado como parte de un trabajo para el curso de Algoritmos y Estructura de Datos y esfuerzo conjunto de los siguientes contribuidores:
- Jorge Luis Felipe Aguilar Portillo - 23195
- Ricardo Arturo Godinez Sanchéz - 23247
- Vianka Vanessa Castro Ordoñez - 23201




