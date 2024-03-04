package me.shrestho.minimalcms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.shrestho.minimalcms.entity.Page;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.services.AuthService;
import me.shrestho.minimalcms.services.PageService;
import me.shrestho.minimalcms.services.UserService;
import me.shrestho.minimalcms.utils.projections.UserPagesProjection;
import me.shrestho.minimalcms.utils.projections.UserProjection;

@RestController
@RequestMapping("/admin")
public class ProtectedAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PageService pageService;

    @RequestMapping("/")
    public ResponseEntity<?> greet(@RequestAttribute("auth_user") User admin) {
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("success", true);
        resMap.put("message", "Hello Admin");
        resMap.put("user", admin);
        return new ResponseEntity<>(resMap, HttpStatus.OK);

    }

    /**
     * Create Users
     */

    @PostMapping("/new-user")
    public ResponseEntity<?> addUser(@RequestAttribute("auth_user") User admin,
            @RequestBody Map<String, Object> reqData) {
        Map<String, Object> resMap = authService.register(reqData);

        return new ResponseEntity<>(resMap, resMap.get("success").equals(true) ? HttpStatus.CREATED
                : HttpStatus.BAD_REQUEST);
    }

    //////////////////////////////////
    /// Change Password
    //////////////////////////////////

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestAttribute("auth_user") User admin,
            @RequestBody Map<String, String> reqData) {

        Map<String, Object> resObj = userService.changePassword(admin, reqData);
        Boolean success = (Boolean) resObj.get("success");
        return new ResponseEntity<>(resObj, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

    }

    //////////////////////////////////
    /// Dashboard Data
    //////////////////////////////////

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(@RequestAttribute("auth_user") User admin) {

        Map<String, Object> resObj = new HashMap<String, Object>();

        List<UserProjection> last5Users = userService.getLastFiveUsers();
        List<UserPagesProjection> last5Pages = pageService.getLastFivePages();
        long userCount = userService.getUserCount();
        long pageCount = pageService.getPageCount();

        long todaysUserCount = userService.getTodaysUserCount();
        long todaysPageCount = pageService.getTodaysPageCount();

        resObj.put("last5Users", last5Users);
        resObj.put("last5Pages", last5Pages);

        resObj.put("userCount", userCount);
        resObj.put("pageCount", pageCount);
        resObj.put("todaysUserCount", todaysUserCount);
        resObj.put("todaysPageCount", todaysPageCount);

        return new ResponseEntity<>(resObj, HttpStatus.OK);

    }

}
