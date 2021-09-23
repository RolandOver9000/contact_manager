package hu.futureofmedia.task.contactsapi.repositories;

import hu.futureofmedia.task.contactsapi.model.entities.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Long> {
    List<Company> findAll();

    Optional<Company> findByName(String name);
}
