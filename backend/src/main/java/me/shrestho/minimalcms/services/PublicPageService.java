package me.shrestho.minimalcms.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.entity.Page;
import me.shrestho.minimalcms.entity.SiteFooter;
import me.shrestho.minimalcms.entity.SiteHeader;
import me.shrestho.minimalcms.entity.SiteStyle;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.PageRepository;
import me.shrestho.minimalcms.repository.SiteFooterRepository;
import me.shrestho.minimalcms.repository.SiteHeaderRepository;
import me.shrestho.minimalcms.repository.SiteStyleRepository;
import me.shrestho.minimalcms.repository.UserRepository;

@Service
public class PublicPageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private SiteHeaderRepository siteHeaderRepository;

    @Autowired
    private SiteFooterRepository siteFooterRepository;

    @Autowired
    private SiteStyleRepository siteStyleRepository;

    public Map<String, Object> getPage(User user, String pageSlug) {
        Map<String, Object> resObj = new HashMap<String, Object>();

        Page page = pageRepository.findByUserAndSlug(user, pageSlug);

        if (page != null) {
            page.setUser(null);
            resObj.put("success", true);
            user.setPasswordHash(null); // to hide password hash
            resObj.put("owner", user);
            resObj.put("page", page);
        } else {
            resObj.put("success", false);
            resObj.put("message", "Page not found");
        }

        return resObj;
    }

    public Map<String, Object> getStuff(User user) {
        Map<String, Object> resObj = new HashMap<String, Object>();

        // find user by username
        // find stuff with user

        user.setPasswordHash(null);
        resObj.put("success", true);
        // Find Header
        SiteHeader siteHeader = siteHeaderRepository.findByUserId(user.getId());
        // siteHeader.setUser(null);

        SiteFooter siteFooter = siteFooterRepository.findByUserId(user.getId());
        // siteFooter.setUser(null);
        resObj.put("siteHeader", siteHeader);
        resObj.put("siteFooter", siteFooter);

        return resObj;
    }

    public Map<String, Object> getDesign(User user) {
        Map<String, Object> resObj = new HashMap<String, Object>();

        // find user by username
        // find stuff with user

        user.setPasswordHash(null);
        resObj.put("success", true);
        // Find Style
        SiteStyle siteStyle = siteStyleRepository.findByUserId(user.getId());
        // siteStyle.setUser(null);
        System.out.println("siteStyle: " + siteStyle);
        resObj.put("siteStyle", siteStyle);

        return resObj;
    }

}
