package com.rps.Portfolio.service;

import com.rps.Portfolio.dto.ContactDto;
import com.rps.Portfolio.dto.CreateContactRequest;
import com.rps.Portfolio.dto.UpdateContactStatusRequest;
import com.rps.Portfolio.entity.Contact;
import com.rps.Portfolio.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    public List<ContactDto> getAllContacts() {
        List<Contact> contacts = contactRepository.findAllByOrderByCreatedAtDesc();
        List<ContactDto> contactDtos = new ArrayList<>();
        
        for (Contact contact : contacts) {
            ContactDto dto = convertToDto(contact);
            contactDtos.add(dto);
        }
        
        return contactDtos;
    }
    
    public List<ContactDto> getContactsByStatus(String status) {
        Contact.ContactStatus contactStatus = Contact.ContactStatus.valueOf(status.toUpperCase());
        List<Contact> contacts = contactRepository.findByStatusOrderByCreatedAtDesc(contactStatus);
        List<ContactDto> contactDtos = new ArrayList<>();
        
        for (Contact contact : contacts) {
            ContactDto dto = convertToDto(contact);
            contactDtos.add(dto);
        }
        
        return contactDtos;
    }
    
    public List<ContactDto> getNewContacts() {
        List<Contact> contacts = contactRepository.findNewContacts();
        List<ContactDto> contactDtos = new ArrayList<>();
        
        for (Contact contact : contacts) {
            ContactDto dto = convertToDto(contact);
            contactDtos.add(dto);
        }
        
        return contactDtos;
    }
    
    public List<ContactDto> getContactsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Contact> contacts = contactRepository.findByCreatedAtBetween(startDate, endDate);
        List<ContactDto> contactDtos = new ArrayList<>();
        
        for (Contact contact : contacts) {
            ContactDto dto = convertToDto(contact);
            contactDtos.add(dto);
        }
        
        return contactDtos;
    }
    
    public ContactDto getContactById(Long contactId) {
        Optional<Contact> contactOpt = contactRepository.findById(contactId);
        if (contactOpt.isPresent()) {
            return convertToDto(contactOpt.get());
        }
        throw new RuntimeException("Contact not found with id: " + contactId);
    }
    
    public ContactDto createContact(CreateContactRequest request) {
        Contact contact = new Contact();
        
        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setCompany(request.getCompany());
        contact.setSubject(request.getSubject());
        contact.setMessage(request.getMessage());
        
        Contact savedContact = contactRepository.save(contact);
        return convertToDto(savedContact);
    }
    
    public ContactDto updateContactStatus(Long contactId, UpdateContactStatusRequest request) {
        Optional<Contact> contactOpt = contactRepository.findById(contactId);
        if (!contactOpt.isPresent()) {
            throw new RuntimeException("Contact not found with id: " + contactId);
        }
        
        Contact contact = contactOpt.get();
        
        if (request.getStatus() != null) {
            contact.setStatus(Contact.ContactStatus.valueOf(request.getStatus().toUpperCase()));
        }
        
        Contact updatedContact = contactRepository.save(contact);
        return convertToDto(updatedContact);
    }
    
    public void deleteContact(Long contactId) {
        Optional<Contact> contactOpt = contactRepository.findById(contactId);
        if (!contactOpt.isPresent()) {
            throw new RuntimeException("Contact not found with id: " + contactId);
        }
        
        contactRepository.deleteById(contactId);
    }
    
    public long getContactCountByStatus(String status) {
        Contact.ContactStatus contactStatus = Contact.ContactStatus.valueOf(status.toUpperCase());
        return contactRepository.countByStatus(contactStatus);
    }
    
    public long getContactCountSince(LocalDateTime date) {
        return contactRepository.countContactsSince(date);
    }
    
    private ContactDto convertToDto(Contact contact) {
        ContactDto dto = new ContactDto();
        dto.setId(contact.getId());
        dto.setName(contact.getName());
        dto.setEmail(contact.getEmail());
        dto.setPhone(contact.getPhone());
        dto.setCompany(contact.getCompany());
        dto.setSubject(contact.getSubject());
        dto.setMessage(contact.getMessage());
        dto.setStatus(contact.getStatus() != null ? contact.getStatus().name() : null);
        dto.setCreatedAt(contact.getCreatedAt());
        dto.setUpdatedAt(contact.getUpdatedAt());
        return dto;
    }
}