package me.shrestho.minimalcms.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.entity.Page;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.PageRepository;
import me.shrestho.minimalcms.repository.UserRepository;
import me.shrestho.minimalcms.utils.Utils;
import me.shrestho.minimalcms.utils.enums.PageStatus;
import me.shrestho.minimalcms.utils.projections.UserPagesProjection;

@Service
public class PageService {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    UserRepository userRepository;

    public void addPage() {

    }

    public Object getPagesByUser(User user) {
        List<UserPagesProjection> items = pageRepository.findAllByUser(user);
        Map<String, Object> resObj = new HashMap<String, Object>();
        resObj.put("success", true);
        // resObj.put("totalItems", items.size());
        // resObj.put("totalPages", Math.ceil(items.size() / 10));
        resObj.put("page", 1);
        resObj.put("items", items);

        return items;
    }

    /*
     * Add Page
     * Create page for user
     */
    public Map<String, Object> addPage(User user, Page page) {
        Map<String, Object> resObj = new HashMap<String, Object>();
        resObj.put("success", false);

        Map<String, Object> errors = new HashMap<String, Object>();

        if (page.getTitle() == null || page.getTitle().isEmpty()) {
            errors.put("title", "Title is required");
        }
        if (page.getStatus() == null) {
            errors.put("status", "Status is required");
        }
        if (page.getSlug() == null || page.getSlug().isEmpty()) {
            errors.put("slug", "Slug is required");
        }
        if (page.getContent() == null || page.getContent().isEmpty()) {
            errors.put("content", "Content is required");
        }

        // find page by user and slug
        Page existingPage = pageRepository.findByUserAndSlug(user, page.getSlug());

        if (existingPage != null) {
            errors.put("slug", "Slug already exists");
        }

        // If there are errors, return them
        if (errors.size() > 0) {
            resObj.put("errors", errors);
            return resObj;
        }

        // We are safe here

        try {

            user = userRepository.getReferenceById(user.getId());
            page.setUser(user);
            page.setId(UUID.randomUUID().toString());

            Page savedPage = pageRepository.save(page);

            resObj.put("success", true);

            Map<String, Object> pageObj = new HashMap<String, Object>();
            pageObj.put("id", savedPage.getId());
            pageObj.put("title", savedPage.getTitle());
            pageObj.put("slug", savedPage.getSlug());
            pageObj.put("status", savedPage.getStatus());
            pageObj.put("content", savedPage.getContent());
            pageObj.put("created", savedPage.getCreated());
            pageObj.put("updated", savedPage.getUpdated());

            resObj.put("page", pageObj);

        } catch (Exception e) {
            resObj.put("message", e.getMessage());
            resObj.put("required", Utils.requiredPageFields);
            return resObj;
        }

        return resObj;
    }

    public Map<String, Object> deletePage(User user, Page page) {

        Map<String, Object> resObj = new HashMap<String, Object>();
        resObj.put("success", false);

        Map<String, Object> errors = new HashMap<String, Object>();

        if (page.getId() == null) {
            errors.put("id", "Id is required");
        }

        // If there are errors, return them
        if (errors.size() > 0) {
            resObj.put("errors", errors);
            return resObj;
        }

        // We are safe here

        try {
            // Check if user is the owner of the page
            Page existingPage = pageRepository.findByUserAndId(user, page.getId());

            if (existingPage == null) {
                resObj.put("message", "Page not found");
                return resObj;
            }

            pageRepository.delete(existingPage);
            resObj.put("success", true);

        } catch (Exception e) {
            resObj.put("message", e.getMessage());
            return resObj;
        }

        return resObj;

    }

    //
    public Object getPagesByUserWithFilters(User user, Integer page, Integer limit, String status, String q) {

        Map<String, Object> resObj = new HashMap<String, Object>();

        resObj.put("totalItems", 0);
        resObj.put("totalPages", 0);
        resObj.put("page", page);
        resObj.put("items", null);

        // Object items = pageRepository.findBy(user);
        // find user's pages with filters

        q = q.toLowerCase();

        if (status.equals("all")) {
            Integer count = pageRepository.countByFilter(
                    user.getId(),
                    q);

            List<UserPagesProjection> items = pageRepository.findByFilter(
                    user.getId(),
                    q,
                    // orderBy,
                    // sort,
                    (page - 1) * limit,
                    limit

            );

            System.out.println("Filters: " + user.getId() + " " + q + " " + limit + " "
                    + (page - 1) * limit);
            resObj.put("totalItems", count);
            resObj.put("items", items);
            resObj.put("totalPages", (int) Math.ceil(count / limit));

        } else if (status.equals("draft") || status.equals("published") || status.equals("banned")) {
            Integer count = pageRepository.countByFilterAndStatus(
                    user.getId(),
                    q,
                    status);

            List<UserPagesProjection> items = pageRepository.findByFilterAndStatus(
                    user.getId(),
                    q,
                    status,
                    (page - 1) * limit,
                    limit);

            resObj.put("totalItems", count);
            resObj.put("items", items);
            resObj.put("totalPages", (int) Math.ceil(count / limit));

        }

        // Count by filter

        // Items by filter

        resObj.put("perPage", limit);

        return resObj;

    }

