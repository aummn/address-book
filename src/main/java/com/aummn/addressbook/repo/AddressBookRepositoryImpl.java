package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class implementing methods for saving, removing and retrieving contacts from the address books.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public class AddressBookRepositoryImpl {

    private Map<Long, AddressBookRecord> addressBookMap = new HashMap();
    private AtomicLong keyGenerator = new AtomicLong(1);

    public AddressBookRepositoryImpl() {}

    public AddressBookRecord save(AddressBookRecord record) {
        Long key = keyGenerator.getAndIncrement();
        addressBookMap.put(key, record);
        record.setId(key);
        return record;
    }
}
