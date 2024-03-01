package me.shrestho.minimalcms.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.UserRepository;
import me.shrestho.minimalcms.utils.Utils;
import me.shrestho.minimalcms.utils.enums.UserRoles;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

}
