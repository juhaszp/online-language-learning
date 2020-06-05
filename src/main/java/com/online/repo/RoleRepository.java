package com.online.repo;
import org.springframework.data.repository.CrudRepository;

import com.online.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String role);
}