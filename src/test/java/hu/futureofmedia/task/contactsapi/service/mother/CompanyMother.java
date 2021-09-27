package hu.futureofmedia.task.contactsapi.service.mother;

import hu.futureofmedia.task.contactsapi.model.entities.Company;
import org.springframework.stereotype.Service;

@Service
public class CompanyMother {

    public Company getTestCompany() {
        Company testCompany = new Company();
        testCompany.setName("TestCompany");
        testCompany.setId(1L);
        return testCompany;
    }
}
