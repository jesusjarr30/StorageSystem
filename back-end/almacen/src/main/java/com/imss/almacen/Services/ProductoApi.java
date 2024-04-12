package com.imss.almacen.Services;

import com.imss.almacen.Repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoApi {
    private ProductRepository productRepository;

    @GetMapping("/holaMas2")
    public String getMArr(){
        return "Hola esto es la massa";
    }
}
