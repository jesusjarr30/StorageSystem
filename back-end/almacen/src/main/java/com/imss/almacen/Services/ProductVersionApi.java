package com.imss.almacen.Services;

import com.imss.almacen.Exception.ForbiddenExcpection;
import com.imss.almacen.Exception.ResourceNotFoundException;
import com.imss.almacen.Models.Lender;
import com.imss.almacen.Models.ProductVersion;
import com.imss.almacen.Models.User;
import com.imss.almacen.Repository.ProductVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/productVersion")
@RestController
public class ProductVersionApi {
    @Autowired
    private ProductVersionRepository productVersionRepository;

    @PostMapping("/addProductVersion")
    public ResponseEntity<String> addLender(@RequestBody ProductVersion productVersion){
        //Se podia hacer la validacion de que el producto tiene tanto cosas o algo asi
        //generate the id for the lender
        productVersion.generateId();
        productVersionRepository.save(productVersion);//save the class
        return new ResponseEntity<>("ProductVersion register success ", HttpStatus.OK);
    }
    @GetMapping("/getProductVersion")
    public List<ProductVersion> getLender(){
        return productVersionRepository.findBySoftDeleteIsFalse();
    }
    @DeleteMapping("/deleteProductVersion/{id}")
    public ResponseEntity<String> deleteLender(@RequestParam String id){
        //check if the user exist
        Optional<ProductVersion> OptionalproductVersion =  productVersionRepository.findById(id);
        ProductVersion productVersion = OptionalproductVersion.orElseThrow(() -> new ResourceNotFoundException("Lneder not register in the database"));//throw exception

        if(productVersion.getSoftDelete()){
            return new ResponseEntity<>("The Product Version is already disable", HttpStatus.OK);
        }
        productVersion.setSoftDelete(true);
        productVersionRepository.save(productVersion);
        return new ResponseEntity<>("Lender now disable from the database", HttpStatus.OK);
    }
    private void checkSoftDelete(ProductVersion u){
        if(u.getSoftDelete()){
            throw new ForbiddenExcpection("The Product version is disable in the database");
        }
    }
    @PutMapping("/edit")
    public ResponseEntity<String> editProductVersion(@RequestBody HashMap<String, Object> requestBody){
        //this return the id save in the Hasmap
        Optional<ProductVersion> optionalproductVersionRepository =  productVersionRepository.findById((String) requestBody.get("id"));
        requestBody.remove("id");//remove the parameter from the requestbody
        ProductVersion productVersion = optionalproductVersionRepository.orElseThrow(() -> new ResourceNotFoundException("The user id is not admitted"));//throw exception
        checkSoftDelete(productVersion);//chec for valid user

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
                field.set(productVersion, val);

            } catch (NoSuchFieldException | IllegalAccessException error) {
                throw new ResourceNotFoundException("Error setting field " + key + ": " + error);
            }
        }
        productVersionRepository.save(productVersion);
        return new ResponseEntity<>("Product version edit successful",HttpStatus.OK);
    }

    public void addmany(){
        //in here need to program a solution to add many products and make a version of it
    }

}
