package com.imss.almacen.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;
@Getter
@Setter
@ToString

@Entity
@Table(name ="product")
public class Product {
    @Id
    private String id;
    @Column(length = 40)
    @NotNull
    private String name;
    @Column(length = 40)
    @NotNull
    private String idSupplier;
    @Column(length = 40)
    @NotNull
    private String description;
    @Column(length = 40)
    @NotNull
    private String brand;
    private String idMass;
    private boolean softDelete;
    public Product(String name,String description,String idSupplier, String brand,String idMass){
    generateId();
    this.name=name;
    this.idSupplier=idSupplier;
    this.description=description;
    this.brand=brand;
    this.idMass=idMass;
    softDelete=false;
    }
    public Product(){

    }
    public void generateId(){
        id = String.valueOf(UUID.randomUUID());
    }

    public boolean getSoftDelete(){
        return this.softDelete;
    }
    public void setSoftDelete(boolean softDelete){
        this.softDelete=softDelete;
    }
}
