package hu.futureofmedia.task.contactsapi.entities;

import javax.persistence.*;

@Entity
public class ContactPerson {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @OneToOne()
    private Company company;
    private String comment;
    @Enumerated(EnumType.STRING)
    private Status status;

}
