package com.project.simple_banking_system.model.valueObjects;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;
import org.jspecify.annotations.NonNull;


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

   public Cash(Double value) { this.value = BigDecimal.valueOf(value); }

    public Cash(double value) { this.value = BigDecimal.valueOf(value); }

    public Cash(int value) { this.value = BigDecimal.valueOf(value); }

    public Cash(Integer value) { this.value = BigDecimal.valueOf(value);  }

    public Cash() { }


   public BigDecimal getValue() {
        return value;
   }

   public void setValue(BigDecimal value) {
        this.value = value;
   }

   public void add(@NonNull Cash cash) {
       value = value.add(cash.getValue());
   }

   public void subtract(@NonNull Cash cash) {
       value = value.subtract(cash.getValue());
   }

   @Override
   public String toString() {
        return value.toEngineeringString();
    }

}
