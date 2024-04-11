package com.imss.almacen.Services;

import com.imss.almacen.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierApi {
    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping("/hfsd0")
    public String hola(){
        return "Hola de supplier";
    }
}
