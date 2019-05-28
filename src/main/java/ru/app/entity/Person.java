package ru.app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
//@SequenceGenerator(name="hibernate_sequence", initialValue=10, allocationSize=100)
public class Person {
    @Id
    //@GenericGenerator(name = "idGenerator", strategy = "increment")
   // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //@GeneratedValue( generator = "idGenerator")
    //@SequenceGenerator(name = "hibernate_sequence", sequenceName = "hibernate_sequence")
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