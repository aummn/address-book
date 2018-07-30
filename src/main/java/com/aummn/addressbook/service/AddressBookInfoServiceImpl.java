package com.aummn.addressbook.service;

import com.aummn.addressbook.model.AddressBookInfo;
import com.aummn.addressbook.model.AddressBookInfoRecord;
import com.aummn.addressbook.repo.AddressBookInfoRepository;
import com.aummn.addressbook.repo.AddressBookInfoRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is designed for adding / removing / printing address book info.
 *
 * @author James Jin
 * @version 1.0 7/22/2018
 * @since 1.0
 */
public class AddressBookInfoServiceImpl implements AddressBookInfoService {

    private AddressBookInfoRepository repo;

    public AddressBookInfoServiceImpl() {
        this.repo = new AddressBookInfoRepositoryImpl();
    }

    public AddressBookInfo addAddressBookInfo(AddressBookInfo info) {
        if(info == null) throw new IllegalArgumentException("address book info is required");
        AddressBookInfoRecord record = new AddressBookInfoRecord(info.getName());
        AddressBookInfoRecord savedRecord = repo.saveAddressBookInfo(record);
        return new AddressBookInfo(savedRecord.getId(),savedRecord.getName());
    }

    public Optional<AddressBookInfo> removeAddressBookInfo(AddressBookInfo info) {
        if(info == null) throw new IllegalArgumentException("address book info is required");
        return repo.removeAddressBookInfo(info.getId()).map(r -> new AddressBookInfo(r.getId(), r.getName()));
    }

    public List<AddressBookInfo> findAddressBookInfoByName(String name) {
        if (name == null) name = "";
        return repo.findAddressBookInfoByName(name).stream()
                .map(r -> new AddressBookInfo(r.getId(), r.getName()))
                .collect(Collectors.toList());
    }


    public List<AddressBookInfo> findAllAddressBookInfo() {
        return repo.findAllAddressBookInfo().stream()
                .map(r -> new AddressBookInfo(r.getId(), r.getName()))
                .collect(Collectors.toList());
    }

}
