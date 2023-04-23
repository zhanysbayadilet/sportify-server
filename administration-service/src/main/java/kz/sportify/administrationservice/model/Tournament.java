package kz.sportify.administrationservice.model;

import kz.sportify.administrationservice.model.enums.ETournamentStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(	name = "_tournament")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "prize_fund")
    private int prizeFund;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "location")
    private String location;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ETournamentStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false, referencedColumnName = "id")
    private City city;

    @ManyToMany
    @JoinTable(
            name = "_user_tournament",
            joinColumns = @JoinColumn(name = "tournament_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "organizer_id", referencedColumnName = "id")
    private User organizer;

    public void addUser(User user) {
        if(users == null)
            users = new ArrayList<>();
        this.users.add(user);
    }
}
