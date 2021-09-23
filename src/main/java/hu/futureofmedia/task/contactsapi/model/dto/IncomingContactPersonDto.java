package hu.futureofmedia.task.contactsapi.model.dto;

import lombok.Data;

@Data
public class IncomingContactPersonDto {

    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String phoneNumber;
    private String comment;


}
