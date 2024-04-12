package com.imss.almacen.Services;

import com.imss.almacen.Repository.ProductVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductVersionApi {
    @Autowired
    private ProductVersionRepository productVersionRepository;


    @GetMapping("/holaMas22")
    public String getMArr(){
        return "Hola esto es la massa";
    }
}
