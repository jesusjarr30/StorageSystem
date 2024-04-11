package com.imss.almacen.Repository;

import com.imss.almacen.Models.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVersionRepository extends JpaRepository<ProductVersion,String> {
}
