package com.imss.almacen.Services;

import com.imss.almacen.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageApi {
    @Autowired
    private StorageRepository storageRepository;

    @GetMapping("/fjhdsgfia0")
    public String getHola(){
        return "Hola ya esta aqui";
    }
}
