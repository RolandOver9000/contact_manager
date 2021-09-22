package hu.futureofmedia.task.contactsapi.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OutgoingContactPerson {

    private String fullName;
    private String companyName;
    private String email;
    private String phoneNumber;
}
