package com.imss.almacen.Services;

import com.imss.almacen.Exception.ForbiddenExcpection;
import com.imss.almacen.Exception.ResourceNotFoundException;
import com.imss.almacen.Models.User;
import com.imss.almacen.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<String> getHolamundo(@RequestBody User u){
        User verificacion = null;
        verificacion= userRepository.findByEmailAndSoftDeleteIsFalse(u.getEmail());
        if(verificacion !=null){
            return new ResponseEntity<>("EL correo ya esta registrado con otra cuenta", HttpStatus.CONFLICT);
        }
        u.encrypt();//encryp the password}
        userRepository.save(u);
        return new ResponseEntity<>("success Register",HttpStatus.OK);
    }
    @GetMapping("/login/{email}/{pass}")
    public User login(@RequestParam String email, @RequestParam String pass){
        User verificacion = null;
        verificacion = userRepository.findByEmailAndSoftDeleteIsFalse(email);

        if(verificacion == null){
            throw new ResourceNotFoundException("The user is not register in the system");
        }
        boolean band= verificacion.checkPassword(pass);
        if(band){
            return verificacion;
        }else{
            throw new ForbiddenExcpection("The password is incorrect");
        }
    }
    @PutMapping("/edit")
    public ResponseEntity<String> editUser(@RequestBody HashMap<String, Object> requestBody){
        //this return the id save in the Hasmap
        Optional<User> optionalUser =  userRepository.findById((String) requestBody.get("id"));
        requestBody.remove("id");//remove the parameter from the requestbody
        User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("The user id is not admitted"));//throw exception
        checkSoftDelete(user);//chec for valid user

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
                field.set(user, val);

            } catch (NoSuchFieldException | IllegalAccessException error) {
                throw new ResourceNotFoundException("Error setting field " + key + ": " + error);
            }
        }
        return new ResponseEntity<>("user edit successful",HttpStatus.OK);
    }
    @PutMapping("/changePass")
    public ResponseEntity<String> changePassword(@RequestBody HashMap<String, String> requestBody){
        //here is aa specific method to change a password to the user
//        the front will be pass the following attributes
//        id: userID
//        password:currentPassowd
//        newPassowrd:newPassowrd
        Optional<User> optionalUser =  userRepository.findById((String) requestBody.get("id"));
        requestBody.remove("id");//remove the parameter from the requestbody
        User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("The user id is not admitted"));//throw exception
        checkSoftDelete(user);//chec for valid user

        if(requestBody.containsValue("password") && requestBody.containsValue("newPassword") && requestBody.size()==2){

            String password=requestBody.get("password");
            String newPassword=requestBody.get("newPassword");
            if(!user.checkPassword(password)){
                throw new ForbiddenExcpection("Wrong password");
            }
            //if everything is ok then save the new password en encryp it.
            user.setPassword(newPassword);
            user.encrypt();;
            userRepository.save(user);
        }else{
            throw new ForbiddenExcpection("The body doesn't contains all the parameter require for this change");
        }
        return new ResponseEntity<>("change success", HttpStatus.OK);
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@RequestParam String id){
        //check if the user exist
        Optional<User> optionalUser =  userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not register in the database"));//throw exception

        if(user.getSoftDelete()){
            return new ResponseEntity<>("The user is already disable", HttpStatus.OK);
        }
        user.setSoftDelete(true);
        userRepository.save(user);
        return new ResponseEntity<>("User now disable from the database", HttpStatus.OK);
    }
    @GetMapping("/getUsers")
    public List<User> getUsers(){
        //return all the user active in the database
        List<User> list= userRepository.findBySoftDeleteIsFalse();
        return list;

    }

    private void checkSoftDelete(User u){
        if(u.getSoftDelete()){
            throw new ForbiddenExcpection("The user is disable in the database");
        }
    }
}
