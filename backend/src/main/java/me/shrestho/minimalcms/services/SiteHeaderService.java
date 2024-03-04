package me.shrestho.minimalcms.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.entity.SiteHeader;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.SiteHeaderRepository;

@Service

public class SiteHeaderService {
    @Autowired
    SiteHeaderRepository siteHeaderRepository;

    public void addSiteHeader(User user) throws Exception {
        // create profile
        SiteHeader siteHeader = new SiteHeader();
        siteHeader.setId(UUID.randomUUID().toString());
        siteHeader.setUser(user);

        siteHeader.setSite_title(user.getName() + " 's MCMS Site");
        String navJson = "[{\"title\":\"Home\",\"href\":\"/home\"},{\"title\":\"Contact\",\"href\":\"/\"},{\"title\":\"Blog\",\"href\":\"/\"}]";
        navJson = navJson.replace("/home", "/" + user.getUsername());
        siteHeader.setNav_json(navJson);
        siteHeader.setLogo("");

        System.out.println("\n\n===================");
        System.out.println("siteHeader: " + siteHeader);
        System.out.println("===================\n\n");

        siteHeaderRepository.save(siteHeader);

    }

    public SiteHeader getSiteHeader(User user) {
        // get profile
        try {
            SiteHeader siteHeader = siteHeaderRepository.findByUser(user);
            siteHeader.getUser().setPasswordHash(null);
            return siteHeader;

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
    public Map<String, Object> updateSiteHeader(User user, SiteHeader siteHeader) {

        Map<String, Object> resObj = null;
        try {
            SiteHeader existingsiteHeader = siteHeaderRepository.findByUser(user);

            if (existingsiteHeader == null) {
                addSiteHeader(user);
                existingsiteHeader = siteHeaderRepository.findByUser(user);
            }

            System.out.print("\n\n=================== Header Update ===================\n\n");
            System.out.println("existingsiteHeader: " + existingsiteHeader);
            System.out.println("siteHeader: " + siteHeader);

            if (siteHeader.getSite_title() != null) {
                existingsiteHeader.setSite_title(siteHeader.getSite_title());
            }
            if (siteHeader.getNav_json() != null) {
                existingsiteHeader.setNav_json(siteHeader.getNav_json());
            }
            if (siteHeader.getLogo() != null) {
                existingsiteHeader.setLogo(siteHeader.getLogo());
            }

            siteHeaderRepository.save(existingsiteHeader);

            resObj = Map.of("success", true, "message", "Site Header updated successfully", "siteHeader",
                    existingsiteHeader);
        } catch (Exception e) {
            resObj = Map.of("success", false, "message", "Site Header update failed");
        }
        return resObj;
    }

}