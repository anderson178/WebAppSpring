package ru.app.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "person")
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
    //TODO негенит Id сам, поправить
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "middle_name")
    String middleName;
    @Column(name = "birth_date")
    Timestamp birthDate;
    @Column(name = "comment")
    String comment;
    @Column(name = "update_date")
    Timestamp updateDate;

    public Person(int id, String lastName, String firstName, String middleName, Timestamp birthDate) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
    }
}
