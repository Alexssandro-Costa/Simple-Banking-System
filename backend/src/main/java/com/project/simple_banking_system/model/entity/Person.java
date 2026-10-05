package com.project.simple_banking_system.model.entity;

import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.model.valueObjects.DateBirth;
import com.project.simple_banking_system.model.valueObjects.Gender;
import com.project.simple_banking_system.model.valueObjects.Name;
import com.project.simple_banking_system.model.valueObjects.Phone;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;


/**
 * Classe abstrata que armazena as informações de um individuo.
 * @author Alexssandro
 * @since release 1
 * @version 2
 */
@MappedSuperclass
public abstract class Person {

    @Schema(description = "Nome de um individuo", example = "ALEX DA COSTA")
    @AttributeOverride(name = "value", column = @Column(name = "nome", nullable = false, length = 120))
    @Embedded
    private Name name;

    @Schema(description = "cpf de um individuo", example = "111.222.333-44")
    @AttributeOverride(name = "value", column = @Column(name = "cpf", nullable = false, unique=true))
    @Embedded
    private Cpf cpf;

    @Schema(description = "Género do individuo", examples = {"MASCULINO", "FEMININO", "OUTRO"})
    @Column(name = "genero", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Schema(description = "Telefone do individuo", example = "22992345678")
    @AttributeOverride(name = "value", column = @Column(name = "telefone", nullable = false, length = 30))
    @Embedded
    private Phone phone;

    @Schema(description = "Data de nascimento do individuo, segue o padrão YYYY/MM/DD", example = "2000-01-01")
    @AttributeOverride(name = "value", column = @Column(name = "data-nascimento", nullable = false, length = 15))
    @Embedded
    private DateBirth dateBirth;

    protected Person(Name name, Cpf cpf, Gender gender, Phone phone, DateBirth dateBirth) {
        this.name = name;
        this.cpf = cpf;
        this.gender = gender;
        this.phone = phone;
        this.dateBirth = dateBirth;
    }
    protected Person() {
    }

    public Name getName() {
        return name;
    }
    public void setName(Name name) {
        this.name = name;
    }
    public Cpf getCpf() {
        return cpf;
    }
    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Phone getPhone() {
        return phone;
    }
    public void setPhone(Phone phone) {
        this.phone = phone;
    }
    public DateBirth getDateBirth() {
        return dateBirth;
    }
    public void setDateBirth(DateBirth dateBirth) {
        this.dateBirth = dateBirth;
    }

    
}
