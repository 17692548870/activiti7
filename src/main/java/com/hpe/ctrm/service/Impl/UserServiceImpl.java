package com.hpe.ctrm.service.Impl;

import com.hpe.ctrm.entity.User;
import com.hpe.ctrm.repository.UserRepository;
import com.hpe.ctrm.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;

    /**
     * 查询用户根据姓名
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 查询用户根据id
     * @param userId
     * @return
     */
    @Override
    public Optional<User> findUserById(Integer userId) {
       return userRepository.findById(userId);
    }
}
