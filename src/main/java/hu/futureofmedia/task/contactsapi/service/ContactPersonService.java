package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.model.dto.IncomingContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.dto.OutgoingDetailedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.dto.OutgoingListedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.entities.Company;
import hu.futureofmedia.task.contactsapi.model.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.model.entities.Status;
import hu.futureofmedia.task.contactsapi.repositories.CompanyRepository;
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
    @Autowired
    private CompanyRepository companyRepository;
    private static final int NUMBER_OF_CONTACTS_PER_PAGE = 10;

    public List<OutgoingListedContactPersonDto> getAllContactPersonAscendingByFirstNameByPageByStatus(int pageNumber,
                                                                                                      Status status) {
        Pageable sortedByFirstNameDesc =
                PageRequest.of(pageNumber, NUMBER_OF_CONTACTS_PER_PAGE, Sort.by("firstName").ascending());
        List<ContactPerson> foundContacts = contactPersonRepository
                .findAllByStatus(status, sortedByFirstNameDesc).getContent();
        return foundContacts.stream()
                .map(this::transformToListedContactPerson)
                .collect(Collectors.toList());
    }

    public OutgoingDetailedContactPersonDto getContactPersonById(long id) {
        return contactPersonRepository
                .findById(id)
                .map(this::transformToDetailedContactPerson)
                .orElseThrow(() -> new EntityNotFoundException("Contact person not found by id: " + id));
    }


    public long saveContactPerson(IncomingContactPersonDto contactPersonDto) {
        Company contactCompany = companyRepository
                .findByName(contactPersonDto.getCompanyName())
                .orElseThrow(() -> new EntityNotFoundException("Company not found by name: " +
                        contactPersonDto.getCompanyName()));
        ContactPerson contactPerson = transformIncomingContactPersonToContactPerson(contactPersonDto, contactCompany);
        return contactPersonRepository.save(contactPerson).getId();
    }

    public long updateContactPersonById(IncomingContactPersonDto contactPersonDto, long id) {
        ContactPerson storedContactPerson = contactPersonRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact person not found by email: " +
                        contactPersonDto.getEmail()));

        ContactPerson updatedContactPerson = transformIncomingContactPersonToContactPerson(
                contactPersonDto, storedContactPerson.getCompany());
        updatedContactPerson.setId(storedContactPerson.getId());
        updatedContactPerson.setCreationDateTime(storedContactPerson.getCreationDateTime());
        return contactPersonRepository.save(updatedContactPerson).getId();
    }

    public void changeContactPersonToDeletedById(long id) {
        ContactPerson storedContactPerson = contactPersonRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact person not found by id: " + id));
        storedContactPerson.setStatus(Status.DELETED);
        contactPersonRepository.save(storedContactPerson);
    }

    private ContactPerson transformIncomingContactPersonToContactPerson(IncomingContactPersonDto contactPersonDto, Company contactCompany) {
        return ContactPerson.builder()
                .id(contactPersonDto.getId())
                .firstName(contactPersonDto.getFirstName())
                .lastName(contactPersonDto.getLastName())
                .comment(contactPersonDto.getComment())
                .company(contactCompany)
                .email(contactPersonDto.getEmail())
                .phoneNumber(contactPersonDto.getPhoneNumber())
                .status(Status.ACTIVE)
                .build();
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
                .id(contactPerson.getId())
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
