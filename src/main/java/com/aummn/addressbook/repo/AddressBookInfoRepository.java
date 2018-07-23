package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookInfoRecord;
import java.util.Optional;

/**
 * This interface defining methods for saving, removing and retrieving address book info.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public interface AddressBookInfoRepository {

    /**
     * Insert an address book info record.
     *
     * @param record the address book info record
     * @return the AddressBookInfoRecord object
     */
    AddressBookInfoRecord saveAddressBookInfo(AddressBookInfoRecord record);

    /**
     * Remove a record.
     *
     * @param id the id of an address book info record
     * @return an Optional<AddressBookInfoRecord> object
     */
    Optional<AddressBookInfoRecord> removeAddressBookInfo(long id);

    /**
     * Find an address book info record by id.
     *
     * @param id the id of an address book info record
     * @return an Optional<AddressBookInfoRecord> object
     */
    Optional<AddressBookInfoRecord> findAddressBookInfoById(long id);

    /**
     * Whether an address book info record exists.
     *
     * @param id the id of an address book info record
     * @return true when the address book info record matching the specified id; false otherwise
     */
    boolean existsById(long id);
}
