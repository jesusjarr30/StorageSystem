package com.imss.almacen.Services;

import com.imss.almacen.Exception.ForbiddenExcpection;
import com.imss.almacen.Exception.ResourceNotFoundException;
import com.imss.almacen.Models.Lender;
import com.imss.almacen.Models.Mass;
import com.imss.almacen.Models.User;
import com.imss.almacen.Repository.MassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RequestMapping("/Mass")
@RestController
public class MassApi {
    @Autowired
    private MassRepository massRepository;

    //queda pendinte hacer un filtro de busqeda de masa para las futuras definiciones
    @PostMapping("/addLender")
    public ResponseEntity<String> addLender(@RequestBody Mass mass){
        //vamos a hacer la validacion por name y por softDelete
        //Aqui falta trabajo de definicion para no repetir productos
        //generate the id for the lender
        mass.generateId();
        massRepository.save(mass);//save the class
        return new ResponseEntity<>("Mass register success ", HttpStatus.OK);
    }
    @GetMapping("/getMass")
    public List<Mass> getMass(){
        return massRepository.findBySoftDeleteIsFalse();
    }
    @DeleteMapping("/deleteMass/{id}")
    public ResponseEntity<String> deleteMass(@RequestParam String id){
        //check if the user exist
        Optional<Mass> optionalMass =  massRepository.findById(id);
        Mass mass = optionalMass.orElseThrow(() -> new ResourceNotFoundException("Mass not register in the database"));//throw exception

        if(mass.getSoftDelete()){
            return new ResponseEntity<>("The Mass is already disable", HttpStatus.OK);
        }
        mass.setSoftDelete(true);
        massRepository.save(mass);
        return new ResponseEntity<>("Mass now disable from the database", HttpStatus.OK);
    }
    private void checkSoftDelete(Mass m){
        if(m.getSoftDelete()){
            throw new ForbiddenExcpection("The Mass is disable in the database");
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editUser(@RequestBody HashMap<String, Object> requestBody){
        //this return the id save in the Hasmap
        Optional<Mass> optionalMass =  massRepository.findById((String) requestBody.get("id"));
        requestBody.remove("id");//remove the parameter from the requestbody
        Mass mass = optionalMass.orElseThrow(() -> new ResourceNotFoundException("The user id is not admitted"));//throw exception
        checkSoftDelete(mass);//chec for valid user

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
                field.set(mass, val);

            } catch (NoSuchFieldException | IllegalAccessException error) {
                throw new ResourceNotFoundException("Error setting field " + key + ": " + error);
            }
        }
        massRepository.save(mass);
        return new ResponseEntity<>("Mass edit successful",HttpStatus.OK);
    }
}

