package com.aummn.addressbook.service;

import com.aummn.addressbook.model.Contact;

/**
 * An interface for adding contacts, removing contacts, printing contacts from address books.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public interface AddressBookService {
    Contact addContact(Contact c, long addressBookId);
}
