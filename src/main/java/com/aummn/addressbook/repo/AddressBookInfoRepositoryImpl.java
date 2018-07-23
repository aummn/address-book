package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookInfoRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class implementing methods for saving, removing and retrieving address info.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public class AddressBookInfoRepositoryImpl implements AddressBookInfoRepository {

    private Map<Long, AddressBookInfoRecord> addressBookInfoMap = new HashMap();
    private AtomicLong keyGenerator = new AtomicLong(1);

    public AddressBookInfoRepositoryImpl() {}

    public AddressBookInfoRecord saveAddressBookInfo(AddressBookInfoRecord record) {
        if(record == null) throw new IllegalArgumentException("record is required");
        Long key = keyGenerator.getAndIncrement();
        record.setId(key);
        addressBookInfoMap.put(key, record);
        return record;
    }

    public Optional<AddressBookInfoRecord> removeAddressBookInfo(long id) {
        return Optional.ofNullable(addressBookInfoMap.remove(id));
    }

    public Optional<AddressBookInfoRecord> findAddressBookInfoById(long id) {
        return Optional.ofNullable(addressBookInfoMap.get(id));
    }

    public boolean existsById(long id) {
        return (addressBookInfoMap.get(id) != null);
    }
}
