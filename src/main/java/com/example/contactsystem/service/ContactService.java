package com.example.contactsystem.service;

import com.example.contactsystem.dto.CreateContactDto;
import com.example.contactsystem.dto.UpdateContactDto;
import com.example.contactsystem.model.Contact;
import com.example.contactsystem.respository.ContactRepository;
import com.example.contactsystem.utilities.CommonHelpers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ObjectMapper objectMapper;
    private final CommonHelpers<Contact> commonHelpers;
    private final Map<String, Sort.Direction> sortingTypes = new HashMap<>();


    @Autowired
    public ContactService(
            ContactRepository contactRepository,
            CommonHelpers<Contact> commonHelpers,
            ObjectMapper objectMapper
    ) {
        this.contactRepository = contactRepository;
        this.commonHelpers = commonHelpers;
        this.objectMapper = objectMapper;
        this.sortingTypes.put("asc", Sort.Direction.ASC);
        this.sortingTypes.put("desc", Sort.Direction.DESC);
    }

    public Page<Contact> getAllContacts(
            Integer page,
            Integer size,
            String search,
            String sortBy,
            String sortOrder
    ) {
        Pageable pageable;
        if (page == null && size == null) {
            pageable = Pageable.unpaged();
        } else {
            int currentPage = (page == null || page == 0) ? 1 : page - 1;
            if (sortBy != null && !sortBy.isEmpty()) {
                String currentSortBy = sortOrder == null ? "DESC" : sortOrder;
                Sort sort = Sort.by(this.sortingTypes.get(currentSortBy.toLowerCase()), sortBy);
                pageable = PageRequest.of(currentPage, size, sort);
            } else {
                pageable = PageRequest.of(currentPage, size);
            }
        }
        if (search != null) {
            return contactRepository.searchContacts(search, pageable);
        }
        return contactRepository.findAll(pageable);
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public void deleteContactById(Long id) {
        contactRepository.deleteById(id);
    }

    public Contact partialUpdateContact(Long id, UpdateContactDto contactDto) {
        Contact contact = objectMapper.convertValue(contactDto, Contact.class);
        Optional<Contact> foundContactOptional = contactRepository.findById(id);
        if (foundContactOptional.isPresent()) {
            Contact foundContact = foundContactOptional.get();
            Contact updatedContact = commonHelpers.performPartialUpdate(foundContact, contact, "id");
            return contactRepository.save(updatedContact);
        } else {
            return null;
        }
    }


    public Contact fullUpdateContact(Long id, CreateContactDto contactDto) {
        Contact contact = objectMapper.convertValue(contactDto, Contact.class);
        Optional<Contact> foundContactOptional = contactRepository.findById(id);
        contact.setId(id);
        if (foundContactOptional.isPresent()) {
            Contact foundContact = foundContactOptional.get();
            contact.setCreatedAt(foundContact.getCreatedAt());
        }
        return contactRepository.save(contact);
    }
}
