package am.task.model.entity;

import am.task.enums.UserRoleEnum;
import am.task.enums.UserStatusEnum;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AppUser")
public class User extends AbstractEntity {

    @Nationalized
    @Column(name = "FirstName")
    private String firstName;

    @Nationalized
    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "UserStatusEnum")
    private UserStatusEnum userStatusEnum;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "UserRole")
    private UserRoleEnum role;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Book> books;

}