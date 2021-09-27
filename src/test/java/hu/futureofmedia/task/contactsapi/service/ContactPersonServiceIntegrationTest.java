package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.model.dto.OutgoingDetailedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.dto.OutgoingListedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.repositories.CompanyRepository;
import hu.futureofmedia.task.contactsapi.repositories.ContactPersonRepository;
import hu.futureofmedia.task.contactsapi.service.mother.CompanyMother;
import hu.futureofmedia.task.contactsapi.service.mother.ContactPersonMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ContactPersonServiceIntegrationTest {

    @Autowired
    ContactPersonService contactPersonService;

    @Autowired
    ContactPersonRepository contactPersonRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ContactPersonMother contactPersonMother;

    @Autowired
    CompanyMother companyMother;

    @Test
    public void testGetAllActiveContactPersonAscendingByFirstNameByPageShouldReturnEmptyList() {
        int pageNumber = 0;
        companyRepository.save(companyMother.getTestCompany());
        List<ContactPerson> deletedContactPersonList = new ArrayList<>(Arrays.asList(
                contactPersonMother.getDeletedContactPerson(),
                contactPersonMother.getDeletedContactPerson()
        ));
        contactPersonRepository.saveAll(deletedContactPersonList);
        contactPersonService.getAllActiveContactPersonAscendingByFirstNameByPage(pageNumber);

        List<OutgoingListedContactPersonDto> actualResultList = contactPersonService
                .getAllActiveContactPersonAscendingByFirstNameByPage(pageNumber);

        Assertions.assertEquals(Collections.emptyList(), actualResultList);
    }

    @Test
    public void testGetAllActiveContactPersonAscendingByFirstNameByPageShouldReturnTwoContactPersons() {
        int pageNumber = 0;
        companyRepository.save(companyMother.getTestCompany());
        ContactPerson firstContactPerson = contactPersonMother.getActiveContactPersonWithFistName("AAAAA");
        ContactPerson secondContactPerson = contactPersonMother.getActiveContactPersonWithFistName("BBBBB");
        List<ContactPerson> activeContactPersonList = new ArrayList<>(Arrays.asList(
                secondContactPerson, firstContactPerson));
        List<ContactPerson> deletedContactPersonList = new ArrayList<>(Arrays.asList(
                contactPersonMother.getDeletedContactPerson(),
                contactPersonMother.getDeletedContactPerson()
        ));
        List<ContactPerson> allContactPersons = new ArrayList<>();
        allContactPersons.addAll(activeContactPersonList);
        allContactPersons.addAll(deletedContactPersonList);
        contactPersonRepository.saveAll(allContactPersons);

        List<OutgoingListedContactPersonDto> expectedActiveContactPersonsInWrongOrder = activeContactPersonList
                .stream()
                .map(contactPersonMother::transformContactPersonToOutgoingListedContactPerson)
                .collect(Collectors.toList());

        List<OutgoingListedContactPersonDto> actualResultList = contactPersonService
                .getAllActiveContactPersonAscendingByFirstNameByPage(pageNumber);

        Assertions.assertEquals(2, actualResultList.size());
        Assertions.assertNotEquals(expectedActiveContactPersonsInWrongOrder, actualResultList);
    }

    @Test
    public void testGetContactPersonByEmailShouldReturnProperContactPerson() {
        String toBeFoundEmail = "validtestemail1@gmail.com";
        ContactPerson toBeFoundContactPerson = contactPersonMother.getActiveContactPersonWithEmail(toBeFoundEmail);
        companyRepository.save(companyMother.getTestCompany());
        contactPersonRepository.save(toBeFoundContactPerson);
        contactPersonRepository.save(contactPersonMother.getActiveContactPersonWithEmail("validtestemail2@gmail.com"));
        contactPersonRepository.save(contactPersonMother.getDeletedContactPerson());

        OutgoingDetailedContactPersonDto expectedContactPerson = contactPersonMother
                .transformToDetailedContactPerson(toBeFoundContactPerson);
        OutgoingDetailedContactPersonDto actualContactPerson = contactPersonService.getContactPersonByEmail(toBeFoundEmail);

        Assertions.assertEquals(expectedContactPerson, actualContactPerson);
    }

    @Test
    public void testGetContactPersonByEmailShouldReturnEntityNotFoundException() {
        String toBeFoundEmail = "validtestemail1@gmail.com";

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contactPersonService.getContactPersonByEmail(toBeFoundEmail);
        });
    }
}
