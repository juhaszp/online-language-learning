package com.online.repo;
import org.springframework.data.repository.CrudRepository;

import com.online.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	User findByActivation(String code);
}