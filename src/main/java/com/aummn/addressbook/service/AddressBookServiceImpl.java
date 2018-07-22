package com.aummn.addressbook.service;

import com.aummn.addressbook.model.AddressBookRecord;
import com.aummn.addressbook.model.Contact;
import com.aummn.addressbook.repo.AddressBookRepositoryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is designed for adding contacts, removing contacts, printing contacts from address books.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public class AddressBookServiceImpl {

    private AddressBookRepositoryImpl repo = null;

    public AddressBookServiceImpl(AddressBookRepositoryImpl repo) {
        this.repo = repo;
    }

    public Contact addContact(Contact c, long addressBookId) {
        if(c == null) throw new IllegalArgumentException("contact is required");
        AddressBookRecord record = new AddressBookRecord(c.getName(),c.getPhone(), addressBookId);
        AddressBookRecord savedRecord = repo.save(record);
        return new Contact(savedRecord.getId(),savedRecord.getName(), savedRecord.getPhone());
    }

    public List<Contact> addContacts(List<Contact> contacts, long addressBookId) {
        if(contacts == null) throw new IllegalArgumentException("contacts is required");
        List contactList = contacts.stream()
                .map(contact -> this.addContact(contact, addressBookId))
                .collect(Collectors.toList());
        return contactList;
    }

    public List<Contact> addContacts(List<Contact> contacts, List<Long> addressBookIds) {
        if(contacts == null) throw new IllegalArgumentException("contacts is required");
        if(addressBookIds == null) throw new IllegalArgumentException("address book ids is required");

        List<Contact> contactList = addressBookIds.stream()
                .map(addressBookId -> this.addContacts(contacts, addressBookId))
                .flatMap(x-> x.stream())
                .collect(Collectors.toList());

        return contactList;
    }

}
