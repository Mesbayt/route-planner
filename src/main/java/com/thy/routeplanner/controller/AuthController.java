package com.thy.routeplanner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Geçici sabit kullanıcı kontrolü
        if ("admin".equals(username) && "1234".equals(password)) {
            // Geriye sahte bir token gönderiyoruz
            return ResponseEntity.ok(Map.of("token", "dummy-token"));
        } else {
            return ResponseEntity.status(401).body("Kullanıcı adı veya şifre hatalı");
        }
    }
}
