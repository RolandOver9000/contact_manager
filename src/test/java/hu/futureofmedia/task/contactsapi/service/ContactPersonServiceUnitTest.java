package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.model.dto.OutgoingListedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.model.entities.Status;
import hu.futureofmedia.task.contactsapi.repositories.ContactPersonRepository;
import hu.futureofmedia.task.contactsapi.service.mother.ContactPersonMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@SpringBootTest
public class ContactPersonServiceUnitTest {

    @InjectMocks
    ContactPersonService contactPersonService;

    @Mock
    ContactPersonRepository contactPersonRepository;

    @Autowired
    ContactPersonMother contactPersonMother;

    @Test
    public void testGetAllActiveContactPersonAscendingByFirstNameByPageShouldReturnEmptyList() {
        int pageNumber = 0;
        int numberOfContactsPerPage = 10;
        Pageable sortedByFirstNameDesc =
                PageRequest.of(pageNumber, numberOfContactsPerPage, Sort.by("firstName").ascending());
        Page<ContactPerson> contactPersonList = Page.empty();

        Mockito.when(contactPersonRepository
                .findAllByStatus(Status.ACTIVE, sortedByFirstNameDesc)).thenReturn(contactPersonList);
        List<OutgoingListedContactPersonDto> actualResultList = contactPersonService
                .getAllActiveContactPersonAscendingByFirstNameByPage(pageNumber);

        Assertions.assertEquals(Collections.emptyList(), actualResultList);
    }

    @Test
    public void testGetAllActiveContactPersonAscendingByFirstNameByPageShouldReturnValidList() {
        int pageNumber = 0;
        int contactsPerPage = 10;
        Pageable pageableObject = PageRequest.of(pageNumber,
                contactsPerPage,
                Sort.by("firstName").ascending());
        List<ContactPerson> activeContactPersonList = new ArrayList<>(Arrays.asList(
                contactPersonMother.getActiveContactPersonWithFistName("AAAAA"),
                contactPersonMother.getActiveContactPersonWithFistName("BBBBB")
        ));
        Page<ContactPerson> contactPersonList = new PageImpl<>(activeContactPersonList);
        List<OutgoingListedContactPersonDto> expectedResultList = activeContactPersonList
                                .stream()
                                .map(contactPersonMother::transformContactPersonToOutgoingListedContactPerson)
                                .collect(Collectors.toList());

        Mockito.when(contactPersonRepository
                .findAllByStatus(Status.ACTIVE, pageableObject)).thenReturn(contactPersonList);
        List<OutgoingListedContactPersonDto> actualResultList = contactPersonService
                .getAllActiveContactPersonAscendingByFirstNameByPage(pageNumber);

        Assertions.assertEquals(expectedResultList, actualResultList);
        Assertions.assertEquals(2, actualResultList.size());
    }
}
