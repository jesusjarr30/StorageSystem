package com.imss.almacen.Repository;

import com.imss.almacen.Models.Lender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LenderRepository  extends JpaRepository<Lender,String> {


    List<Lender> findByNameAndSoftDelete(String name, boolean softDelete);
    List<Lender> findBySoftDeleteIsFalse();
}
