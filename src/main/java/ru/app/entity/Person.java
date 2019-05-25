package ru.app.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "person")
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
    @Id
   // @GeneratedValue(strategy=GenerationType.AUTO)
    @Column (name = "id", columnDefinition = "int", unique=true, nullable=false)
    Integer id;
    @Column (name = "last_name")
    String lastName;
    @Column (name = "first_name")
    String firstName;
    @Column (name = "middle_name")
    String middleName;
    @Column (name = "birth_date")
    Date birthDate;
    @Column (name = "comment")
    String comment;
    @Column (name = "update_date")
    Timestamp updateDate;

    public Person(Integer id, String lastName, String firstName, String middleName, Date birthDate) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
    }
}
