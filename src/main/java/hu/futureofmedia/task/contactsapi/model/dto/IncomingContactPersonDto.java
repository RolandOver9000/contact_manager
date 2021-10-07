package hu.futureofmedia.task.contactsapi.model.dto;

import lombok.Data;

@Data
public class IncomingContactPersonDto {

    private long id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String phoneNumber;
    private String comment;


}
