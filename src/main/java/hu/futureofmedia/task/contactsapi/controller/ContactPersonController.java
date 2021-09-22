package hu.futureofmedia.task.contactsapi.controller;

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

    @GetMapping("/{id}")
    public ResponseEntity<OutgoingDetailedContactPersonDto> getContactPersonById(@PathVariable(value="id") Long id) {
        try {
            return ResponseEntity.ok(contactPersonService.getContactPersonById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error during getting contact by id: " + id);
        }
    }

}
