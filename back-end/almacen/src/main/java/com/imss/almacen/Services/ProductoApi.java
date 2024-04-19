package com.imss.almacen.Services;

import com.imss.almacen.Exception.ForbiddenExcpection;
import com.imss.almacen.Exception.ResourceNotFoundException;
import com.imss.almacen.Models.Lender;
import com.imss.almacen.Models.Product;
import com.imss.almacen.Models.User;
import com.imss.almacen.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RequestMapping("/product")
@RestController
public class ProductoApi {
    @Autowired
    private ProductRepository productRepository;
    @PostMapping("/addProduct")
    public ResponseEntity<String> addLender(@RequestBody Product product){
        //vamos a hacer la validacion por name y por softDelete
        List<Product> lista = productRepository.findByNameAndSoftDelete(product.getName(),false);

        if(lista.size()!=0){
            throw new ForbiddenExcpection("The Prodcut name is already register in this database");
        }
        //generate the id for the lender
        product.generateId();
        productRepository.save(product);//save the class
        return new ResponseEntity<>("Product register success ", HttpStatus.OK);
    }
    @GetMapping("/getProduct")
    public List<Product> getLender(){
        return productRepository.findBySoftDeleteIsFalse();
    }
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteLender(@RequestParam String id){
        //check if the user exist
        Optional<Product> optionalProduct =  productRepository.findById(id);
        Product prodcut = optionalProduct.orElseThrow(() -> new ResourceNotFoundException("Product not register in the database"));//throw exception

        if(prodcut.getSoftDelete()){
            return new ResponseEntity<>("The Prodcut is already disable", HttpStatus.OK);
        }
        prodcut.setSoftDelete(true);
        productRepository.save(prodcut);
        return new ResponseEntity<>("Product now disable from the database", HttpStatus.OK);
    }
    private void checkSoftDelete(Product u){
        if(u.getSoftDelete()){
            throw new ForbiddenExcpection("The Product is disable in the database");
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editProdcut(@RequestBody HashMap<String, Object> requestBody){
        //this return the id save in the hashMap
        Optional<Product> optionalProduct =  productRepository.findById((String) requestBody.get("id"));
        requestBody.remove("id");//remove the parameter from the requestbody
        Product product = optionalProduct.orElseThrow(() -> new ResourceNotFoundException("The product id is not admitted"));//throw exception
        checkSoftDelete(product);//chec for valid user

        for(Map.Entry<String,Object> map: requestBody.entrySet()){//check all the map to make updates
            String key= map.getKey();
            Object val = map.getValue();
            if (key.isEmpty())
                throw new ForbiddenExcpection("Field name can't be empty");
            if (key == null)
                throw new ForbiddenExcpection("Field value can't be null");
            if (key.equals("id"))
                throw new ForbiddenExcpection("Id can't be changed");
            try {
                Field field = User.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(product, val);

            } catch (NoSuchFieldException | IllegalAccessException error) {
                throw new ResourceNotFoundException("Error setting field " + key + ": " + error);
            }
        }
        productRepository.save(product);
        return new ResponseEntity<>("Product edit successful",HttpStatus.OK);
    }
}
