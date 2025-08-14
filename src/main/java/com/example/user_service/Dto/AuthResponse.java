package com.example.user_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AuthResponse {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}