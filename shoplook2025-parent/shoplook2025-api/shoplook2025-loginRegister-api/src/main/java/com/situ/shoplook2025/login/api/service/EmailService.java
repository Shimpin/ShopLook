package com.situ.shoplook2025.login.api.service;

public interface EmailService {
    void sendVerificationCode(String toEmail, String code);
}