    public Map<String, Object> getSinglePage(User user, String id) {
        Map<String, Object> resObj = new HashMap<String, Object>();

        Page page = pageRepository.findByUserAndId(user, id);
        if (page == null) {
            resObj.put("success", false);
            resObj.put("message", "Page not found");
            return resObj;
        }
        resObj.put("success", true);

        page.setUser(null);

        resObj.put("page", page);
        return resObj;
    }

    public Map<String, Object> getSinglePageBySlug(User user, String id) {
        Map<String, Object> resObj = new HashMap<String, Object>();

        Page page = pageRepository.findByUserAndSlug(user, id);
        if (page == null) {
            resObj.put("success", false);
            resObj.put("message", "Page not found");
            return resObj;
        }
        resObj.put("success", true);

        page.setUser(null);

        resObj.put("page", page);
        return resObj;
    }

    public Map<String, Object> updatePage(User user, String id, Page page) {

        Map<String, Object> resObj = new HashMap<String, Object>();

        // Get original page
        Page originalPage = pageRepository.findByUserAndId(user, id);

        if (originalPage == null) {
            resObj.put("success", false);
            resObj.put("message", "Page not found");
            return resObj;
        }

        // Banned page can't be updated

        if (originalPage.getStatus().equals(PageStatus.banned)) {
            resObj.put("success", false);
            resObj.put("message", "Page is banned");
            return resObj;
        }

        // User can not ban his own page
        if (page.getStatus().equals(PageStatus.banned)) {
            resObj.put("success", false);
            resObj.put("message", "You can not ban your own page");
            return resObj;
        }

        Map<String, Object> errors = new HashMap<String, Object>();

        // we don't need to care about slug here anymore, if any value is empty, that
        // won't be updated

        if (page.getSlug() != null && !page.getSlug().equals(originalPage.getSlug())) {
            // need to check
            Page existingPage = pageRepository.findByUserAndSlug(user, page.getSlug());
            if (existingPage != null) {
                errors.put("slug", "Slug already exists");
                resObj.put("errors", errors);
                return resObj;
            }
        }

        if (page.getTitle() != null && !page.getTitle().isEmpty()) {
            originalPage.setTitle(page.getTitle());
        }
        if (page.getSlug() != null && !page.getSlug().isEmpty()) {
            originalPage.setSlug(page.getSlug());
        }
        if (page.getStatus() != null) {
            originalPage.setStatus(page.getStatus());
        }
        if (page.getContent() != null && !page.getContent().isEmpty()) {
            originalPage.setContent(page.getContent());
        }

        try {
            Page updatedPage = pageRepository.save(originalPage);
            resObj.put("success", true);
            updatedPage.setUser(null);
            resObj.put("message", "Page updated successfully");
            resObj.put("page", updatedPage);
        } catch (Exception e) {
            resObj.put("success", false);
            resObj.put("message", e.getMessage());
        }

        return resObj;

    }

    public Object searchPagesByUser(User user, String q) {
        Map<String, Object> resObj = new HashMap<String, Object>();

        List<UserPagesProjection> items = pageRepository.searchByUserId(user.getId(), q);

        resObj.put("success", true);
        resObj.put("query", q);
        resObj.put("items", items);

        return resObj;
    }

    public List<UserPagesProjection> getLastFivePages() {

        // Return last 5 users by `created` where page role = 'user'
        return pageRepository.findTop5ByOrderByCreatedDesc();
    }

    public long getPageCount() {
        return pageRepository.count();
    }

    public long getUsersPageCount(User user) {
        return pageRepository.countByUserId(user.getId());
    }

    public long getUsersPageCountByStatus(User user, String status) {
        return pageRepository.countByUserIdAndStatus(user.getId(), status);
    }

