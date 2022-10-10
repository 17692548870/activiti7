package com.hpe.ctrm.service;

import com.hpe.ctrm.entity.User;

import java.util.Optional;

public interface UserService {
    User findUserByUsername(String username);
    Optional<User> findUserById(Integer userId);
}
