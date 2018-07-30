package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookInfoRecord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * This class implementing methods for saving, removing and retrieving address info.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public class AddressBookInfoRepositoryImpl implements AddressBookInfoRepository {

    // the address book data store
    private Map<Long, AddressBookInfoRecord> addressBookInfoMap = new HashMap();

    // the ID generator for address books
    private AtomicLong keyGenerator = new AtomicLong(0);

    public AddressBookInfoRepositoryImpl() {}

    public AddressBookInfoRecord saveAddressBookInfo(AddressBookInfoRecord record) {
        if(record == null) throw new IllegalArgumentException("record is required");
        Long key = keyGenerator.incrementAndGet();
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

    public List<AddressBookInfoRecord> findAddressBookInfoByName(String name) {
        if(name == null) throw new IllegalArgumentException("name is required");
        return addressBookInfoMap.entrySet().stream()
                .filter(entry -> entry.getValue().getName().contains(name))
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

    public boolean existsById(long id) {
        return (addressBookInfoMap.get(id) != null);
    }

    public List<AddressBookInfoRecord> findAllAddressBookInfo() {
        return addressBookInfoMap.entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }
}
