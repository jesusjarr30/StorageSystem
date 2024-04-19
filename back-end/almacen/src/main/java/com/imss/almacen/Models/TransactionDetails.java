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
@Table( name = "transactionDetails")

public class TransactionDetails {
    @Id
    private String id;
    @NotNull
    @Column(length = 40)
    private String idTransaction;
    @NotNull
    @Column(length = 40)
    private String idVersion;
    

    public TransactionDetails(String idTransaction,String idVersion){
        this.idTransaction=idTransaction;
        this.idVersion=idVersion;
        generateId();
    }
    public TransactionDetails(){

    }
    public void generateId(){
        id = String.valueOf(UUID.randomUUID());
    }

}
