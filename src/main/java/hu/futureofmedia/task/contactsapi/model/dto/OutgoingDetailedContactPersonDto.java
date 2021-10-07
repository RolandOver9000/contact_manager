package hu.futureofmedia.task.contactsapi.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OutgoingDetailedContactPersonDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String comment;
    private Date creationDateTime;
    private Date updateDateTime;
}
