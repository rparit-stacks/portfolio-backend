package com.rps.Portfolio.controller;

import com.rps.Portfolio.dto.ContactDto;
import com.rps.Portfolio.dto.CreateContactRequest;
import com.rps.Portfolio.dto.UpdateContactStatusRequest;
import com.rps.Portfolio.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    
    @Autowired
    private ContactService contactService;
    
    @PostMapping
    public ResponseEntity<ContactDto> createContact(@RequestBody CreateContactRequest request) {
        try {
            ContactDto createdContact = contactService.createContact(request);
            return ResponseEntity.ok(createdContact);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactDto>> getAllContacts() {
        List<ContactDto> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactDto>> getContactsByStatus(@PathVariable String status) {
        try {
            List<ContactDto> contacts = contactService.getContactsByStatus(status);
            return ResponseEntity.ok(contacts);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactDto>> getNewContacts() {
        List<ContactDto> contacts = contactService.getNewContacts();
        return ResponseEntity.ok(contacts);
    }
    
    @GetMapping("/{contactId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long contactId) {
        try {
            ContactDto contact = contactService.getContactById(contactId);
            return ResponseEntity.ok(contact);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{contactId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactDto> updateContactStatus(@PathVariable Long contactId, @RequestBody UpdateContactStatusRequest request) {
        try {
            ContactDto updatedContact = contactService.updateContactStatus(contactId, request);
            return ResponseEntity.ok(updatedContact);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{contactId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteContact(@PathVariable Long contactId) {
        try {
            contactService.deleteContact(contactId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/count/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getContactCountByStatus(@PathVariable String status) {
        try {
            long count = contactService.getContactCountByStatus(status);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/count/recent/{days}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getRecentContactCount(@PathVariable int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        long count = contactService.getContactCountSince(since);
        return ResponseEntity.ok(count);
    }
}