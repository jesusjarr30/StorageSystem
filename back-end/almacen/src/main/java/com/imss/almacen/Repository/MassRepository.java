package com.imss.almacen.Repository;

import com.imss.almacen.Models.Mass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassRepository extends JpaRepository<Mass,String> {
}
