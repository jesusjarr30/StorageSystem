package com.imss.almacen.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@ToString

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    private String id;
    private Date dateExit;
    private String idLender;
    private int amount;
    private Date dateReturn;
    private boolean returnF;
    private boolean softDelete;

    public Transaction(Date dateExit, String idLender,int amount,Date dateReturn, boolean retunF){
        this.dateExit=dateExit;
        this.idLender=idLender;
        this.amount=amount;
        this.dateReturn=dateReturn;
        this.returnF=retunF;
        softDelete=false;
        generateId();
    }
    public Transaction(){

    }
    public void generateId(){
        id = String.valueOf(UUID.randomUUID());
    }
    public boolean getSoftDelete(){
        return softDelete;
    }
    public void setSoftDelete(boolean softDelete) {
        this.softDelete = softDelete;
    }
}

