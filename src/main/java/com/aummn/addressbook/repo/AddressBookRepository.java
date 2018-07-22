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
     * Insert a list of records into an address book.
     *
     * @param records the address book entries
     * @return a list of AddressBookRecord objects
     */
    List<AddressBookRecord> saveRecords(List<AddressBookRecord> records);

    /**
     * Remove a record.
     *
     * @param id the id of an address book entry
     *
     */
    void removeRecord(Long id);


    /**
     * Find an address book entry by id.
     *
     * @param id the id of an address book entry
     *
     */
    Optional<AddressBookRecord> findRecordById(Long id);
}
