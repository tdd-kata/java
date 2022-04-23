package org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.iterator.ProfileIterator;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SocialMediaTest {

    @Test
    void sut_profile_iterator() {
        SocialMedia facebook = new Facebook(createTestProfiles());
        ProfileIterator coworkersIterator = facebook.createCoworkersIterator("anna.smith@bing.com");
        assertThat(coworkersIterator.getNext().getName()).isEqualTo("Sam Kitting");
        assertThat(coworkersIterator.hasNext()).isFalse();

        SocialMedia linkedIn = new LinkedIn(createTestProfiles());
        ProfileIterator friendsIterator = linkedIn.createFriendsIterator("anna.smith@bing.com");
        assertThat(friendsIterator.getNext().getName()).isEqualTo("Maximilian");
        assertThat(friendsIterator.hasNext()).isTrue();
        assertThat(friendsIterator.getNext().getName()).isEqualTo("Liza");
    }

    public static List<Profile> createTestProfiles() {
        List<Profile> data = new ArrayList<>();
        data.add(new Profile("anna.smith@bing.com", "Anna Smith", "friends:mad_max@ya.com", "friends:catwoman@yahoo.com", "coworkers:sam@amazon.com"));
        data.add(new Profile("mad_max@ya.com", "Maximilian", "friends:anna.smith@bing.com", "coworkers:sam@amazon.com"));
        data.add(new Profile("bill@microsoft.eu", "Billie", "coworkers:avanger@ukr.net"));
        data.add(new Profile("avanger@ukr.net", "John Day", "coworkers:bill@microsoft.eu"));
        data.add(new Profile("sam@amazon.com", "Sam Kitting", "coworkers:anna.smith@bing.com", "coworkers:mad_max@ya.com", "friends:catwoman@yahoo.com"));
        data.add(new Profile("catwoman@yahoo.com", "Liza", "friends:anna.smith@bing.com", "friends:sam@amazon.com"));
        return data;
    }
}
