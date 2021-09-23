package hu.futureofmedia.task.contactsapi.model.entities;

import hu.futureofmedia.task.contactsapi.validator.annotation.ValidE164Format;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactPerson {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @ValidE164Format
    private String phoneNumber;

    @ManyToOne()
    @NotNull
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
