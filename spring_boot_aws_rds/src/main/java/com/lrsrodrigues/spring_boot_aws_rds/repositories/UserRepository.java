package com.lrsrodrigues.spring_boot_aws_rds.repositories;

import com.lrsrodrigues.spring_boot_aws_rds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
