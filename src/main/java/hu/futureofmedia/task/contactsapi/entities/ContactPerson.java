package hu.futureofmedia.task.contactsapi.entities;

import javax.persistence.*;
import java.util.Date;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDateTime;

    @PrePersist
    public void setCreationDate() {
        creationDateTime = new Date();
    }

    @PreUpdate
    public void setUpdateTime() {
        updateDateTime = new Date();
    }
}
