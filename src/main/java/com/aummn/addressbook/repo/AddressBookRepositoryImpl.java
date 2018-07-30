package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * This class implementing methods for saving, removing and retrieving contacts from the address books.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public class AddressBookRepositoryImpl implements AddressBookRepository {

    private Map<Long, AddressBookRecord> addressBookMap = new HashMap();
    private AtomicLong keyGenerator = new AtomicLong(0);

    public AddressBookRepositoryImpl() {}

    public AddressBookRecord saveRecord(AddressBookRecord record) {
        Long key = keyGenerator.incrementAndGet();
        record.setId(key);
        addressBookMap.put(key, record);
        return record;
    }

    public Optional<AddressBookRecord> removeRecord(long id) {
        return Optional.ofNullable(addressBookMap.remove(id));
    }

    public Optional<AddressBookRecord> findRecordById(Long id) {
        return Optional.ofNullable(addressBookMap.get(id));
    }

    public List<AddressBookRecord> findRecord(String searchString) {
        return addressBookMap.entrySet().stream()
                .filter(entry -> {
                    return String.valueOf(entry.getValue().getId()).contains(searchString) ||
                            entry.getValue().getName().contains(searchString) ||
                            entry.getValue().getPhone().contains(searchString);
                })
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }


    public List<AddressBookRecord> findAllRecordsByAbid(long addressBookId) {
        return addressBookMap.entrySet().stream()
                .filter(entry -> entry.getValue().getAbid() == addressBookId)
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

    public List<AddressBookRecord> findAllRecordsByAbids(List<Long> addressBookIds) {
        if(addressBookIds == null) throw new IllegalArgumentException("addressBook Ids is required");
        return addressBookIds.stream()
                .map(addressBookId -> this.findAllRecordsByAbid(addressBookId))
                .flatMap(addressBookRecord -> addressBookRecord.stream())
                .collect(Collectors.toList());
    }
}
