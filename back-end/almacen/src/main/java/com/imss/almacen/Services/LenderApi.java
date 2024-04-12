package com.imss.almacen.Services;

import com.imss.almacen.Repository.LenderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LenderApi {
    private LenderRepository lenderRepository;
    @GetMapping("/lenderA")
    public String lenderAdd(){
        return "Lender";
    }
}
