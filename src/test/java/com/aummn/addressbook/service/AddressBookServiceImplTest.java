package com.aummn.addressbook.service;

import com.aummn.addressbook.model.Contact;
import com.aummn.addressbook.repo.AddressBookRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddressBookServiceImplTest {

    AddressBookServiceImpl service = null;

    @Before
    public void runBeforeEveryTest() {
        service = new AddressBookServiceImpl(new AddressBookRepositoryImpl());
    }

    @After
    public void runAfterEveryTest() {
        service = null;
    }

    @Test
    public void addOneContactToSingleAddressBook() {
        Contact c = new Contact();
        c.setName("peter");
        c.setPhone("0430111002");
        Contact savedContact = service.addContact(c, 1L);
        assertThat(savedContact.getName(), is(c.getName()));
        assertThat(savedContact.getPhone(), is(c.getPhone()));
        assertThat(savedContact.getId(), is(1L));
    }

    @Test
    public void addTwoContactsToSingleAddressBook() {
        Contact c1 = new Contact();
        c1.setName("peter");
        c1.setPhone("0430111002");
        Contact savedContact = service.addContact(c1, 1L);
        assertThat(savedContact.getName(), is(c1.getName()));
        assertThat(savedContact.getPhone(), is(c1.getPhone()));
        assertThat(savedContact.getId(), is(1L));

        Contact c2 = new Contact();
        c2.setName("donald");
        c2.setPhone("0435495021");
        savedContact = service.addContact(c2, 1L);

        assertThat(savedContact.getName(), is(c2.getName()));
        assertThat(savedContact.getPhone(), is(c2.getPhone()));
        assertThat(savedContact.getId(), is(2L));
    }


}
