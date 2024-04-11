package com.imss.almacen.Services;

import com.imss.almacen.Repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionDetailsApi {
    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @GetMapping("/fsdfh")
    public String HolaMUndo(){
        return "Hola mundo";
    }

}
