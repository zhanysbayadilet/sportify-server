package kz.sportify.administrationservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(	name = "_city",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "kk_name"),
                @UniqueConstraint(columnNames = "ru_name"),
                @UniqueConstraint(columnNames = "en_name")
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "kk_name")
    private String kkName;

    @Column(name = "ru_name")
    private String ruName;

    @Column(name = "en_name")
    private String enName;
}
