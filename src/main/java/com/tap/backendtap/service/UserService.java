package com.tap.backendtap.service;

import com.tap.backendtap.model.User;
import com.tap.backendtap.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Florina on 5/6/2020
 */
@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public Long findIdUserByEmail(String email){
        return userRepo.getUserIdByEmail(email);
    }
}
