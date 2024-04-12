package com.imss.almacen.Repository;

import com.imss.almacen.Models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,String> {

    List<Supplier> findBySoftDeleteIsFalse();




}
