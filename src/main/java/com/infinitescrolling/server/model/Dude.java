package com.infinitescrolling.server.model;

import javax.persistence.*;

@Entity
@Table(name = "Dude",
        indexes = { // JPA2.1 syntax sucks
        @Index(name = "fNameIndex", columnList = "fName"),
        @Index(name = "lNameIndex", columnList = "lName"),
})
public class Dude {
    @Id
    @Column(name = "id")
    private int id;

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
