package com.aummn.addressbook.model;

import java.util.Objects;

/**
 * This class holding the address book record info.
 *
 * @author James Jin
 * @version 1.0 21/07/2018
 * @since 1.0
 */
public class AddressBookRecord {

    private long id;
    private String name;
    private String phone;
    private long abid;

    public AddressBookRecord() {}

    public AddressBookRecord(long id, String name, String phone, long abid) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.abid = abid;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getAbid() {
        return abid;
    }

    public void setAbid(long abid) {
        this.abid = abid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBookRecord that = (AddressBookRecord) o;
        return id == that.id &&
                abid == that.abid &&
                Objects.equals(name, that.name) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, phone, abid);
    }

    @Override
    public String toString() {
        return "AddressBookRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", abid=" + abid +
                '}';
    }
}
