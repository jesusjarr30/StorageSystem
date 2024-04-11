package com.imss.almacen.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString

@Entity
@Table(name = "storage")
public class Storage {
    @Id
    private String id;
    private String nameStorage;
    private String address;
    private boolean softDelete;

    public Storage(String nameStorage,String address){
        generateId();
        this.nameStorage=nameStorage;
        this.address=address;
        softDelete=false;
    }
    public void generateId(){
        id = String.valueOf(UUID.randomUUID());
    }



}
