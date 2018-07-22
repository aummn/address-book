package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;

import java.util.List;

/**
 * This interface defining methods for saving, removing and retrieving contacts from the address books.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public interface AddressBookRepository {

    /**
     * Insert a record into an address book.
     *
     * @param record the address book entry
     * @return the AddressBookRecord object
     */
    AddressBookRecord save(AddressBookRecord record);

    /**
     * Insert a list of records into an address book.
     *
     * @param records the address book entries
     * @return a list of AddressBookRecord objects
     */
    List<AddressBookRecord> saveAll(List<AddressBookRecord> records);

}
