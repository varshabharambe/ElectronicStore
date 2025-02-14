package com.electronics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronics.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{

}
