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

@Setter
@Getter
@ToString

@Entity
@Table(name = "lender")
public class Lender {
    @Id
    private String id;
    @NotNull
    @Column(length = 40)
    private String name;
    @NotNull
    @Column(length = 12)
    private String telephone;
    @NotNull
    @Column(length = 50)
    private String direccion;
    private boolean softDelete;

    public Lender(String name, String telephone,String direccion){
        this.softDelete=false;
        this.name=name;
        this.telephone=telephone;
        this.direccion=direccion;
        generateId();
    }

    public void generateId(){
        id = String.valueOf(UUID.randomUUID());
    }
}
