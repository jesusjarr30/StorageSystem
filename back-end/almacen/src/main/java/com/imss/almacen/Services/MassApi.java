package com.imss.almacen.Services;

import com.imss.almacen.Repository.MassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MassApi {
    @Autowired
    private MassRepository massRepository;

    @GetMapping("/holaMass")
    public String getMArr(){
        return "Hola esto es la massa";
    }
}
