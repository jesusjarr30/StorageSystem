package com.imss.almacen.Services;

import com.imss.almacen.Exception.ResourceNotFoundException;
import com.imss.almacen.Models.Supplier;
import com.imss.almacen.Models.User;
import com.imss.almacen.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequestMapping("/supplier")
@RestController
public class SupplierApi {
    @Autowired
    private SupplierRepository supplierRepository;
    @GetMapping("/hfsd0")
    public String hola(){
        return "Hola de supplier";
    }
    @PostMapping("/add")
    public ResponseEntity<String> addSupplier(@RequestBody Supplier supplier){
        //algunas ideas para hacer modificaciones es posib
        //aqui se debe de hacer el la verificacion para que no tenga algun sistema repetido
        supplier.generateId();//generate the id for the supplier
        supplier.setState(true);
        supplier.setSoftDelete(false);
        supplierRepository.save(supplier);

        return new ResponseEntity<>("The supplier register is ok", HttpStatus.OK);
    }
    @GetMapping("/getSupplier")
    public List<Supplier> getSupplier(){
        //return all the supplier active in the database
        return supplierRepository.findBySoftDeleteIsFalse();
    }
    @PutMapping("/editSupplier")
    public ResponseEntity<String> editSupplier(@RequestBody HashMap<String, Object> requestbody){
        return null;
    }
    @DeleteMapping("/deleteSupplier/{id}")
    public ResponseEntity<String> deleteSupplier(@RequestParam String id){
        //find the supplier by ID
        Optional<Supplier> optionalSupplier =  supplierRepository.findById(id);
        Supplier supplier = optionalSupplier.orElseThrow(() -> new ResourceNotFoundException("User not register in the database"));//throw exception

        supplier.setSoftDelete(true);
        supplierRepository.save(supplier);

        return new ResponseEntity<>("The supplier is disable now ",HttpStatus.OK);
    }
}
