package com.example.contactsystem.controller;

import com.example.contactsystem.dto.CreateContactDto;
import com.example.contactsystem.dto.GetAllContactsDto;
import com.example.contactsystem.dto.GetContactDto;
import com.example.contactsystem.dto.UpdateContactDto;
import com.example.contactsystem.model.Contact;
import com.example.contactsystem.service.ContactService;
import com.example.contactsystem.utilities.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {
    private final ObjectMapper objectMapper;
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService, ObjectMapper objectMapper) {
        this.contactService = contactService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllContacts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {
        Page<Contact> contacts = contactService.getAllContacts(page, size, search, sortBy, sortOrder);
        GetAllContactsDto allContacts = objectMapper.convertValue(contacts, GetAllContactsDto.class);
        return ApiResponse.success("Contacts retrieved successfully", allContacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getContact(@PathVariable Long id) {
        Contact contact = contactService.getContactById(id);
        if (contact == null) {
            return ApiResponse.notFound("Contact with id " + id + " not found.");
        }
        GetContactDto contactResponse = objectMapper.convertValue(contact, GetContactDto.class);
        return ApiResponse.success("Contact retrieved successfully", contactResponse);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createContact(@RequestBody Contact contact) {
        Contact savedContact = contactService.saveContact(contact);
        return ApiResponse.created("Contact created successfully", savedContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteContact(@PathVariable Long id) {
        contactService.deleteContactById(id);
        return ApiResponse.success("Delete successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateContact(@PathVariable Long id, @RequestBody UpdateContactDto contact) {
        Contact updatedContact = contactService.partialUpdateContact(id, contact);
        if (updatedContact == null) {
            return ApiResponse.notFound("Contact with id " + id + " not found.");
        }
        return ApiResponse.success("Updated successfully", updatedContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> fullUpdateContact(@PathVariable Long id, @RequestBody CreateContactDto contact) {
        Contact updatedContact = contactService.fullUpdateContact(id, contact);
        return ApiResponse.success("Inserted successfully", updatedContact);
    }
}
