package com.imss.almacen.Services;

import com.imss.almacen.Exception.ForbiddenExcpection;
import com.imss.almacen.Exception.ResourceNotFoundException;
import com.imss.almacen.Models.Lender;
import com.imss.almacen.Models.User;
import com.imss.almacen.Repository.LenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/lender")
@RestController
public class LenderApi {
    @Autowired
    private LenderRepository lenderRepository;
    @PostMapping("/addLender")
    public ResponseEntity<String> addLender(@RequestBody Lender lender){
        //vamos a hacer la validacion por name y por softDelete
        List<Lender> lista = lenderRepository.findByNameAndSoftDelete(lender.getName(),false);

        if(lista.size()!=0){
            throw new ForbiddenExcpection("The lender name is already register in this database");
        }
        //generate the id for the lender
        lender.generateId();
        lenderRepository.save(lender);//save the class
        return new ResponseEntity<>("Lender register success ", HttpStatus.OK);

    }
    @GetMapping("/getLender")
    public List<Lender> getLender(){
        return lenderRepository.findBySoftDeleteIsFalse();
    }
    @DeleteMapping("/deleteLender/{id}")
    public ResponseEntity<String> deleteLender(@RequestParam String id){
        //check if the user exist
        Optional<Lender> optionalLender =  lenderRepository.findById(id);
        Lender lender = optionalLender.orElseThrow(() -> new ResourceNotFoundException("Lneder not register in the database"));//throw exception

        if(lender.getSoftDelete()){
            return new ResponseEntity<>("The Lender is already disable", HttpStatus.OK);
        }
        lender.setSoftDelete(true);
        lenderRepository.save(lender);
        return new ResponseEntity<>("Lender now disable from the database", HttpStatus.OK);
    }
    private void checkSoftDelete(Lender u){
        if(u.getSoftDelete()){
            throw new ForbiddenExcpection("The lender is disable in the database");
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editUser(@RequestBody HashMap<String, Object> requestBody){
        //this return the id save in the Hasmap
        Optional<Lender> optionalLender =  lenderRepository.findById((String) requestBody.get("id"));
        requestBody.remove("id");//remove the parameter from the requestbody
        Lender lender = optionalLender.orElseThrow(() -> new ResourceNotFoundException("The user id is not admitted"));//throw exception
        checkSoftDelete(lender);//chec for valid user

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
                field.set(lender, val);

            } catch (NoSuchFieldException | IllegalAccessException error) {
                throw new ResourceNotFoundException("Error setting field " + key + ": " + error);
            }
        }
        lenderRepository.save(lender);
        return new ResponseEntity<>("Lender edit successful",HttpStatus.OK);
    }
}
