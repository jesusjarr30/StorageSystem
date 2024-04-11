package com.imss.almacen.Repository;

import com.imss.almacen.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<User, String> {
}
