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
@Table(name= "productVersion")
public class ProductVersion {
    @Id
    private String id;
    @Column(length = 6)
    @NotNull
    private String identification;
    @Column(length = 6)
    @NotNull
    private String idProduct;
    @Column(length = 40)
    @NotNull
    private String idStorage;
    private boolean active;//aviable now or not
    private boolean softDelete;
    public ProductVersion(){

    }
    public ProductVersion(String identification, String idProduct, String idStorage){
        generateId();
        this.identification=identification;
        this.idProduct=idProduct;
        this.idStorage=idStorage;
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
