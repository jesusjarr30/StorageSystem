package com.imss.almacen.Services;

import com.imss.almacen.Repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {

    @Autowired
    private userRepository UserRepository;

    @GetMapping("/holamundo")
    public String getHolamundo(){
        return "Hola mundo";
    }

}
