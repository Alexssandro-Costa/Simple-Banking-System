package com.project.simple_banking_system.model.valueObjects;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;



/**
 * Classe que armazena o valor em dinheiro.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Embeddable
public class Cash {

   private BigDecimal value;

   public Cash(BigDecimal value) {
        this.value = value;
   }

   public Cash() {
   }

   public BigDecimal getValue() {
        return value;
   }

   public void setValue(BigDecimal value) {
        this.value = value;
   }

   public void add(Cash cash) {
       value = value.add(cash.getValue());
   }

   public void subtract(Cash cash) {
       value = value.subtract(cash.getValue());
   }

    @Override
    public String toString() {
        return value.toEngineeringString();
    }




}
