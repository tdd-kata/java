package org.xpdojo.designpatterns._01_creational_patterns._03_builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TourDirectorTest {

    private TourDirector director;
    private DefaultTourBuilder defaultTourBuilder;

    @BeforeEach
    void setUp() {
        defaultTourBuilder = new DefaultTourBuilder();
        director = new TourDirector(defaultTourBuilder);
    }

    @Test
    @DisplayName("Builder로 생성한 객체를 반환한다")
    void sut_builder() {
        TourPlan cancunTrip = director.cancunTrip();
        List<DetailPlan> plans = cancunTrip.getPlans();

        assertThat(plans)
                .map(DetailPlan::getPlan)
                .hasSize(2)
                .containsExactlyInAnyOrder("체크인하고 짐 풀기", "저녁 식사");
    }

    @Test
    @DisplayName("Builder로 생성한 객체는 온전하지 않을 수 있다")
    void sut_builder_empty_plan() {
        TourPlan longBeachTrip = director.longBeachTrip();
        List<DetailPlan> plans = longBeachTrip.getPlans();

        assertThat(plans)
                .isNullOrEmpty();
    }

}
