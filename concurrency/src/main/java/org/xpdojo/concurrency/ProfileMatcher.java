package org.xpdojo.concurrency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ProfileMatcher {
    private Map<String, Profile> profiles = new HashMap<>();
    private static final int DEFAULT_POOL_SIZE = 4;

    public void add(Profile profile) {
        profiles.put(profile.getId(), profile);
    }

    private final ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);

    ExecutorService getExecutor() {
        return executor;
    }

    public void findMatchingProfiles(
            MatchListener listener,
            List<MatchSet> matchSets,
            BiConsumer<MatchListener, MatchSet> processFunction) {

        for (MatchSet set : matchSets) {
            Runnable runnable = () -> processFunction.accept(listener, set);
            executor.execute(runnable);
        }
        executor.shutdown();
    }

    List<MatchSet> collectMatchSets(Criteria criteria) {
        return profiles.values().stream()
                .map(profile -> profile.getMatchSet(criteria))
                .collect(Collectors.toList());
    }

    void process(MatchListener listener, MatchSet set) {
        if (set.matches())
            listener.foundMatch(profiles.get(set.getProfileId()), set);
    }
}
