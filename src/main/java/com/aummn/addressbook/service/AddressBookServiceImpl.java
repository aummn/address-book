package com.aummn.addressbook.service;

import com.aummn.addressbook.model.AddressBookRecord;
import com.aummn.addressbook.model.Contact;
import com.aummn.addressbook.repo.AddressBookRepositoryImpl;

import java.util.List;
import java.util.Optional;
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
        AddressBookRecord savedRecord = repo.saveRecord(record);
        return new Contact(savedRecord.getId(),savedRecord.getName(), savedRecord.getPhone());
    }

    public List<Contact> addContacts(List<Contact> contacts, long addressBookId) {
        if(contacts == null) throw new IllegalArgumentException("contacts is required");

        // build the list of AddressBookRecord objects
        List<AddressBookRecord> records = contacts.stream()
                .map(contact ->
                        new AddressBookRecord(contact.getName(), contact.getPhone(), addressBookId))
                .collect(Collectors.toList());

        // saveRecord records to the address book
        return repo.saveRecords(records).stream()
                .map(record -> new Contact(record.getId(), record.getName(), record.getPhone()))
                .collect(Collectors.toList());
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

    public void removeContact(Contact contact) {
        if(contact == null) throw new IllegalArgumentException("contact is required");
        repo.removeRecord(contact.getId());

    }

    public Optional<Contact> findContact(Contact contact) {
        if(contact == null) throw new IllegalArgumentException("contact is required");
        return repo.findRecordById(contact.getId())
                .map(r -> Optional.of(new Contact(r.getId(), r.getName(), r.getPhone())))
                .orElse(Optional.empty());
    }
}
