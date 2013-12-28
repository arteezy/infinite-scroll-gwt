package com.hiringtask.server.model;

import javax.persistence.*;

@Entity
@Table(name = "Dude",
        indexes = {
        @Index(name = "fNameIndex", columnList = "fName"), // JPA2.1 syntax rocks!
        @Index(name = "lNameIndex", columnList = "lName"),
})
public class Dude {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fName")
    private String firstName;

    @Column(name = "lName")
    private String lastName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
