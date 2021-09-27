package hu.futureofmedia.task.contactsapi.service.mother;

import hu.futureofmedia.task.contactsapi.model.dto.OutgoingListedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.entities.Company;
import hu.futureofmedia.task.contactsapi.model.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.model.entities.Status;
import org.springframework.stereotype.Service;

@Service
public class ContactPersonMother {

    public ContactPerson getDeletedContactPerson() {
        return ContactPerson.builder()
                .firstName("TestFirstName")
                .lastName("TestLastName")
                .comment("TestComment")
                .email("TestEmail")
                .phoneNumber("TestPhoneNumber")
                .company(getTestCompany())
                .status(Status.DELETED)
                .build();
    }

    public ContactPerson getActiveContactPersonWithFistName(String firstName) {
        return ContactPerson.builder()
                .company(getTestCompany())
                .firstName(firstName)
                .lastName("TestLastName")
                .comment("TestComment")
                .email("TestEmail")
                .phoneNumber("TestPhoneNumber")
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

    private Company getTestCompany() {
        Company testCompany = new Company();
        testCompany.setName("TestCompany");
        return testCompany;
    }
}
