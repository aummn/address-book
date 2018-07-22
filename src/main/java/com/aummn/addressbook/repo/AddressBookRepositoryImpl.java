package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
        record.setId(key);
        addressBookMap.put(key, record);
        return record;
    }

    public List<AddressBookRecord> saveAll(List<AddressBookRecord> records) {
        if(records == null) throw new IllegalArgumentException("records is required");
        return records.stream()
                .map(record -> this.save(record)).collect(Collectors.toList());
    }
}
