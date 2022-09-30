package com.secutitypoc.userproducts.repositories;


import com.secutitypoc.userproducts.models.UserProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProductsRepository  extends JpaRepository<UserProducts, Long> {
    Optional<UserProducts> findByUserId(Long userId);
}
