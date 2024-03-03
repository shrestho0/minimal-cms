package me.shrestho.minimalcms.controller;

import java.util.HashMap;
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
import me.shrestho.minimalcms.services.PageService;
import me.shrestho.minimalcms.services.UserService;

@RestController
@RequestMapping("/user")
public class ProtectedUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PageService pageService;

    @GetMapping("/")
    public ResponseEntity<?> get() {

        Map<String, Object> resObj = new HashMap<String, Object>();
        resObj.put("success", true);
        resObj.put("message", "Welcome authenticated user!");
        return new ResponseEntity<>(resObj, HttpStatus.OK);

    }

    //////////////////////////////////
    /// Page Section
    //////////////////////////////////

    /**
     * List Pages
     * Returns all pages of user
     */
    @GetMapping("/pages")
    public ResponseEntity<?> getPages(
            @RequestAttribute("auth_user") User user,
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            @RequestParam("status") String status,
            @RequestParam("q") String q) {

        System.out.println("Page Filters" + " " + page + " " + limit + " " + status + " " + q + " ");
        System.out.println("");

        return new ResponseEntity<>(pageService.getPagesByUserWithFilters(user, page, limit, status, q),
                HttpStatus.OK);
    }

    @GetMapping("/pages/{id}")
    public ResponseEntity<?> getPage(@RequestAttribute("auth_user") User user, @PathVariable("id") String id) {
        if (id == null || id.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // if len = 36, find with
        if (id.length() == 36) {

            Map<String, Object> resObj = pageService.getSinglePage(user, id);

            return new ResponseEntity<>(resObj,
                    resObj.get("success").equals(true) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        }

        // Maybe, slug
        Map<String, Object> resObj = pageService.getSinglePageBySlug(user, id);
        if (resObj.get("success").equals(true)) {
            return new ResponseEntity<>(resObj, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/pages/{id}")
    public ResponseEntity<?> updatePage(@RequestAttribute("auth_user") User user, @PathVariable("id") String id,
            @RequestBody Page page) {

        if (id == null || id.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> resObj = pageService.updatePage(user, id, page);

        return new ResponseEntity<>(resObj,
                resObj.get("success").equals(true) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    /**
     * Create Page
     */

    @PostMapping("/pages")
    public ResponseEntity<?> createPage(@RequestAttribute("auth_user") User user, @RequestBody Page page) {

        Map<String, Object> resObj = pageService.addPage(user, page);

        return new ResponseEntity<>(resObj,
                resObj.get("success").equals(true) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    /**
     * Delete Page
     */
    @DeleteMapping("/pages")
    public ResponseEntity<?> deletePage(@RequestAttribute("auth_user") User user, @RequestBody Page page) {

        Map<String, Object> resObj = pageService.deletePage(user, page);

        return new ResponseEntity<>(resObj,
                resObj.get("success").equals(true) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}