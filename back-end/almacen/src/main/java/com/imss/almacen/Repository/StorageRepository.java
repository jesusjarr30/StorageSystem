package com.imss.almacen.Repository;

import com.imss.almacen.Models.Lender;
import com.imss.almacen.Models.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<Storage,String> {

    List<Lender> findByNameStorageAndSoftDelete(String name, boolean softDelete);
    List<Lender> findBySoftDeleteIsFalse();
}
