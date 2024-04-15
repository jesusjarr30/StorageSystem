package com.imss.almacen.Repository;

import com.imss.almacen.Models.Lender;
import com.imss.almacen.Models.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVersionRepository extends JpaRepository<ProductVersion,String> {


    List<ProductVersion> findBySoftDeleteIsFalse();
}
