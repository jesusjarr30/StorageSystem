package com.imss.almacen.Repository;

import com.imss.almacen.Models.Lender;
import com.imss.almacen.Models.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,String> {

    List<Lender> findBySoftDeleteIsFalse();
}
