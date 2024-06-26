package com.imss.almacen.Repository;

import com.imss.almacen.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
    User findByEmailAndSoftDeleteIsFalse(String email);

    List<User> findBySoftDeleteIsFalse();//return all list active


}
