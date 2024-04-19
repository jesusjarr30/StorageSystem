package com.imss.almacen.Repository;

import com.imss.almacen.Models.Lender;
import com.imss.almacen.Models.Mass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MassRepository extends JpaRepository<Mass,String> {

    //List<Mass> findByNameAndSoftDelete(String name, boolean softDelete);
    List<Mass> findBySoftDeleteIsFalse();
}
