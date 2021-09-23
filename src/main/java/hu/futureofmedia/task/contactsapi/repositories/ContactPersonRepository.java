package hu.futureofmedia.task.contactsapi.repositories;

import hu.futureofmedia.task.contactsapi.model.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.model.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactPersonRepository extends PagingAndSortingRepository<ContactPerson, Long> {

    Page<ContactPerson> findAllByStatus(Status status, Pageable pageable);

    Optional<ContactPerson> findByEmail(String email);
}
