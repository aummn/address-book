package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;
import java.util.List;
import java.util.Optional;

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
    AddressBookRecord saveRecord(AddressBookRecord record);

    /**
     * Remove a record.
     *
     * @param id the id of an address book entry
     *
     */
    Optional<AddressBookRecord> removeRecord(long id);

    /**
     * Find an address book entry by id.
     *
     * @param id the id of an address book entry
     *
     */
    Optional<AddressBookRecord> findRecordById(Long id);

    /**
     * Find a list of an address book entries by id, or name, or phone
     *
     * @param searchString the string containing id, or name, or phone
     *
     */
    List<AddressBookRecord> findRecord(String searchString);

    /**
     * Find all records from an address book.
     *
     * @param addressBookId the id of an address book
     *
     */
    List<AddressBookRecord> findAllRecordsByAbid(long addressBookId);

    /**
     * Find all records from multiple address books.
     *
     * @param addressBookIds the ids of address books
     * @return a list of AddressBookRecord objects
     */
    List<AddressBookRecord> findAllRecordsByAbids(List<Long> addressBookIds);

}
