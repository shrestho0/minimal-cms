package me.shrestho.minimalcms.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.UserRepository;
import me.shrestho.minimalcms.utils.projections.UserProjection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Map<String, Object> changePassword(User user, Map<String, String> reqData) {

        Map<String, Object> resObj = new HashMap<String, Object>();

        resObj.put("success", false);

        if (reqData.get("oldPassword") == null || reqData.get("password") == null
                || reqData.get("passwordConfirm") == null) {
            resObj.put("message", "All fields are required");
            return resObj;
        }

        if (!reqData.get("password").equals(reqData.get("passwordConfirm"))) {
            resObj.put("message", "Passwords do not match");
            return resObj;
        }

        System.out.println("USER==========>>>" + user);

        // Optional<User> actualUser = userRepository.findById(user.getId());
        // if (!actualUser.isPresent()) {
        // resObj.put("message", "User not found");
        // return resObj;
        // }

        User actualUser = userRepository.findByUsername(user.getUsername());

        if (actualUser == null) {
            resObj.put("message", "User not found");
            return resObj;
        }

        if (!BCrypt.checkpw(reqData.get("oldPassword"), actualUser.getPasswordHash())) {
            resObj.put("message", "Old password is incorrect");
            return resObj;
        }

        String hashedPassword = BCrypt.hashpw(reqData.get("password"), BCrypt.gensalt());

        actualUser.setPasswordHash(hashedPassword);

        userRepository.save(actualUser);

        System.out.println("USER==========>>>" + actualUser);

        // If erverything is okay, then change the password
        resObj.put("success", true);
        resObj.put("message", "Password changed successfully");
        return resObj;
    }

    public List<UserProjection> getLastFiveUsers() {

        // Return last 5 users by `created` where page role = 'user'
        return userRepository.findTop5ByOrderByCreatedDesc();
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public long getTodaysUserCount() {
        return userRepository.countByCreatedToday();
    }

    // public Map<String, Object> create(User user) {

    // Map<String, Object> resObj = new HashMap<>();
    // // check user's username is unique
    // if (userRepository.findByUsername(user.getUsername()) != null) {
    // resObj.put("error", "Username already exists");
    // }
    // // check user's email is unique
    // if (userRepository.findByEmail(user.getEmail()) != null) {
    // resObj.put("error", "Email already exists");
    // }

    // if (resObj.containsKey("error")) {
    // resObj.put("success", false);
    // return resObj;
    // }

    // user.setId(UUID.randomUUID().toString());
    // user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(),
    // BCrypt.gensalt()));

    // User newUser = userRepository.save(user);

    // if (newUser == null) {
    // resObj.put("message", "Failed to create user");
    // resObj.put("success", false);
    // return resObj;
    // }

    // return resObj;
    // }

}
