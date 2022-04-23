package org.xpdojo.designpatterns._03_behavioral_patterns._08_state.school;

public interface State {

    void addReview(String review, Student student);

    void addStudent(Student student);
}
