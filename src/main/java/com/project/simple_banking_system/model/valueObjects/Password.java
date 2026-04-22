package com.project.simple_banking_system.model.valueObjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.simple_banking_system.exceptions.NullElementException;

import jakarta.persistence.Embeddable;

/**
 * Classe que armazena a senha de uma conta bancaria.
 * @author Alexssandro
 * @since release 1
 * @version 2
 */
@Embeddable
public class Password {

    private String value;

    /** Formato padrão de uma senha */
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");

    public Password(String value) {
        this.value = value;
    }

    public Password() {
        this(null);
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Criptografa o conteúdo de uma senha.
     * @param rawPassword Senha não criptografada.
     * @exception NullElementException Lançada caso o elemento passado for nulo.
     * @return String criptografa.
     */
    public static void encrypt(Password rawPassword) {

        /* 
        if(rawPassword == null || rawPassword.getValue() == null)
            throw new NullElementException("Elemento nulo informado"); 

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        return bcrypt.encode(rawPassword.getValue());
    */
   }


    /**
     * Compara uma senha criptografada com uma senha pura. 
     * @param rawPassword Senha não criptografada.
     * @param encodedPassword Senha criptografada.
     * @exception NullElementException Lançada caso um elemento passado for nulo.
     * @return boolean - Verdadeiro se os elementos forem iguais, falso caso contrario.
     */
    public static void compare(Password rawPassword, Password encodedPassword) {

        /* 
        if( ( rawPassword == null || rawPassword.getValue() == null ) || 
        (encodedPassword == null || encodedPassword.getValue() == null) )
            throw new NullElementException("Elemento nulo informado");

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        return bcrypt.matches(rawPassword.getValue(), encodedPassword.getValue());

        */

    }


    
    /**
     * Verifica se o valor do objeto está no formato padrão.
     * @return verdadeiro se estiver, falso caso contrario.
     */
    public boolean isStandardized() {

        Matcher matcher = PASSWORD_PATTERN.matcher(value);
        return matcher.matches();
    }

    
    
}
