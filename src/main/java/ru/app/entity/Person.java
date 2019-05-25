package ru.app.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "person")
public class Person {
    @Id
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

    public Person() {

    }
    public Person(Integer id, String lastName, String firstName, String middleName, Date birthDate) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + birthDate +
                ", comment='" + comment + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }
}
