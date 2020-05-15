package com.tap.backendtap.model;

/**
 * Created by Florina on 5/12/2020
 */
public class LoginRequest {
    String email;
    String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
