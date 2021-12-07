package markruler.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(
        name = "member_seq_generator",
        sequenceName = "member_seq", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 50) // DBMS의 sequence 값을 미리 지정한 수만큼 메모리에 가져온다
public class MemberSequence {

    public MemberSequence() {
    }

    public MemberSequence(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "member_seq_generator")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    // @Column(name = "team_id", nullable = false)
    // private Long teamId;

    // @ManyToOne(fetch = FetchType.EAGER) // left outer join
    @ManyToOne(fetch = FetchType.LAZY) // 필요할 때(getTeam() 사용 시) team만 select
    @JoinColumn(name = "team_id")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "MemberSequence{" +
                "id=" + id +
                ", username='" + username + '\'' +
                // ", team=" + team + // LAZY fetch
                '}';
    }
}
