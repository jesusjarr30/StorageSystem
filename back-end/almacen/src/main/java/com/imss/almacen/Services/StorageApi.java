package com.imss.almacen.Services;

import com.imss.almacen.Exception.ForbiddenExcpection;
import com.imss.almacen.Exception.ResourceNotFoundException;
import com.imss.almacen.Models.Lender;
import com.imss.almacen.Models.Storage;
import com.imss.almacen.Models.User;
import com.imss.almacen.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/storage")
@RestController
public class StorageApi {
    @Autowired
    private StorageRepository storageRepository;

    @PostMapping("/addStorage")
    public ResponseEntity<String> addLender(@RequestBody Storage storage){
        //vamos a hacer la validacion por name y por softDelete
        List<Lender> lista = storageRepository.findByNameStorageAndSoftDelete(storage.getNameStorage(),false);

        if(lista.size()!=0){
            throw new ForbiddenExcpection("The Storage name is already register in this database");
        }
        //generate the id for the lender
        storage.generateId();
        storageRepository.save(storage);//save the class
        return new ResponseEntity<>("Storage register success ", HttpStatus.OK);
    }
    @GetMapping("/getStorage")
    public List<Lender> getLender(){
        return storageRepository.findBySoftDeleteIsFalse();
    }
    @DeleteMapping("/deleteStorage/{id}")
    public ResponseEntity<String> deleteStorage(@RequestParam String id){
        //check if the user exist
        Optional<Storage> optionalStorage =  storageRepository.findById(id);
        Storage storage = optionalStorage.orElseThrow(() -> new ResourceNotFoundException("Storage not register in the database"));//throw exception

        if(storage.getSoftDelete()){
            return new ResponseEntity<>("The Storage is already disable", HttpStatus.OK);
        }
        storage.setSoftDelete(true);
        storageRepository.save(storage);
        return new ResponseEntity<>("Storage now disable from the database", HttpStatus.OK);
    }
    private void checkSoftDelete(Storage s){
        if(s.getSoftDelete()){
            throw new ForbiddenExcpection("The Storage is disable in the database");
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editUser(@RequestBody HashMap<String, Object> requestBody){
        //this return the id save in the Hasmap
        Optional<Storage> optionalLender =  storageRepository.findById((String) requestBody.get("id"));
        requestBody.remove("id");//remove the parameter from the requestbody
        Storage storage = optionalLender.orElseThrow(() -> new ResourceNotFoundException("The user id is not admitted"));//throw exception
        checkSoftDelete(storage);//chec for valid user

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
                field.set(storage, val);

            } catch (NoSuchFieldException | IllegalAccessException error) {
                throw new ResourceNotFoundException("Error setting field " + key + ": " + error);
            }
        }
        storageRepository.save(storage);
        return new ResponseEntity<>("Lender edit successful",HttpStatus.OK);
    }
}
