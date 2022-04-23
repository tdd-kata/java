package org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.spam;

import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.Profile;
import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.SocialMedia;
import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.iterator.ProfileIterator;

public class SocialSpammer {
    public SocialMedia media;
    public ProfileIterator iterator;

    public SocialSpammer(SocialMedia media) {
        this.media = media;
    }

    public void sendSpamToFriends(String profileEmail, String message) {
        System.out.println("\nIterating over friends...\n");
        iterator = media.createFriendsIterator(profileEmail);
        while (iterator.hasNext()) {
            Profile profile = iterator.getNext();
            sendMessage(profile.getEmail(), message);
        }
    }

    public void sendSpamToCoworkers(String profileEmail, String message) {
        System.out.println("\nIterating over coworkers...\n");
        iterator = media.createCoworkersIterator(profileEmail);
        while (iterator.hasNext()) {
            Profile profile = iterator.getNext();
            sendMessage(profile.getEmail(), message);
        }
    }

    public void sendMessage(String email, String message) {
        System.out.println("Sent message to: '" + email + "'. Message body: '" + message + "'");
    }
}
