package me.shrestho.minimalcms.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.UserRepository;
import me.shrestho.minimalcms.utils.enums.UserRoles;
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

    public Map<String, Object> getByQueryAndLimit(String userQuery, int limit, int page) {
        Map<String, Object> resObj = new HashMap<>();
        int offset = (page - 1) * limit;
        List<UserProjection> items = userRepository.findRegularUserByQueryAndLimit(userQuery, limit, offset);
        long totalItems = userRepository.countRegularUserByQueryAndLimit(userQuery, limit, offset);
        long totalPages = (long) Math.ceil((double) totalItems / limit);
        resObj.put("totalItems", totalItems);
        resObj.put("totalPages", totalPages);
        resObj.put("page", page);
        resObj.put("perPage", limit);
        resObj.put("items", items);
        return resObj;
    }

    public Map<String, Object> getAdminByQueryAndLimit(String userQuery, int limit, int page) {
        Map<String, Object> resObj = new HashMap<>();
        int offset = (page - 1) * limit;
        List<UserProjection> items = userRepository.findAdminUserByQueryAndLimit(userQuery, limit, offset);
        long totalItems = userRepository.countAdminUserByQueryAndLimit(userQuery, limit, offset);
        long totalPages = (long) Math.ceil((double) totalItems / limit);
        resObj.put("totalItems", totalItems);
        resObj.put("totalPages", totalPages);
        resObj.put("page", page);
        resObj.put("perPage", limit);
        resObj.put("items", items);
        return resObj;
    }

    public Map<String, Object> updateUserById(String userId, Map<String, Object> reqData) {

        User originalUser = userRepository.findById(userId).orElse(null);
        if (originalUser == null) {
            Map<String, Object> resObj = new HashMap<>();
            resObj.put("success", false);
            resObj.put("message", "User not found");
            return resObj;
        }

        Map<String, Object> resObj = new HashMap<>();
        resObj.put("success", false);

        Map<String, Object> errors = new HashMap<>();

        // Parse Data
        String username = (String) reqData.get("username");
        String email = (String) reqData.get("email");
        String name = (String) reqData.get("name");
        String password = (String) reqData.get("password");

        if (username != null) {
            if (!originalUser.getUsername().equals(username)) {
                if (userRepository.findByUsername(username) != null) {
                    errors.put("username", "Username already exists");
                } else {
                    originalUser.setUsername(username);
                }
            }
        }

        if (email != null) {
            if (!originalUser.getEmail().equals(email)) {
                if (userRepository.findByEmail(email) != null) {
                    errors.put("email", "Email already exists");
                } else {
                    originalUser.setEmail(email);
                }
            }
        }

        if (name != null) {
            originalUser.setName(name);
        }

        if (password != null) {
            originalUser.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        }

        if (errors.size() > 0) {
            resObj.put("errors", errors);
            return resObj;
        }

        try {
            userRepository.save(originalUser);
            resObj.put("success", true);
            resObj.put("message", "User updated successfully");
        } catch (Exception e) {
            resObj.put("message", "Failed to update user");
        }

        return resObj;

    }

    public List<User> findAllUserByQuery(String userQuery) {
        String likeQuery = "%" + userQuery + "%";
        UserRoles role = UserRoles.USER;
        return userRepository.findByNameLikeOrUsernameLikeOrIdEqualsAndRoleEquals(likeQuery, likeQuery, userQuery,
                role);

    }

}
