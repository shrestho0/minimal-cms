package me.shrestho.minimalcms.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.entity.SiteStyle;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.SiteStyleRepository;

@Service
public class SiteStyleService {
    @Autowired
    SiteStyleRepository siteStyleRepository;

    public void addSiteStyle(User user) throws Exception {
        // create profile
        SiteStyle siteStyle = new SiteStyle();
        siteStyle.setId(UUID.randomUUID().toString());
        // siteStyle.setUser(user);
        siteStyle.setUserId(user.getId());

        siteStyle.setFontFamily("Poppins");
        siteStyle.setFontLoadUrl("https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap");
        siteStyle.setStyleJson("{}");

        System.out.println("siteStyle: " + siteStyle);
        System.out.println("===================\n\n");

        siteStyleRepository.save(siteStyle);

    }

    public SiteStyle getSiteStyle(User user) {
        // get profile
        try {
            SiteStyle siteStyle = siteStyleRepository.findByUserId(user.getId());
            // siteStyle.getUser().setPasswordHash(null);
            return siteStyle;

        } catch (Exception e) {
            // handle exception
        }
        return null;
    }

    /**
     * 
     * @param user
     * @param profile
     * @return Map<String, Object>
     */
    public Map<String, Object> updateSiteStyle(User user, SiteStyle siteStyle) {

        Map<String, Object> resObj = null;
        try {
            SiteStyle existingsiteStyle = siteStyleRepository.findByUserId(user.getId());

            if (existingsiteStyle == null) {
                addSiteStyle(user);
                existingsiteStyle = siteStyleRepository.findByUserId(user.getId());
            }

            System.out.print("\n\n=================== Style Update ===================\n\n");
            System.out.println("existingsiteStyle: " + existingsiteStyle);
            System.out.println("siteStyle: " + siteStyle);

            if (siteStyle.getFontFamily() != null) {
                existingsiteStyle.setFontFamily(siteStyle.getFontFamily());
            }

            if (siteStyle.getFontLoadUrl() != null) {
                existingsiteStyle.setFontLoadUrl(siteStyle.getFontLoadUrl());
            }

            if (siteStyle.getStyleJson() != null) {
                existingsiteStyle.setStyleJson(siteStyle.getStyleJson());
            }

            siteStyleRepository.save(existingsiteStyle);

            resObj = Map.of("success", true, "message", "Site Style updated successfully", "siteStyle",
                    existingsiteStyle);
        } catch (Exception e) {
            resObj = Map.of("success", false, "message", "Site Style update failed");
        }
        return resObj;
    }

    public void deleteSiteStyleByUser(User user) throws Exception {

        SiteStyle siteStyle = siteStyleRepository.findByUserId(user.getId());
        siteStyleRepository.delete(siteStyle);
    }

}