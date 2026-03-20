package utility;

import java.util.Random;

public class Generator {

    public static String generateNumericString(int tam) {

        /// Cria uma String  de tamanho(tam) com caracteres numericos

        StringBuilder element = new StringBuilder();
        Random r = new Random();

        for(int i = 0; i < tam; i++) {
            element.append(r.nextInt(0, 10));
        }

        return String.valueOf(element);
    }
}
