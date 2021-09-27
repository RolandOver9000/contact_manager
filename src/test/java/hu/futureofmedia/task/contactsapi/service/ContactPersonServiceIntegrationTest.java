package hu.futureofmedia.task.contactsapi.service;

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
        contactPersonService.getAllActiveContactPersonAscendingByFirstNameByPage(pageNumber);

        List<OutgoingListedContactPersonDto> actualResultList = contactPersonService
                .getAllActiveContactPersonAscendingByFirstNameByPage(pageNumber);

        Assertions.assertEquals(Collections.emptyList(), actualResultList);
    }

    @Test
    public void testGetAllActiveContactPersonAscendingByFirstNameByPageShouldReturnTwoContactPersons() {
        int pageNumber = 0;
        companyRepository.save(companyMother.getTestCompany());
        contactPersonService.getAllActiveContactPersonAscendingByFirstNameByPage(pageNumber);
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
}
