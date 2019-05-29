package ru.app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Денис Мироненко
 * @version $Id$
 * @since 29.05.2019
 */

@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
    @Id
    @Column(name = "id")
    Integer id;
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

    public Person(Integer id) {
        this.id = id;
    }
}