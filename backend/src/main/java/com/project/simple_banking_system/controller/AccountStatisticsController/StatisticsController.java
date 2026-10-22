package com.project.simple_banking_system.controller.AccountStatisticsController;

import com.project.simple_banking_system.service.statistics.GenericTransactionsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller relacionado as funcionalidades de busca de dados filtrados e 
 * estatisticas relacionadas totalVolume um conta especifica.
 */
@RestController
@RequestMapping("/api/account/statistics") 
public class StatisticsController {

    @Autowired
    private GenericTransactionsData genericTransactionsData;

    /**
     * Recupera Estatísticas genéricas de uma conta bancaria
     */
    @GetMapping("/generic")
    public ResponseEntity<?> getGenericAccountStatistics() {
        var result = genericTransactionsData.get();
        return ResponseEntity.ok(result);
    }
    

    
}
