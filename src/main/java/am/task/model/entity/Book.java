package am.task.model.entity;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Book")
public class Book extends AbstractEntity {

    @Nationalized
    @Column(name = "FoolName")
    private String foolName;

    @ManyToOne
    private User user;
}
