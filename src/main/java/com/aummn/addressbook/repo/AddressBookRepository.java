package com.aummn.addressbook.repo;

/**
 * This interface defining methods for saving, removing and retrieving contacts from the address books.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public interface AddressBookRepository<T, ID> {
    <S extends T> S save(S s);
}
