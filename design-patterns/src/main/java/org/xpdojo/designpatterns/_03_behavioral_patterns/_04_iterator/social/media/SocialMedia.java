package org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media;

import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.iterator.ProfileIterator;

public interface SocialMedia {
    ProfileIterator createFriendsIterator(String profileEmail);

    ProfileIterator createCoworkersIterator(String profileEmail);
}
