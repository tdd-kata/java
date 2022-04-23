package org.xpdojo.designpatterns._03_behavioral_patterns._08_state.school;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OnlineCourseTest {

    @Test
    void sut_online_course() {
        OnlineCourse onlineCourse = new OnlineCourse();
        Student student1 = new Student("whiteship");
        Student student2 = new Student("markruler");
        student2.addPrivate(onlineCourse);

        onlineCourse.addStudent(student1);
        assertThat(onlineCourse.getStudents()).containsExactly(student1);

        onlineCourse.changeState(new Private(onlineCourse));
        assertThat(onlineCourse.getState()).isInstanceOf(Private.class);

        onlineCourse.addReview("hello", student1);
        assertThat(onlineCourse.getReviews()).containsExactly("hello");
        assertThatThrownBy(() -> onlineCourse.addReview("hello", student2))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("프라이빗 코스를 수강하는 학생만 리뷰를 남길 수 있습니다.");

        onlineCourse.addStudent(student2);
        assertThat(onlineCourse.getStudents()).containsExactly(student1, student2);
    }

}
