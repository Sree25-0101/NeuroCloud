package com.neurocloud.userservice.repository;

import com.neurocloud.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
