package me.shrestho.minimalcms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.shrestho.minimalcms.entity.Profile;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.services.ProfileService;
import me.shrestho.minimalcms.services.PublicPageService;
import me.shrestho.minimalcms.services.UserService;

@RestController
@RequestMapping("/")
public class PublicPageController {

    @Autowired
    private PublicPageService publicPageService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    /*
     * Page Content
     */
    @GetMapping("/page/{username}/{pageSlug}")
    public ResponseEntity<?> getPageContent(@PathVariable String username, @PathVariable String pageSlug) {
        // Check if user exists, else return 404
        User user = userService.getByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(Map.of("success", false, "message", "User not found"), HttpStatus.NOT_FOUND);
        }

        Map<String, Object> resObj = publicPageService.getPage(user, pageSlug);

        return new ResponseEntity<>(resObj,
                resObj.get("success").equals(true) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    ////////////////////

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getProfileContent(@PathVariable String username) {

        // Check if user exists, else return 404
        User user = userService.getByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(Map.of("success", false, "message", "User not found"), HttpStatus.NOT_FOUND);
        }

        Profile profile = profileService.getProfile(user);

        if (profile == null) {
            return new ResponseEntity<>(Map.of("success", false, "message", "Profile not found"), HttpStatus.NOT_FOUND);
        }

        profile.setUser(null);

        Map<String, Object> resObj = new HashMap<>();
        resObj.put("success", true);
        resObj.put("profile", profile);
        user.setPasswordHash(null);
        resObj.put("owner", user);

        return new ResponseEntity<>(resObj, HttpStatus.OK);

    }

    /**
     * 
     * Header, Footer
     * 
     * @return
     */
    @GetMapping("/stuff/{username}")
    public ResponseEntity<?> getStuff(@PathVariable String username) {

        // Check if user exists, else return 404
        User user = userService.getByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(Map.of("success", false, "message", "User not found"), HttpStatus.NOT_FOUND);
        }

        Map<String, Object> resObj = publicPageService.getStuff(user);

        return new ResponseEntity<>(resObj,
                resObj.get("success").equals(true) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/design/{username}")
    public ResponseEntity<?> getDesign(@PathVariable String username) {

        // Check if user exists, else return 404
        User user = userService.getByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(Map.of("success", false, "message", "User not found"), HttpStatus.NOT_FOUND);
        }

        Map<String, Object> resObj = publicPageService.getDesign(user);

        return new ResponseEntity<>(resObj,
                resObj.get("success").equals(true) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
