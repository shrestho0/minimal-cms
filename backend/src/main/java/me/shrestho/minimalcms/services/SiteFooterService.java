package me.shrestho.minimalcms.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.entity.SiteFooter;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.SiteFooterRepository;

@Service

public class SiteFooterService {
    @Autowired
    SiteFooterRepository siteFooterRepository;

    public void addSiteFooter(User user) throws Exception {
        // create profile
        SiteFooter siteFooter = new SiteFooter();
        siteFooter.setId(UUID.randomUUID().toString());
        // siteFooter.setUser(user);
        siteFooter.setUserId(user.getId());

        siteFooter.setText(user.getName() + "'s MCMS Site");

        siteFooter.setSocial_json(
                "[{\"title\":\"Github\",\"href\":\"https://github.com/\",\"fa_icon\":\"fa fa-github\"},{\"title\":\"Linkedin\",\"href\":\"https://linkedin.com/\",\"fa_icon\":\"fa fa-linkedin\"}]");

        siteFooterRepository.save(siteFooter);

    }

    public SiteFooter getSiteFooter(User user) {
        // get profile
        try {
            SiteFooter siteFooter = siteFooterRepository.findByUserId(user.getId());
            // siteFooter.getUser().setPasswordHash(null);
            return siteFooter;

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
    public Map<String, Object> updateSiteFooter(User user, SiteFooter siteFooter) {

        Map<String, Object> resObj = null;
        try {
            SiteFooter existingsiteFooter = siteFooterRepository.findByUserId(user.getId());

            if (existingsiteFooter == null) {
                addSiteFooter(user);
                existingsiteFooter = siteFooterRepository.findByUserId(user.getId());
            }

            if (siteFooter.getText() != null) {
                existingsiteFooter.setText(siteFooter.getText());
            }
            if (siteFooter.getSocial_json() != null) {
                existingsiteFooter.setSocial_json(siteFooter.getSocial_json());
            }

            siteFooterRepository.save(existingsiteFooter);

            resObj = Map.of("success", true, "message", "Site Footer updated successfully");
        } catch (Exception e) {
            resObj = Map.of("success", false, "message", "Site Footer update failed");
        }
        return resObj;
    }

    public void deleteSiteFooterByUser(User user) throws Exception {
        SiteFooter siteFooter = siteFooterRepository.findByUserId(user.getId());
        siteFooterRepository.delete(siteFooter);
    }

}