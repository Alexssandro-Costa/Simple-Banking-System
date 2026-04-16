package com.project.simple_banking_system.utility;

import java.util.Random;

/**
 * Gerador de números, strings(numéricas, alfanuméricas, ou alfabéticas) aleatório.
 * @author ALexssandro
 * @since release 2
 * @version 1.0
 */
public class Generator {

    /**
     * Cria uma string com caracteres numéricos.
     * @param tam tamanho da string.
     * @return String com 'tam' caracteres numéricos.
     */
    public static String generateNumericString(int tam) {

        StringBuilder element = new StringBuilder();
        Random r = new Random();

        for(int i = 0; i < tam; i++) {
            element.append(r.nextInt(0, 10));
        }

        return String.valueOf(element);
    }
}