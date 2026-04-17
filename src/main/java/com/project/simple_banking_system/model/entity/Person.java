package com.project.simple_banking_system.model.entity;

import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.model.valueObjects.DateBirth;
import com.project.simple_banking_system.model.valueObjects.Gender;
import com.project.simple_banking_system.model.valueObjects.Name;
import com.project.simple_banking_system.model.valueObjects.Phone;

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

    @AttributeOverride(name = "value", column = @Column(name = "nome", nullable = false, length = 120))
    @Embedded
    private Name name;


    @AttributeOverride(name = "value", column = @Column(name = "cpf", nullable = false, unique=true))
    @Embedded
    private Cpf cpf;


    @Column(name = "genero", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Gender gender;


    @AttributeOverride(name = "value", column = @Column(name = "telefone", nullable = false, length = 30))
    @Embedded
    private Phone phone;

    
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
