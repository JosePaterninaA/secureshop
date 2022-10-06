package com.secutitypoc.jwtauthserver.repositories;

import com.secutitypoc.jwtauthserver.models.Role;
import com.secutitypoc.jwtauthserver.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
