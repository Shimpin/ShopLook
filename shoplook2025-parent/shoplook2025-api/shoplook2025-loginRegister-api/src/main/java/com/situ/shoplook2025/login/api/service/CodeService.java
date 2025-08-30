package com.situ.shoplook2025.login.api.service;

public interface CodeService {
    String generateCode();
    void saveCode(String email, String code);
    boolean validateCode(String email, String code);
}