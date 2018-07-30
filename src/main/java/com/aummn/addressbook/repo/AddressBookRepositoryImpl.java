package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * This class implementing methods for saving, removing and retrieving contacts from the address books.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public class AddressBookRepositoryImpl implements AddressBookRepository {

    private Map<Long, AddressBookRecord> addressBookMap = new HashMap<>();
    private AtomicLong keyGenerator = new AtomicLong(0);

    // the data store lock
    private Lock lock = new ReentrantLock();


    public AddressBookRepositoryImpl() {}

    public AddressBookRecord saveRecord(AddressBookRecord record) {
        lock.lock();
        try {
            Long key = keyGenerator.incrementAndGet();
            record.setId(key);
            addressBookMap.put(key, record);
            return record;
        } finally {
            lock.unlock();
        }
    }

    public Optional<AddressBookRecord> removeRecord(long id) {
        lock.lock();
        try {
            return Optional.ofNullable(addressBookMap.remove(id));
        } finally {
            lock.unlock();
        }
    }

    public Optional<AddressBookRecord> findRecordById(Long id) {
        lock.lock();
        try {
            return Optional.ofNullable(addressBookMap.get(id));
        } finally {
            lock.unlock();
        }
    }

    public List<AddressBookRecord> findRecord(String searchString) {
        lock.lock();
        try {
            return addressBookMap.entrySet().stream()
                    .filter(entry -> String.valueOf(entry.getValue().getId()).contains(searchString) ||
                            entry.getValue().getName().contains(searchString) ||
                            entry.getValue().getPhone().contains(searchString))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }


    public List<AddressBookRecord> findAllRecordsByAbid(long addressBookId) {
        lock.lock();
        try {
            return addressBookMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getAbid() == addressBookId)
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    public List<AddressBookRecord> findAllRecordsByAbids(List<Long> addressBookIds) {
        if(addressBookIds == null) throw new IllegalArgumentException("addressBook Ids is required");

        lock.lock();
        try {
            return addressBookIds.stream()
                    .map(this::findAllRecordsByAbid)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }
}
