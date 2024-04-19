package com.imss.almacen.Repository;

import com.imss.almacen.Models.Product;
import com.imss.almacen.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    List<Product> findBySoftDeleteIsFalse();//return all list active
    List<Product> findByNameAndSoftDelete(String name, boolean softDelete);
}
