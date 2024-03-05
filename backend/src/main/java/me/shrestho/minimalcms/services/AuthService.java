package me.shrestho.minimalcms.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.interfaces.DecodedJWT;

import me.shrestho.minimalcms.entity.TokenBlacklisted;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.PageRepository;
import me.shrestho.minimalcms.repository.TokenBlacklistedRepository;
import me.shrestho.minimalcms.repository.UserRepository;
import me.shrestho.minimalcms.utils.JwtUtils;
import me.shrestho.minimalcms.utils.Utils;
import me.shrestho.minimalcms.utils.enums.TokenType;
import me.shrestho.minimalcms.utils.enums.UserRoles;
import me.shrestho.minimalcms.utils.projections.UserProjection;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SiteFooterService siteFooterService;

    @Autowired
    private SiteHeaderService siteHeaderService;

    @Autowired
    private SiteStyleService siteStyleService;

    @Autowired
    private PageService pageService;

    // @Autowired
    // private SiteFooter

    @Autowired
    private TokenBlacklistedRepository tokenBlacklistedRepository;

    //////////////////////////////////
    /// Auth Section
    //////////////////////////////////
    public Map<String, Object> register(Map<String, Object> newUserData, UserRoles role) {
        Map<String, Object> ResponseObj = new HashMap<String, Object>(
                Map.of("success", false, "message", "All fields are required"));

        Map<String, Object> errorObj = new HashMap<String, Object>();

        // check if any field is missing

        for (String key : Utils.requiredRegisterFields) {
            if (newUserData.get(key) == null) {
                return ResponseObj;
            }
            System.out.println("key: " + key + " " + newUserData.get(key));
        }

        String passwordHash = BCrypt.hashpw((String) newUserData.get("password"), BCrypt.gensalt());

        User user = new User();
        user.setName((String) newUserData.get("name"));
        user.setUsername((String) newUserData.get("username"));
        user.setEmail((String) newUserData.get("email"));
        user.setPasswordHash(passwordHash);

        // Only role USER is allowed here
        user.setRole(role);

        System.out.println(user);

        // check if username exists
        User userWithUsername = userRepository.findByUsername(user.getUsername());
        if (userWithUsername != null) {
            errorObj.put("username", "Username already exists");
        }
        User userWithEmail = userRepository.findByEmail(user.getEmail());
        if (userWithEmail != null) {
            errorObj.put("email", "Email already exists");
        }
        // Check if passwords match
        if (!newUserData.get("password").equals(newUserData.get("passwordConfirm"))) {
            errorObj.put("passwordConfirm", "Passwords do not match");
        }
        // Return error if username or email exists
        if (!errorObj.isEmpty()) {
            ResponseObj.put("message", "Some fields are invalid");
            ResponseObj.put("errors", errorObj);
            return ResponseObj;
        }

        // System.out.println("User with username " + userWithUsername);

        // check if email exists
        // check if password and passwordConfirm matches
        // Simply return success:true or false,

        try {

            user.setId(UUID.randomUUID().toString());
            User newUser = userRepository.save(user);

            ResponseObj.put("success", true);
            ResponseObj.put("message", "User created successfully");
            ResponseObj.put("user", newUser);

            // Misc Stuff
            if (role == UserRoles.ADMIN) {
                return ResponseObj;
            }

            try {
                profileService.addProfile(newUser);
                siteFooterService.addSiteFooter(newUser);
                siteHeaderService.addSiteHeader(newUser);
                siteStyleService.addSiteStyle(newUser);
            } catch (Exception e) {
                // handle exception
                System.out.println(e);
            }

            return ResponseObj;
        } catch (Exception e) {
            ResponseObj.put("message", "Error creating user");
            return ResponseObj;
        }

    }

    public Map<String, Object> login(Map<String, Object> reqData) {

        Map<String, Object> ResponseObj = new HashMap<String, Object>(
                Map.of("success", false, "message", "All fields are required"));

        System.out.println("reqData: " + reqData);
        if (reqData.get("email") == null || reqData.get("password") == null) {
            ResponseObj.put("required", Utils.requiredLoginFields);
            return ResponseObj;
        }

        // check if user exists with email

        User userWithEmail = userRepository.findByEmail((String) reqData.get("email"));
        if (userWithEmail == null) {
            ResponseObj.put("message", "User not found with email");
            return ResponseObj;
        }

        // match passwords
        Boolean passwordMatched = BCrypt.checkpw((String) reqData.get("password"), userWithEmail.getPasswordHash());
        if (!passwordMatched) {
            ResponseObj.put("message", "Password does not match");
            return ResponseObj;
        }

        // Safe here
        // Generate tokens

        // Return tokens

        // System.out.println("emailUser: " + userWithEmail + " ");

        JwtUtils jwtUtils = new JwtUtils();
        String accessToken = jwtUtils.generateToken(userWithEmail, TokenType.ACCESS);
        String refreshToken = jwtUtils.generateToken(userWithEmail, TokenType.REFRESH);

        ResponseObj.put("success", true);
        ResponseObj.put("message", "Login successful");
        Map<String, String> Tokens = new HashMap<String, String>();
        Tokens.put("access", accessToken);
        Tokens.put("refresh", refreshToken);
        ResponseObj.put("tokens", Tokens);

        return ResponseObj;
        // Validate user's credentials
        // Generate token
        // Return token
    }

    public Map<String, Object> refreshToken(TokenBlacklisted tokenBlacklisted) {

        Map<String, Object> ResponseObj = new HashMap<String, Object>(
                Map.of("success", false));

        if (tokenBlacklisted.getRefreshToken() == null) {
            ResponseObj.put("message", "Refresh token is required");
            return ResponseObj;
        }

        // Check if token is blacklisted

        System.out.println("tokenBlacklisted: " + tokenBlacklisted + " ");

        // Check if tokens is blacklisted
        TokenBlacklisted tb = tokenBlacklistedRepository.findByRefreshToken(tokenBlacklisted.getRefreshToken());
        if (tb != null) {
            ResponseObj.put("message", "Token is blacklisted");
            return ResponseObj;
        }

        JwtUtils jwtUtils = new JwtUtils();
        DecodedJWT tokenData;
        try {
            tokenData = jwtUtils.verifyTokenAndReturnDecoded(tokenBlacklisted.getRefreshToken());
            if (TokenType.valueOf(tokenData.getSubject()) != TokenType.REFRESH) {
                ResponseObj.put("message", "Invalid token type");
                return ResponseObj;
            }
            System.out.println("tokenData: " + tokenData.getClaim("id") + " ");

        } catch (Exception e) {
            ResponseObj.put("message", e.getMessage());
            return ResponseObj;
        }

        // Dhuklei blacklist, we don't care
        tokenBlacklisted.setId(UUID.randomUUID().toString());
        tokenBlacklistedRepository.save(tokenBlacklisted);

        // Check if user exists
        String userId = tokenData.getClaim("id").asString();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            ResponseObj.put("message", "User does not exist anymore");
            return ResponseObj;
        }

        // Generate new tokens
        String accessToken = jwtUtils.generateToken(user, TokenType.ACCESS);
        String refreshToken = jwtUtils.generateToken(user, TokenType.REFRESH);
        Map<String, String> tokens = new HashMap<String, String>();
        tokens.put("access", accessToken);
        tokens.put("refresh", refreshToken);

        ResponseObj.put("success", true);
        ResponseObj.put("tokens", tokens);

        return ResponseObj;

    }

    public Map<String, Object> invalidateRefreshToken(TokenBlacklisted token) {

        Map<String, Object> ResponseObj = new HashMap<String, Object>(
                Map.of("success", false));

        if (token.getRefreshToken() == null) {
            ResponseObj.put("message", "Refresh token header is required");
            return ResponseObj;
        }

        // Verify token
        JwtUtils jwtUtils = new JwtUtils();
        DecodedJWT tokenData;
        try {
            tokenData = jwtUtils.verifyTokenAndReturnDecoded(token.getRefreshToken());
            if (TokenType.valueOf(tokenData.getSubject()) != TokenType.REFRESH) {
                ResponseObj.put("message", "Invalid token type");
                return ResponseObj;
            }
        } catch (Exception e) {
            ResponseObj.put("message", e.getMessage());
            return ResponseObj;
        }

        // Check if tokens is blacklisted
        TokenBlacklisted tb = tokenBlacklistedRepository.findByRefreshToken(token.getRefreshToken());
        if (tb != null) {
            ResponseObj.put("message", "Token is already blacklisted");
            return ResponseObj;
        }

        token.setId(UUID.randomUUID().toString());
        tokenBlacklistedRepository.save(token);

        ResponseObj.put("success", true);
        ResponseObj.put("message", "Token blacklisted successfully");
        return ResponseObj;
    }

    @Transactional
    public Map<String, Object> deleteUserById(String userId) {

        Map<String, Object> ResponseObj = new HashMap<String, Object>(
                Map.of("success", false));

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            ResponseObj.put("message", "User not found");
            return ResponseObj;
        }

        // delete pages with user
        pageService.deletePageByUser(user);

        if (user.getRole().equals(UserRoles.ADMIN)) {
            // ResponseObj.put("message", "Admin cannot be deleted");
            userRepository.delete(user);
            ResponseObj.put("success", true);
            ResponseObj.put("message", "Admin deleted successfully");
            return ResponseObj;
        }
        try {
            // delete siteHeader with userId
            siteHeaderService.deleteSiteHeaderByUser(user);
            // delete siteFooter with userId
            siteFooterService.deleteSiteFooterByUser(user);
            // delete siteStyle with userId
            siteStyleService.deleteSiteStyleByUser(user);
            // delete profile with userId
            profileService.deleteProfileByUser(user);

            userRepository.delete(user);
            ResponseObj.put("success", true);
            ResponseObj.put("message", "User deleted successfully");
        } catch (Exception e) {

            ResponseObj.put("message", "Error deleting user");
            System.out.println(e);
        }

        return ResponseObj;

    }

}
