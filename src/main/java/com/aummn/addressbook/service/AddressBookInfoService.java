package com.aummn.addressbook.service;

import com.aummn.addressbook.model.AddressBookInfo;

import java.util.List;
import java.util.Optional;

/**
 * An interface for adding / removing / printing address book info.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public interface AddressBookInfoService {

    /**
     * Add an address book info.
     *
     * @param addressBookInfo an address book info
     * @return an AddressBookInfo object
     */
    AddressBookInfo addAddressBookInfo(AddressBookInfo addressBookInfo);

    /**
     * Remove an address book info.
     *
     * @param addressBookInfo an address book info
     *
     */
    Optional<AddressBookInfo> removeAddressBookInfo(AddressBookInfo addressBookInfo);

    /**
     * Find an address book info.
     *
     * @param id the id of an address book info
     * @return an Optional object
     *
     */
    Optional<AddressBookInfo> findAddressBookInfo(long id);

    /**
     * Find all address book info.
     *
     * @return an list of AddressBookInfo object
     *
     */
    List<AddressBookInfo> findAllAddressBookInfo();

}
