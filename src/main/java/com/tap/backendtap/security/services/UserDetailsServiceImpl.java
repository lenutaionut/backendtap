package com.tap.backendtap.security.services;

import com.tap.backendtap.model.User;
import com.tap.backendtap.repository.UserRepo;
import com.tap.backendtap.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email:" + email);
        }
        return UserDetailsImpl.build(user);
    }
}