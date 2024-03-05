package me.shrestho.minimalcms.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.shrestho.minimalcms.entity.Profile;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.repository.ProfileRepository;
import me.shrestho.minimalcms.repository.UserRepository;

@Service

public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserRepository userRepository;

    public void addProfile(User user) throws Exception {
        // create profile
        Profile profile = new Profile();
        profile.setId(UUID.randomUUID().toString());
        // profile.setUser(user);
        profile.setUserId(user.getId());

        profile.setTitle("{{name}} 's Profile");
        profile.setContent("Welcome to {{name}}'s MCMS Profile!");

        profileRepository.save(profile);

    }

    public Profile getProfile(User user) {
        // get profile
        try {
            Profile profile = profileRepository.findByUserId(user.getId());
            // profile.getUser().setPasswordHash(null);
            return profile;

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
    public Map<String, Object> updateProfile(User user, Profile profile) {

        Map<String, Object> resObj = null;
        try {
            Profile existingProfile = profileRepository.findByUserId(user.getId());
            existingProfile.setTitle(profile.getTitle());
            existingProfile.setContent(profile.getContent());
            profileRepository.save(existingProfile);
            resObj = Map.of("success", true, "message", "Profile updated successfully");
        } catch (Exception e) {
            resObj = Map.of("success", false, "message", "Profile update failed");
        }
        return resObj;
    }

    public void deleteProfileByUser(User user) throws Exception {
        Profile profile = profileRepository.findByUserId(user.getId());
        profileRepository.delete(profile);
    }

}