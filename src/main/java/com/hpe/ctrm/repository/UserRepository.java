package com.hpe.ctrm.repository;

import com.hpe.ctrm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * user表数据访问层
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);

    Optional<User> findById(Integer userId);
}
