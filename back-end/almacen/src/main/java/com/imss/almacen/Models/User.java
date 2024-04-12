package com.imss.almacen.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Setter
@Getter
@ToString

@Entity
@Table( name = "user")
public class User {
    @Id
    @Column(length = 100)
    @NotNull
    private String id;
    @Column(length = 50)
    @NotNull
    private String firstName;
    @Column(length = 50)
    @NotNull
    private String lastName;
    @NotNull
    @Column(length = 30)
    private String email;
    @NotNull
    @Column(length = 100)
    private String password;
    @NotNull
    @Column(length = 5)
    private String role;//ADMIN or USER
    private boolean softDelete;
    public User() {
    }

    public User (String firstName, String lastName, String email, String password, String role,boolean softDelete){
        generateId();
        this.firstName = firstName;
        this.lastName= lastName;
        this.email = email;
        this.password= password;
        this.role=role;
        this.softDelete=false;
    }
    public User (String id,String firstName, String lastName, String email, String password, String role,boolean softDelete){
        generateId();
        this.firstName = firstName;
        this.lastName= lastName;
        this.email = email;
        this.password= password;
        this.role=role;
        this.softDelete=false;
    }
    public void generateId(){
        this.id = String.valueOf(UUID.randomUUID());
    }
    public void encrypt() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(this.password);
    }
    public boolean checkPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, this.password);
    }
    public boolean getSoftDelete(){
        return softDelete;
    }
    public void setSoftDelete(boolean softDelete){
        this.softDelete=softDelete;

    }
}