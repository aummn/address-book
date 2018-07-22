package com.aummn.addressbook.repo;

public interface AddressBookRepository<T, ID> {
    <S extends T> S save(S s);
}
