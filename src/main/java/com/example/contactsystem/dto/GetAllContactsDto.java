package com.example.contactsystem.dto;

import com.example.contactsystem.model.Contact;

import java.util.List;

public class GetAllContactsDto {
    private List<Contact> content;
    private int totalPages;
    private int totalElements;
    private int numberOfElements;

    public List<Contact> getContent() {
        return content;
    }

    public void setContent(List<Contact> content) {
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
    
    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}
