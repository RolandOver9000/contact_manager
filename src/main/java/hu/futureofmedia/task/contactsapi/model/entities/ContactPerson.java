package hu.futureofmedia.task.contactsapi.model.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

@Entity
@Data
public class ContactPerson {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;

    @Email
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
