package com.imss.almacen.Repository;

import com.imss.almacen.Models.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage,String> {
}
