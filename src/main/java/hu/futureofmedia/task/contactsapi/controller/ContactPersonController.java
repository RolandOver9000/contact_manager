package hu.futureofmedia.task.contactsapi.controller;

import hu.futureofmedia.task.contactsapi.model.dto.IncomingContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.dto.OutgoingDetailedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.dto.OutgoingListedContactPersonDto;
import hu.futureofmedia.task.contactsapi.service.ContactPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "*")
public class ContactPersonController {

    @Autowired
    private ContactPersonService contactPersonService;

    @GetMapping("/active")
    public ResponseEntity<List<OutgoingListedContactPersonDto>> getAllActiveContactPersonsByPage(
                                                        @RequestParam("page") int pageNumber) {
        try {
            return ResponseEntity.ok(contactPersonService
                    .getAllActiveContactPersonAscendingByFirstNameByPage(pageNumber));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error during getting active contacts on page: " + pageNumber);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<OutgoingDetailedContactPersonDto> getContactPersonById(
            @PathVariable(value="email") String email) {
        try {
            return ResponseEntity.ok(contactPersonService.getContactPersonById(email));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error during getting contact by email: " + email);
        }
    }

    @PostMapping("")
    public ResponseEntity<String> saveContactPerson(@RequestBody IncomingContactPersonDto contactPersonDto) {
        try {
            contactPersonService.saveContactPerson(contactPersonDto);
            return ResponseEntity.ok("Contact person saved successfully.");
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error during saving contact.");
        }
    }

}
