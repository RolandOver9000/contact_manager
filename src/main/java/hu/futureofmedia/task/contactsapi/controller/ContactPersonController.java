package hu.futureofmedia.task.contactsapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "*")
public class ContactPersonController {

    @GetMapping("/active")
    public ResponseEntity<List<OutgoingContactPerson>> getAllActiveContactPersonsFromTo(@RequestParam("from") int from,
                                                                                        @RequestParam("to") int to) {
        try {
            return ResponseEntity.ok(contactPersonService.getAllActiveContactPersonFromTo(from, to));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error during getting active contacts from " + from + " - " + to);
        }
    }

}
