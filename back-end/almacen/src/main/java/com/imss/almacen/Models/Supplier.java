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
@Table(name = "supplier")
public class Supplier {
    @Id
    private String id;
    @NotNull
    @Column(length = 50)
    private String company;
    @NotNull
    @Column(length = 50)
    private String address;
    @NotNull
    @Column(length = 50)
    private String contactPerson;//name person
    @NotNull
    @Column(length = 12)
    private String contactNumber;//telephone contact
    private boolean state;//if the company is active or not
    private  boolean softDelete;

    public Supplier(){

    }

    public Supplier(String company,String address, String contactNumber, String contactPerson){

        generateId();
        this.company=company;
        this.address=address;
        this.contactPerson=contactPerson;
        this.contactNumber=contactNumber;
        this.softDelete=false;
        this.state=true;//all supplier aat the momento of the register will be active
    }
    public void generateId(){
        id = String.valueOf(UUID.randomUUID());
    }
}