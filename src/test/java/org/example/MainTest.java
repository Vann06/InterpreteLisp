package org.example;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MainTest {

    @Test
    public void testHandleAritmetica() {
        Interprete interprete = new Interprete();

        // Test the example you provided
        double result = interprete.testHandleAritmetica("(+ 2 (* 3 4))");
        assertEquals(14, result, 0);

        // Add more test cases to cover different scenarios
    }
}
