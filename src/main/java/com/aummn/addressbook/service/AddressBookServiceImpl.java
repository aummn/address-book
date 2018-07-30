package com.aummn.addressbook.service;

import com.aummn.addressbook.model.AddressBookRecord;
import com.aummn.addressbook.model.Contact;
import com.aummn.addressbook.repo.AddressBookRepository;
import com.aummn.addressbook.repo.AddressBookRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class is designed for adding contacts, removing contacts, printing contacts from address books.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public class AddressBookServiceImpl implements AddressBookService {

    private AddressBookRepository repo;

    public AddressBookServiceImpl() {
        this.repo = new AddressBookRepositoryImpl();
    }

    public Contact addContact(Contact c, long addressBookId) {
        if(c == null) throw new IllegalArgumentException("contact is required");
        AddressBookRecord record = new AddressBookRecord(c.getName(),c.getPhone(), addressBookId);
        AddressBookRecord savedRecord = repo.saveRecord(record);
        return new Contact(savedRecord.getId(),savedRecord.getName(), savedRecord.getPhone());
    }

    public Optional<Contact> removeContact(Contact contact) {
        if(contact == null) throw new IllegalArgumentException("contact is required");
        return repo.removeRecord(contact.getId())
                .map(r -> Optional.of(new Contact(r.getId(), r.getName(), r.getPhone())))
                .orElse(Optional.empty());

    }

    public Optional<Contact> findContact(Contact contact) {
        if(contact == null) throw new IllegalArgumentException("contact is required");
        return repo.findRecordById(contact.getId())
                .map(r -> Optional.of(new Contact(r.getId(), r.getName(), r.getPhone())))
                .orElse(Optional.empty());
    }

    public List<Contact> findContact(String searchString) {
        if(searchString == null) throw new IllegalArgumentException("search string is required");
        return repo.findRecord(searchString).stream()
                .map(r -> new Contact(r.getId(), r.getName(), r.getPhone()))
                .collect(Collectors.toList());
    }

    public List<Contact> printContacts(Long addressBookId) {
        return repo.findAllRecordsByAbid(addressBookId).stream()
                .map(record -> new Contact(record.getId(), record.getName(), record.getPhone()))
                .collect(Collectors.toList());
    }

    public List<Contact> printContacts(List<Long> addressBookIds) {
        if(addressBookIds == null) throw new IllegalArgumentException("addressBook Ids is required");
        return repo.findAllRecordsByAbids(addressBookIds).stream()
                .map(record -> new Contact(record.getId(), record.getName(), record.getPhone()))
                .collect(Collectors.toList());
    }

    public List<Contact> printUniqueContacts(List<Long> addressBookIds) {
        if(addressBookIds == null) throw new IllegalArgumentException("addressBook Ids is required");
        return repo.findAllRecordsByAbids(addressBookIds).stream()
                .map(record -> new Contact(record.getId(), record.getName(), record.getPhone()))
                .filter(distinctByKey(Contact::getContents))
                .collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> uniqueContactSet = ConcurrentHashMap.newKeySet();
        return t -> uniqueContactSet.add(keyExtractor.apply(t));
    }
}
