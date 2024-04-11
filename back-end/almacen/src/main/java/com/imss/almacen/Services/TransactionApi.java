package com.imss.almacen.Services;

import com.imss.almacen.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionApi {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/lgf0")
    public String ShowHola(){
        return "Hola mundo";
    }
}
