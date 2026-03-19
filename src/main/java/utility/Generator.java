package utility;

import java.util.Random;

public class Generator {

    public static String generateNumericString(int tam) {

        // Cria uma String com tam caracteres numericos

        StringBuilder element = new StringBuilder();
        Random r = new Random();

        for(int i = 0; i < tam; i++) {
            element.append(i);
        }

        return String.valueOf(element);
    }
}
