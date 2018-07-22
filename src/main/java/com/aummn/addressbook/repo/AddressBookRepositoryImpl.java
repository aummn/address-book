package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

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