    public List<UserPagesProjection> getLastFiveUpdatedPages(User user) {

        // Return last 5 users by `updated` where user = user.getId()
        return pageRepository.findTop5ByUserOrderByUpdatedDesc(user);

    }

    public long getTodaysPageCount() {
        return pageRepository.countByCreatedToday();
    }

    public void deletePageByUser(User user) {
        // pageRepository.deleteByUser(user);
        pageRepository.deleteAllByUser(user);
    }

    // public Map<String, Object> findPagesForAdmin(List<User> users, String
    // pageQuery, String status, int limit,
    // int page) {

    // Map<String, Object> resObj = new HashMap<String, Object>();
    // resObj.put("totalItems", 0);
    // resObj.put("totalPages", 0);
    // resObj.put("page", page);
    // resObj.put("items", null);
    // resObj.put("perPage", limit);

    // if (users == null || users.size() == 0) {
    // return resObj;
    // }

    // // Do Stuff here
    // // List<UserPagesProjection> items = pageRepository.findByUser(users,
    // pageQuery,
    // // status, limit, page);

    // return resObj;

    // }

    public Map<String, Object> findPagesForAdminWithoutUser(int page, int limit, String status, String pageQuery,
            String userQuery) {

        Map<String, Object> resObj = new HashMap<String, Object>();
        resObj.put("totalItems", 0);
        resObj.put("totalPages", 0);
        resObj.put("page", page);
        resObj.put("items", null);
        resObj.put("perPage", limit);

        // Do Stuff here
        List<Page> items = null;
        long totalItems = 0;

        if (status.equals("all") || status.equals("")) {

            if (userQuery.equals("")) {
                items = pageRepository.findByFilterForAdmin(pageQuery, (page - 1) * limit, limit);
                totalItems = pageRepository.countByFilterForAdmin(pageQuery);
            } else {
                System.out.println("\n===Searching for pages with user restriction without status" + status + " ===\n");
                totalItems = pageRepository.countByFilterAndUserIdForAdmin(userQuery, pageQuery);
                items = pageRepository.findByFilterAndUserIdForAdmin(userQuery, pageQuery, (page - 1) * limit, limit);
            }

        } else if (status.equals("draft") || status.equals("published") || status.equals("banned")) {
            if (userQuery.equals("")) {

                items = pageRepository.findByFilterAndStatusForAdmin(pageQuery, status, (page
                        - 1) * limit, limit);
                totalItems = pageRepository.countByFilterAndStatusForAdmin(pageQuery, status);
            } else {
                System.out.println("\n===Searching for pages with user restriction and status " + status + " ===\n");
                totalItems = pageRepository.countByFilterAndUserIdAndStatusForAdmin(userQuery, pageQuery, status);
                items = pageRepository.findByFilterAndUserIdAndStatusForAdmin(userQuery, pageQuery, status, (page
                        - 1) * limit, limit);
            }

        }

        if (items == null || items.size() == 0) {
            return resObj;
        }

        long totalPages = (long) Math.ceil((double) totalItems / limit);

        resObj.put("totalItems", totalItems);
        resObj.put("totalPages", totalPages);

        // remove password hash from users
        for (Page singlePage : items) {
            singlePage.getUser().setPasswordHash(null);
        }

        resObj.put("items", items);

        return resObj;

    }

    public Map<String, Object> updatePageStatusById(String pageId, Page updateTo) {
        Map<String, Object> resObj = new HashMap<>();
        resObj.put("success", false);
        // find page
        Page originalPage = pageRepository.findById(pageId).orElse(null);
        if (originalPage == null) {
            resObj.put("message", "Page not found");
            return resObj;
        }

        originalPage.setStatus(updateTo.getStatus());
        Page updatedPage = pageRepository.save(originalPage);
        resObj.put("success", true);
        resObj.put("message", "Page status updated successfully");
        resObj.put("page", updatedPage);

        return resObj;
    }

    public Map<String, Object> deletePageById(String pageId) {
        Map<String, Object> resObj = new HashMap<>();
        resObj.put("success", false);
        // find page
        Page originalPage = pageRepository.findById(pageId).orElse(null);
        if (originalPage == null) {
            resObj.put("message", "Page not found");
            return resObj;
        }

        try {
            pageRepository.delete(originalPage);
            resObj.put("success", true);
            resObj.put("message", "Page deleted successfully");
        } catch (Exception e) {
            resObj.put("message", e.getMessage());
            return resObj;
        }

        return resObj;

    }

}
