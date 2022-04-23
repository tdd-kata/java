package org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media;

import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.iterator.FacebookIterator;
import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.iterator.ProfileIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Facebook implements SocialMedia {
    private List<Profile> profiles;

    public Facebook(List<Profile> cache) {
        this.profiles = Objects.requireNonNullElseGet(cache, ArrayList::new);
    }

    public Profile requestProfileFromFacebook(String profileEmail) {
        // EN: Here would be a POST request to one of the Facebook API
        // endpoints. Instead, we emulates long network connection, which you
        // would expect in the real life...

        // simulateNetworkLatency();
        System.out.println("Facebook: Loading profile '" + profileEmail + "' over the network...");

        // EN: ...and return test data.
        return findProfile(profileEmail);
    }

    public List<String> requestProfileFriendsFromFacebook(String profileEmail, String contactType) {
        // EN: Here would be a POST request to one of the Facebook API
        // endpoints. Instead, we emulates long network connection, which you
        // would expect in the real life...

        // simulateNetworkLatency();
        System.out.println("Facebook: Loading '" + contactType + "' list of '" + profileEmail + "' over the network...");

        // EN: ...and return test data.
        //
        // RU: ...и возвращаем тестовые данные.
        Profile profile = findProfile(profileEmail);
        if (profile != null) {
            return profile.getContacts(contactType);
        }
        return null;
    }

    private Profile findProfile(String profileEmail) {
        for (Profile profile : profiles) {
            if (profile.getEmail().equals(profileEmail)) {
                return profile;
            }
        }
        return null;
    }

    private void simulateNetworkLatency() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ProfileIterator createFriendsIterator(String profileEmail) {
        return new FacebookIterator(this, "friends", profileEmail);
    }

    @Override
    public ProfileIterator createCoworkersIterator(String profileEmail) {
        return new FacebookIterator(this, "coworkers", profileEmail);
    }
}
