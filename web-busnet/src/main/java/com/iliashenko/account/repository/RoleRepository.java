package com.iliashenko.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iliashenko.account.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
