package hu.futureofmedia.task.contactsapi.model.dto;

import hu.futureofmedia.task.contactsapi.model.entities.Company;
import hu.futureofmedia.task.contactsapi.model.entities.Status;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

@Data
@Builder
public class OutgoingDetailedContactPersonDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String comment;
    private Date creationDateTime;
    private Date updateDateTime;
}
