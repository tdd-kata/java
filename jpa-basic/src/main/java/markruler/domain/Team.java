package markruler.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") // MemberSequence에서 필드명
    private List<MemberSequence> members
            = new ArrayList<>(); // add 할 때 NPE를 피하기 위해 초기화

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MemberSequence> getMembers() {
        return members;
    }

    public void setMembers(List<MemberSequence> members) {
        this.members = members;
    }
}
