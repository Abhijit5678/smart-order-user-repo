package com.example.user_service.Dto;

import lombok.Data;


public class LoginRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest(String password, String username) {
        this.password = password;
        this.username = username;
    }
}
