package kz.sportify.administrationservice.model;

import kz.sportify.administrationservice.model.enums.ECategoryType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(	name = "_category",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "kk_name"),
                @UniqueConstraint(columnNames = "ru_name"),
                @UniqueConstraint(columnNames = "en_name")
        })
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category {

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

        @Column(name = "img")
        private String img;

        @Column(name = "type")
        @Enumerated(EnumType.STRING)
        private ECategoryType type;

        @Column(name = "description")
        private String description;
}
