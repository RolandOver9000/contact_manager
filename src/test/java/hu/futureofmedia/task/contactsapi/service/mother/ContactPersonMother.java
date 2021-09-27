package hu.futureofmedia.task.contactsapi.service.mother;

import hu.futureofmedia.task.contactsapi.model.dto.OutgoingDetailedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.dto.OutgoingListedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.model.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactPersonMother {

    @Autowired
    CompanyMother companyMother;

    public ContactPerson getDeletedContactPerson() {
        return ContactPerson.builder()
                .firstName("TestFirstName")
                .lastName("TestLastName")
                .comment("TestComment")
                .email("testemail@gmail.com")
                .phoneNumber("+36 20 555 5555")
                .company(companyMother.getTestCompany())
                .status(Status.DELETED)
                .build();
    }

    public ContactPerson getActiveContactPersonWithFistName(String firstName) {
        return ContactPerson.builder()
                .company(companyMother.getTestCompany())
                .firstName(firstName)
                .lastName("TestLastName")
                .comment("TestComment")
                .email("testemail@gmail.com")
                .phoneNumber("+36 20 555 5555")
                .status(Status.ACTIVE)
                .build();
    }

    public ContactPerson getActiveContactPersonWithEmail(String email) {
        return ContactPerson.builder()
                .company(companyMother.getTestCompany())
                .firstName("TestFirstName")
                .lastName("TestLastName")
                .comment("TestComment")
                .email(email)
                .phoneNumber("+36 20 555 5555")
                .status(Status.ACTIVE)
                .build();
    }

    public OutgoingListedContactPersonDto transformContactPersonToOutgoingListedContactPerson(
                                                ContactPerson contactPerson) {
        return OutgoingListedContactPersonDto.builder()
                .companyName(contactPerson.getCompany().getName())
                .email(contactPerson.getEmail())
                .fullName(contactPerson.getFirstName() + " " + contactPerson.getLastName())
                .phoneNumber(contactPerson.getPhoneNumber())
                .build();
    }

    public OutgoingDetailedContactPersonDto transformToDetailedContactPerson(ContactPerson contactPerson) {
        return OutgoingDetailedContactPersonDto.builder()
                .firstName(contactPerson.getFirstName())
                .lastName(contactPerson.getLastName())
                .companyName(contactPerson.getCompany().getName())
                .email(contactPerson.getEmail())
                .phoneNumber(contactPerson.getPhoneNumber())
                .comment(contactPerson.getComment())
                .creationDateTime(contactPerson.getCreationDateTime())
                .updateDateTime(contactPerson.getUpdateDateTime())
                .build();
    }
}
