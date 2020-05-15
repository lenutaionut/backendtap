package com.tap.backendtap.controller;

import com.tap.backendtap.model.LoginRequest;
import com.tap.backendtap.model.User;
import com.tap.backendtap.repository.UserRepo;
import com.tap.backendtap.security.Model.JwtResponse;
import com.tap.backendtap.security.jwt.JwtUtils;
import com.tap.backendtap.security.services.UserDetailsImpl;
import com.tap.backendtap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Florina on 5/6/2020
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepo userRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest user) throws UnsupportedEncodingException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user ) {
        if (userRepository.existsUserByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        User newUser = new User(user.getName(), user.getSurname(), user.getEmail(), encoder.encode(user.getPassword()));
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully!");
    }

    @RequestMapping(value = "/idByEmail", method = GET)
    public ResponseEntity<Long> findIdByEmail(@RequestParam String email) {
        Long userId = userService.findIdUserByEmail(email);
        return new ResponseEntity<Long>(userId, HttpStatus.OK);
    }
}
