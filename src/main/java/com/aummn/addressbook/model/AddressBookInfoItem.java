package com.aummn.addressbook.model;

/**
 * This class is the wrapper class for AddressBookInfoRecord for use in combox.
 *
 * @author James Jin
 * @version 1.0 21/07/2018
 * @since 1.0
 */
public class AddressBookInfoItem {

    private long id;
    private String name;

    public AddressBookInfoItem(AddressBookInfo info) {
        this.id = info.getId();
        this.name = info.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
