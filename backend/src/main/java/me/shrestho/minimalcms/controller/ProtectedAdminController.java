package me.shrestho.minimalcms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.shrestho.minimalcms.entity.Page;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.services.AuthService;
import me.shrestho.minimalcms.services.PageService;
import me.shrestho.minimalcms.services.UserService;
import me.shrestho.minimalcms.utils.enums.UserRoles;
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

    //////////////////////////////////
    /// New User
    //////////////////////////////////
    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestAttribute("auth_user") User admin,
            @RequestBody Map<String, Object> reqData, @RequestParam("role") String role) {
        Map<String, Object> resMap = authService.register(reqData, UserRoles.valueOf(role));

        return new ResponseEntity<>(resMap, resMap.get("success").equals(true) ? HttpStatus.CREATED
                : HttpStatus.BAD_REQUEST);
    }

    //////////////////////////////////
    /// User List (Search & Filters)
    //////////////////////////////////
    @GetMapping("/users")
    public ResponseEntity<?> listUsers(@RequestAttribute("auth_user") User admin, @RequestParam("qu") String userQuery,
            @RequestParam("limit") int limit, @RequestParam("page") int page) {

        Map<String, Object> users = userService.getByQueryAndLimit(userQuery, limit, page);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //////////////////////////////////
    /// Admin List (Search & Filters)
    //////////////////////////////////
    @GetMapping("/admins")
    public ResponseEntity<?> listAdmins(@RequestAttribute("auth_user") User admin, @RequestParam("qu") String userQuery,
            @RequestParam("limit") int limit, @RequestParam("page") int page) {

        Map<String, Object> users = userService.getAdminByQueryAndLimit(userQuery, limit, page);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //////////////////////////////////
    /// Delete User
    //////////////////////////////////

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@RequestAttribute("auth_user") User admin,
            @PathVariable("userId") String userId) {
        Map<String, Object> resMap = authService.deleteUserById(userId);

        return new ResponseEntity<>(resMap,
                resMap.get("success").equals(true) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    //////////////////////////////////
    /// Update user
    //////////////////////////////////
    @PatchMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@RequestAttribute("auth_user") User admin,
            @PathVariable("userId") String userId, @RequestBody Map<String, Object> reqData) {
        Map<String, Object> resMap = userService.updateUserById(userId, reqData);

        return new ResponseEntity<>(resMap,
                resMap.get("success").equals(true) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    //////////////////////////////////
    /// List Pages (Search & Filters)
    /// ?status=&limit=5&qu=&q=&page=1
    //////////////////////////////////

    @GetMapping("/pages")
    public ResponseEntity<?> listPages(
            @RequestAttribute("auth_user") User admin,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,

            @RequestParam("status") String status,
            @RequestParam("qu") String userQuery, // takes user.id only
            @RequestParam("q") String pageQuery) {

        // filter with status after all done for simplicity
        Map<String, Object> resObj = new HashMap<>();

        resObj = pageService.findPagesForAdminWithoutUser(page, limit, status, pageQuery, userQuery);

        return new ResponseEntity<>(resObj, HttpStatus.OK);
    }

    //////////////////////////////////
    /// Update Page Status, Ban, Unban
    //////////////////////////////////

    @PatchMapping("/pages/{pageId}")
    public ResponseEntity<?> updatePageStatus(@RequestAttribute("auth_user") User admin,
            @PathVariable("pageId") String pageId, @RequestBody Page updateTo) {

        Map<String, Object> resMap = pageService.updatePageStatusById(pageId, updateTo);

        return new ResponseEntity<>(resMap,
                resMap.get("success").equals(true) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    //////////////////////////////////
    /// Delete Page
    //////////////////////////////////

    @DeleteMapping("/pages/{pageId}")
    public ResponseEntity<?> deletePage(@RequestAttribute("auth_user") User admin,
            @PathVariable("pageId") String pageId) {
        Map<String, Object> resMap = pageService.deletePageById(pageId);

        return new ResponseEntity<>(resMap,
                resMap.get("success").equals(true) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
