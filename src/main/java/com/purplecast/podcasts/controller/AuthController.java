package com.purplecast.podcasts.controller;

import com.purplecast.podcasts.config.security.jwt.JwtUtil;
import com.purplecast.podcasts.dto.LoginRequest;
import com.purplecast.podcasts.dto.RegisterRequest;
import com.purplecast.podcasts.dto.TokenResponse;
import com.purplecast.podcasts.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8082")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserService userDetailService;
    private JwtUtil jwtTokenUtil;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new TokenResponse(jwt));
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest){
        userDetailService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
