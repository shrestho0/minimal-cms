package me.shrestho.minimalcms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.services.UserService;

@RestController
@RequestMapping("/user")
public class ProtectedUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> get() {

        Map<String, Object> resObj = new HashMap<String, Object>();
        resObj.put("success", true);
        resObj.put("message", "Welcome authenticated user!");
        return new ResponseEntity<>(resObj, HttpStatus.OK);


    }
}