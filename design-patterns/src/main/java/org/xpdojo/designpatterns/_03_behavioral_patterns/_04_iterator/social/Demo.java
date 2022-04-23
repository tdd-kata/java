package org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social;

import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.Facebook;
import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.LinkedIn;
import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.Profile;
import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.SocialMedia;
import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.spam.SocialSpammer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Please specify social network to target spam tool (default:Facebook):");
        System.out.println("1. Facebook");
        System.out.println("2. LinkedIn");
        String choice = scanner.nextLine();

        SocialMedia media;
        if (choice.equals("2")) {
            media = new LinkedIn(createTestProfiles());
        } else {
            media = new Facebook(createTestProfiles());
        }

        SocialSpammer spammer = new SocialSpammer(media);
        spammer.sendSpamToFriends("anna.smith@bing.com",
                "Hey! This is Anna's friend Josh. Can you do me a favor and like this post [link]?");
        spammer.sendSpamToCoworkers("anna.smith@bing.com",
                "Hey! This is Anna's boss Jason. Anna told me you would be interested in [link].");
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
