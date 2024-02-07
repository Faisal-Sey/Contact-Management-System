package com.example.contactsystem.respository;

import com.example.contactsystem.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact c WHERE "
            + "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')) OR "
            + "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR "
            + "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR "
            + "LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Contact> searchContacts(
            String search,
            Pageable pageable
    );
}
