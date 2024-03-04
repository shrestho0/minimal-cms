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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.shrestho.minimalcms.entity.Page;
import me.shrestho.minimalcms.entity.Profile;
import me.shrestho.minimalcms.entity.SiteFooter;
import me.shrestho.minimalcms.entity.SiteHeader;
import me.shrestho.minimalcms.entity.SiteStyle;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.services.PageService;
// import me.shrestho.minimalcms.services.ProfileService;
import me.shrestho.minimalcms.services.ProfileService;
import me.shrestho.minimalcms.services.SiteFooterService;
import me.shrestho.minimalcms.services.SiteHeaderService;
import me.shrestho.minimalcms.services.SiteStyleService;
import me.shrestho.minimalcms.services.UserService;
import me.shrestho.minimalcms.utils.enums.PageStatus;
import me.shrestho.minimalcms.utils.projections.UserPagesProjection;
import me.shrestho.minimalcms.utils.projections.UserProjection;

@RestController
@RequestMapping("/user")
public class ProtectedUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PageService pageService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SiteFooterService siteFooterService;

    @Autowired
    private SiteHeaderService siteHeaderService;

    @Autowired
    private SiteStyleService siteStyleService;

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

    @GetMapping("/pages/search")
    public ResponseEntity<?> searchPages(
            @RequestAttribute("auth_user") User user,
            @RequestParam("q") String q) {

        return new ResponseEntity<>(pageService.searchPagesByUser(user, q), HttpStatus.OK);
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

        return new ResponseEntity<>(resObj, HttpStatus.OK);
        // return new ResponseEntity<>(resObj,
        // resObj.get("success").equals(true) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

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

    //////////////////////////////////
    /// Profile Section
    //////////////////////////////////

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestAttribute("auth_user") User user) {
        return new ResponseEntity<>(profileService.getProfile(user), HttpStatus.OK);
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestAttribute("auth_user") User user,
            @RequestBody Profile profile) {
        return new ResponseEntity<>(profileService.updateProfile(user, profile), HttpStatus.OK);
    }

    //////////////////////////////////
    /// Site Footer Section
    //////////////////////////////////

    @GetMapping("/site-footer")
    public ResponseEntity<?> getSiteFooter(@RequestAttribute("auth_user") User user) {
        return new ResponseEntity<>(siteFooterService.getSiteFooter(user), HttpStatus.OK);
    }

    @PatchMapping("/site-footer")
    public ResponseEntity<?> updateSiteFooter(@RequestAttribute("auth_user") User user,
            @RequestBody SiteFooter siteFooter) {
        return new ResponseEntity<>(siteFooterService.updateSiteFooter(user, siteFooter), HttpStatus.OK);
    }

    //////////////////////////////////
    /// Site Header Section
    //////////////////////////////////

    @GetMapping("/site-header")
    public ResponseEntity<?> getSiteHeader(@RequestAttribute("auth_user") User user) {
        return new ResponseEntity<>(siteHeaderService.getSiteHeader(user), HttpStatus.OK);
    }

    @PatchMapping("/site-header")
    public ResponseEntity<?> updateSiteHeader(@RequestAttribute("auth_user") User user,
            @RequestBody SiteHeader siteHeader) {
        return new ResponseEntity<>(siteHeaderService.updateSiteHeader(user, siteHeader), HttpStatus.OK);
    }

    //////////////////////////////////
    /// Site Style Section
    //////////////////////////////////

    @GetMapping("/site-style")
    public ResponseEntity<?> getSiteStyle(@RequestAttribute("auth_user") User user) {
        return new ResponseEntity<>(siteStyleService.getSiteStyle(user), HttpStatus.OK);
    }

    @PatchMapping("/site-style")
    public ResponseEntity<?> updateSiteStyle(@RequestAttribute("auth_user") User user,
            @RequestBody SiteStyle siteStyle) {
        return new ResponseEntity<>(siteStyleService.updateSiteStyle(user, siteStyle), HttpStatus.OK);
    }

    //////////////////////////////////
    /// Change Password
    //////////////////////////////////

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestAttribute("auth_user") User user,
            @RequestBody Map<String, String> reqData) {

        Map<String, Object> resObj = userService.changePassword(user, reqData);
        Boolean success = (Boolean) resObj.get("success");
        return new ResponseEntity<>(resObj, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

    }

    //////////////////////////////////
    /// Dashboard Data
    //////////////////////////////////
    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(@RequestAttribute("auth_user") User user) {

        Map<String, Object> resObj = new HashMap<String, Object>();

        List<UserPagesProjection> last5Pages = pageService.getLastFiveUpdatedPages(user);

        long totalPages = pageService.getUsersPageCount(user);

        long publishedPages = pageService.getUsersPageCountByStatus(user,
                "published");
        long draftPages = pageService.getUsersPageCountByStatus(user, "draft");
        long bannedPages = pageService.getUsersPageCountByStatus(user, "banned");

        resObj.put("last5Pages", last5Pages);

        resObj.put("totalPages", totalPages);
        resObj.put("publishedPages", publishedPages);
        resObj.put("draftPages", draftPages);
        resObj.put("bannedPages", bannedPages);

        return new ResponseEntity<>(resObj, HttpStatus.OK);

    }
}