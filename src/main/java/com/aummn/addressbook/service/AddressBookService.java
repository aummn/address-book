package com.aummn.addressbook.service;

import com.aummn.addressbook.model.Contact;
import java.util.List;
import java.util.Optional;

/**
 * An interface for adding contacts, removing contacts, printing contacts from address books.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public interface AddressBookService {

    /**
     * Add a contact into an address book.
     *
     * @param contact a contact entry with name and phone
     * @param addressBookId the id of an address book
     * @return a Contact object
     */
    Contact addContact(Contact contact, long addressBookId);

    /**
     * Remove a contact.
     *
     * @param contact a contact entry with id, name and phone
     * @return an Optional object
     *
     */
    Optional<Contact> removeContact(Contact contact);

    /**
     * Find a contact.
     *
     * @param contact a contact entry with id, name and phone
     * @return an Optional object
     *
     */
    Optional<Contact> findContact(Contact contact);


    /**
     * Find a list of contacts.
     *
     * @param searchString a string containing id, or name, or phone
     * @return a list of Contact object
     *
     */
    List<Contact> findContact(String searchString);

    /**
     * Find all contacts of an address book.
     *
     * @param addressBookId the id of an address book
     * @return a list of Contact objects
     *
     */
    List<Contact> printContacts(Long addressBookId);

    /**
     * Find all contacts from multiple address books.
     *
     * @param addressBookIds the ids of address books
     * @return a list of Contact objects
     *
     */
    List<Contact> printContacts(List<Long> addressBookIds);

    /**
     * Find all unique contacts from multiple address books.
     *
     * @param addressBookIds the ids of address books
     * @return a list of Contact objects
     *
     */
    List<Contact> printUniqueContacts(List<Long> addressBookIds);


}
