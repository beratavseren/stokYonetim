package com.example.demo.Controller;

import com.example.demo.Entity.Admin;
import com.example.demo.Entity.WerehouseWorker;
import com.example.demo.Repository.AdminRepo;
import com.example.demo.Repository.WerehouseWorkerRepo;
import com.example.demo.Security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AdminRepo adminRepo;
    private final WerehouseWorkerRepo workerRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AdminRepo adminRepo, WerehouseWorkerRepo workerRepo, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.workerRepo = workerRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public static class LoginRequest {
        public String email;
        public String password;
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public void setEmail(String email) { this.email = email; }
        public void setPassword(String password) { this.password = password; }
    }
    public static class LoginResponse {
        public String token;
        public String role;
        public LoginResponse(String token, String role) { this.token = token; this.role = role; }
        public String getToken() { return token; }
        public String getRole() { return role; }
        public void setToken(String token) { this.token = token; }
        public void setRole(String role) { this.role = role; }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Try admin first
        Admin admin = adminRepo.findByEmail(request.getEmail());
        if (admin != null && passwordMatches(request.getPassword(), admin.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", "ADMIN");
            String token = jwtService.generateToken(admin.getEmail(), claims);
            return ResponseEntity.ok(new LoginResponse(token, "ADMIN"));
        }
        // Try worker
        WerehouseWorker worker = workerRepo.findByEmail(request.getEmail());
        if (worker != null && passwordMatches(request.getPassword(), worker.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", "WORKER");
            if (worker.getWerehouse() != null) {
                claims.put("werehouseId", worker.getWerehouse().getWerehouseId());
            }
            String token = jwtService.generateToken(worker.getEmail(), claims);
            return ResponseEntity.ok(new LoginResponse(token, "WORKER"));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    private boolean passwordMatches(String raw, String stored) {
        if (stored == null) return false;
        // Support both plain text (existing data) and BCrypt (future/new data)
        if (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$")) {
            return passwordEncoder.matches(raw, stored);
        }
        return stored.equals(raw);
    }
}
