package me.shrestho.minimalcms.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.entity.Page;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.PageRepository;
import me.shrestho.minimalcms.repository.UserRepository;
import me.shrestho.minimalcms.utils.Utils;
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
        Page existingPage = pageRepository.findByUserAndId(user, id);

        if (existingPage == null) {
            errors.put("id", "Page not found");
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
            page.setId(id);

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

}
