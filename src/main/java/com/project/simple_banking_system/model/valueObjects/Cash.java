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
        this(new BigDecimal(0));
   }

   public BigDecimal getValue() {
        return value;
   }

   public void setValue(BigDecimal value) {
        this.value = value;
   }

}
