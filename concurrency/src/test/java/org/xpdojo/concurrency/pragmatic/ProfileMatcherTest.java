package org.xpdojo.concurrency.pragmatic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.xpdojo.concurrency.pragmatic.Answer;
import org.xpdojo.concurrency.pragmatic.Bool;
import org.xpdojo.concurrency.pragmatic.BooleanQuestion;
import org.xpdojo.concurrency.pragmatic.Criteria;
import org.xpdojo.concurrency.pragmatic.Criterion;
import org.xpdojo.concurrency.pragmatic.MatchListener;
import org.xpdojo.concurrency.pragmatic.MatchSet;
import org.xpdojo.concurrency.pragmatic.Profile;
import org.xpdojo.concurrency.pragmatic.ProfileMatcher;
import org.xpdojo.concurrency.pragmatic.Weight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Pragmatic Unit Testing in Java 8 with JUnit
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfileMatcherTest {
    private BooleanQuestion question;
    private Criteria criteria;
    private ProfileMatcher matcher;
    private Profile matchingProfile;
    private Profile nonMatchingProfile;

    @BeforeAll
    public void create() {
        question = new BooleanQuestion(1, "");
        criteria = new Criteria();
        criteria.add(new Criterion(matchingAnswer(), Weight.MustMatch));
        matchingProfile = createMatchingProfile("matching");
        nonMatchingProfile = createNonMatchingProfile("nonMatching");
    }

    private Profile createMatchingProfile(String name) {
        Profile profile = new Profile(name);
        profile.add(matchingAnswer());
        return profile;
    }

    private Profile createNonMatchingProfile(String name) {
        Profile profile = new Profile(name);
        profile.add(nonMatchingAnswer());
        return profile;
    }

    private Answer matchingAnswer() {
        return new Answer(question, Bool.TRUE);
    }

    private Answer nonMatchingAnswer() {
        return new Answer(question, Bool.FALSE);
    }

    @BeforeAll
    public void createMatcher() {
        matcher = new ProfileMatcher();
    }

    @Test
    @DisplayName("collectMatchSets()")
    void collectsMatchSets() {
        matcher.add(matchingProfile);
        matcher.add(nonMatchingProfile);

        Set<String> actual = matcher.collectMatchSets(criteria)
                .stream()
                .map(MatchSet::getProfileId)
                .collect(Collectors.toSet());

        assertThat(actual)
                .containsExactlyInAnyOrder(matchingProfile.getId(), nonMatchingProfile.getId());
    }

    private MatchListener listener;

    @BeforeAll
    public void createMatchListener() {
        listener = mock(MatchListener.class);
    }

    @Test
    @DisplayName("matchingProfile에 대한 matcher.process()")
    void processNotifiesListenerOnMatch() {
        // 주어진 조건에 매칭될 것으로 기대되는 Profile을 matcher 변수에 추가합니다.
        matcher.add(matchingProfile);

        // 주어진 조건 집합에 매칭되는 Profile에 대한 MatchSet 객체를 요청합니다.
        MatchSet matchSet = matchingProfile.getMatchSet(criteria);

        // mocking listener와 MatchSet 객체를 넘겨 매칭 처리를 지시합니다.
        matcher.process(listener, matchSet);

        verify(listener)
                .foundMatch(matchingProfile, matchSet);
    }

    @Test
    @DisplayName("nonMatchingProfile에 대한 matcher.process()")
    void processDoesNotNotifyListenerWhenNoMatch() {
        matcher.add(nonMatchingProfile);

        MatchSet matchSet = nonMatchingProfile.getMatchSet(criteria);

        matcher.process(listener, matchSet);

        verify(listener, never())
                .foundMatch(nonMatchingProfile, matchSet);
    }

    @Test
    @DisplayName("Multi Thread")
    void gathersMatchingProfiles() {

        // Listener가 수신하는 MatchSet 객체들의 프로파일 ID 목록을
        // 저장할 문자열 Set 객체를 생성합니다.
        Set<String> processedProfileIds = Collections.synchronizedSet(new HashSet<>());

        // 테스트용 MatchSet 객체들을 생성합니다.
        List<MatchSet> matchSets = createMatchSets(100);

        matcher.findMatchingProfiles(
                listener, // mocking
                matchSets,
                (listener, matchSet) -> {
                    // 리스너에 대한 각 콜백에서
                    // MatchSet 객체의 ProfileId를 모읍니다.
                    processedProfileIds.add(matchSet.getProfileId());
                });

        while (!matcher.getExecutor().isTerminated()) {
            // matcher에서 ExecutorService 객체를 얻어 와서
            // 모든 스레드의 실행이 완료될 때까지 반복문을 실행합니다.
        }

        Set<String> profileIds = matchSets.stream()
                .map(MatchSet::getProfileId)
                .collect(Collectors.toSet());

        assertThat(processedProfileIds)
                .isEqualTo(profileIds);
    }

    private List<MatchSet> createMatchSets(int count) {
        List<MatchSet> sets = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            sets.add(new MatchSet(String.valueOf(i), null, null));
        }

        return sets;
    }
}
