package me.shrestho.minimalcms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.shrestho.minimalcms.entity.TokenBlacklisted;
import me.shrestho.minimalcms.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired(required = true)
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody Map<String, Object> reqData) {
        Map<String, Object> resMap = authService.register(reqData);

        return new ResponseEntity<>(resMap, resMap.get("success").equals(true) ? HttpStatus.CREATED
                : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/tokens")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, Object> reqData) {
        Map<String, Object> resMap = authService.login(reqData);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenBlacklisted tokenBlacklisted) {
        Map<String, Object> resMap = authService.refreshToken(tokenBlacklisted);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

}
