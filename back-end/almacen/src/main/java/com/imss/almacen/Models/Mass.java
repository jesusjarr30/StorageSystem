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
@Table(name= "mass")
public class Mass {
    @Id
    private String id;
    @NotNull
    private int amount;
    @NotNull
    private int dozens;
    @NotNull
    private float weight;
    @NotNull
    @Column(length = 40)
    private String description;
    private boolean softDelete;

    public Mass(){

    }
    public Mass(int amount,int dozens,float weight, String description){
        this.amount=amount;
        this.dozens=dozens;
        this.weight=weight;
        this.description=description;
        generateId();
        this.softDelete=false;
    }
    public void generateId(){
        id = String.valueOf(UUID.randomUUID());
    }

    public boolean getSoftDelete(){
        return softDelete;
    }
    public void setSoftDelete(boolean softDelete){
        this.softDelete=softDelete;
    }
}