package iloveyouboss.domain;

public class Criterion implements Scoreable {
    private Weight weight;
    private Answer answer;
    private int score;

    public Criterion(Answer answer, Weight weight) {
        this.answer = answer;
        this.weight = weight;
    }

    public Answer getAnswer() { return answer; }

    public Weight getWeight() { return weight; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }
}
