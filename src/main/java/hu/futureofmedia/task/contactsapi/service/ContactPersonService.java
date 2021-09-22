package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.model.dto.OutgoingDetailedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.dto.OutgoingListedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.model.entities.Status;
import hu.futureofmedia.task.contactsapi.repositories.ContactPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactPersonService {

    @Autowired
    private ContactPersonRepository  contactPersonRepository;
    private static final int NUMBER_OF_CONTACTS_PER_PAGE = 10;

    public List<OutgoingListedContactPersonDto> getAllActiveContactPersonAscendingByFirstNameByPage(int pageNumber) {
        Pageable sortedByFirstNameDesc =
                PageRequest.of(pageNumber, NUMBER_OF_CONTACTS_PER_PAGE, Sort.by("firstName").ascending());
        List<ContactPerson> foundContacts = contactPersonRepository
                .findAllByStatus(Status.ACTIVE, sortedByFirstNameDesc).getContent();
        return foundContacts.stream()
                .map(this::transformToListedContactPerson)
                .collect(Collectors.toList());
    }

    public OutgoingDetailedContactPersonDto getContactPersonById(Long id) {
        return contactPersonRepository
                .findById(id)
                .map(this::transformToDetailedContactPerson)
                .orElseThrow(() -> new EntityNotFoundException("Contact person not found by id: " + id));
    }

    private OutgoingListedContactPersonDto transformToListedContactPerson(ContactPerson contactPerson) {
        return OutgoingListedContactPersonDto.builder()
                .companyName(contactPerson.getCompany().getName())
                .email(contactPerson.getEmail())
                .fullName(contactPerson.getFirstName() + " " + contactPerson.getLastName())
                .phoneNumber(contactPerson.getPhoneNumber())
                .build();
    }

    private OutgoingDetailedContactPersonDto transformToDetailedContactPerson(ContactPerson contactPerson) {
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
