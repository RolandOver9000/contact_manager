package hu.futureofmedia.task.contactsapi.controller;

import hu.futureofmedia.task.contactsapi.model.dto.IncomingContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.dto.OutgoingDetailedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.dto.OutgoingListedContactPersonDto;
import hu.futureofmedia.task.contactsapi.model.entities.Status;
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

    @GetMapping("")
    public ResponseEntity<List<OutgoingListedContactPersonDto>> getContactPersonsByPageByStatus(
                                                        @RequestParam("page") int pageNumber,
                                                        @RequestParam("status") Status status) {
        try {
            return ResponseEntity.ok(contactPersonService
                    .getAllContactPersonAscendingByFirstNameByPageByStatus(pageNumber, status));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error during getting active contacts on page: " + pageNumber);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutgoingDetailedContactPersonDto> getContactPersonByEmail(
            @PathVariable(value="id") long id) {
        try {
            return ResponseEntity.ok(contactPersonService.getContactPersonById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error during getting contact by id: " + id);
        }
    }

    @PostMapping("")
    public ResponseEntity<Long> saveContactPerson(@RequestBody IncomingContactPersonDto contactPersonDto) {
        try {
            return ResponseEntity.ok(contactPersonService.saveContactPerson(contactPersonDto));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error during saving contact.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateContactPerson(@RequestBody IncomingContactPersonDto contactPersonDto,
                                                      @PathVariable(value="id") long id) {
        try {
            return ResponseEntity.ok(contactPersonService.updateContactPersonById(contactPersonDto, id));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error during updating contact.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContactPersonByEmail(@PathVariable(value="id") long id) {
        try {
            contactPersonService.changeContactPersonToDeletedById(id);
            return ResponseEntity.ok("Contact person deleted successfully.");
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error during deleting contact.");
        }
    }

}
