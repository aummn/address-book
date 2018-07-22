package com.aummn.addressbook.service;

import com.aummn.addressbook.model.AddressBookRecord;
import com.aummn.addressbook.model.Contact;
import com.aummn.addressbook.repo.AddressBookRepositoryImpl;

/**
 * This class is designed for adding contacts, removing contacts, printing contacts from address books.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public class AddressBookServiceImpl {

    AddressBookRepositoryImpl repo = new AddressBookRepositoryImpl();

    public AddressBookServiceImpl(AddressBookRepositoryImpl repo) {
        this.repo = repo;
    }

    public Contact addContact(Contact c, long addressBookId) {
        AddressBookRecord record = new AddressBookRecord(c.getId(),c.getName(),c.getPhone(), addressBookId);
        AddressBookRecord savedRecord = repo.save(record);
        return new Contact(savedRecord.getId(),savedRecord.getName(), savedRecord.getPhone());
    }
}
