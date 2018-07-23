package com.aummn.addressbook.service;

import com.aummn.addressbook.model.AddressBookRecord;
import com.aummn.addressbook.model.Contact;
import com.aummn.addressbook.repo.AddressBookRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressBookServiceImplTest {

    @Mock
    private AddressBookRepositoryImpl repository;

    @InjectMocks
    private AddressBookServiceImpl service;


    @Test
    public void addOneContactToSingleAddressBook() {
        Contact c = new Contact();
        c.setName("peter");
        c.setPhone("0430111002");

        AddressBookRecord inputRecord = new AddressBookRecord();
        inputRecord.setName(c.getName());
        inputRecord.setPhone(c.getPhone());
        inputRecord.setAbid(1L);

        AddressBookRecord outputRecord = new AddressBookRecord();
        outputRecord.setId(1L);
        outputRecord.setName(c.getName());
        outputRecord.setPhone(c.getPhone());
        outputRecord.setAbid(1L);

        when(repository.saveRecord(eq(inputRecord))).thenReturn(outputRecord);
        Contact savedContact = service.addContact(c, 1L);
        verify(repository).saveRecord(eq(inputRecord));

        assertThat(savedContact.getName()).isEqualTo("peter");
        assertThat(savedContact.getPhone()).isEqualTo("0430111002");
        assertThat(savedContact.getId()).isEqualTo(1L);
    }

    @Test
    public void addOneEmptyContactToSingleAddressBook_MissingContact() {

        assertThatThrownBy(() ->
          { service.addContact(null, 1L); }).hasMessage("contact is required");
    }


    @Test
    public void addTwoContactsToSingleAddressBook() {
        Contact c1 = new Contact();
        c1.setName("peter");
        c1.setPhone("0430111002");

        Contact c2 = new Contact();
        c2.setName("donald");
        c2.setPhone("0435495021");

        AddressBookRecord inputRecord1 = new AddressBookRecord();
        inputRecord1.setName(c1.getName());
        inputRecord1.setPhone(c1.getPhone());
        inputRecord1.setAbid(1L);

        AddressBookRecord outputRecord1 = new AddressBookRecord();
        outputRecord1.setId(1L);
        outputRecord1.setName(c1.getName());
        outputRecord1.setPhone(c1.getPhone());
        outputRecord1.setAbid(1L);

        AddressBookRecord inputRecord2 = new AddressBookRecord();
        inputRecord2.setName(c2.getName());
        inputRecord2.setPhone(c2.getPhone());
        inputRecord2.setAbid(1L);

        AddressBookRecord outputRecord2 = new AddressBookRecord();
        outputRecord2.setId(2L);
        outputRecord2.setName(c2.getName());
        outputRecord2.setPhone(c2.getPhone());
        outputRecord2.setAbid(1L);

        List<Contact> contacts = Arrays.asList(c1, c2);

        when(repository.saveRecords(eq(Arrays.asList(inputRecord1, inputRecord2))))
                .thenReturn(Arrays.asList(outputRecord1, outputRecord2));


        List<Contact> contactList = service.addContacts(contacts, 1L);
        verify(repository, times(1)).saveRecords(eq(Arrays.asList(inputRecord1, inputRecord2)));

        assertThat(contactList).hasSize(2).extracting("id", "name", "phone")
                .contains(tuple(1L, "peter", "0430111002"),
                        tuple(2L, "donald", "0435495021"));
    }

    @Test
    public void addTwoContactsToSingleAddressBook_MissingContacts() {

        assertThatThrownBy(() ->
        { service.addContacts(null, 1L); }).hasMessage("contacts is required");
    }

    @Test
    public void addTwoContactsToSingleAddressBook_EmptyContacts() {

        List<Contact> contactList = service.addContacts(new ArrayList<>(), 1L);
        assertThat(contactList).isEmpty();
    }


    @Test
    public void addTwoContactsToTwoAddressBooks() {
        Contact c1 = new Contact();
        c1.setName("peter");
        c1.setPhone("0430111002");

        Contact c2 = new Contact();
        c2.setName("donald");
        c2.setPhone("0435495021");

        AddressBookRecord inputRecord1 = new AddressBookRecord();
        inputRecord1.setName(c1.getName());
        inputRecord1.setPhone(c1.getPhone());
        inputRecord1.setAbid(1L);

        AddressBookRecord outputRecord1 = new AddressBookRecord();
        outputRecord1.setId(1L);
        outputRecord1.setName(c1.getName());
        outputRecord1.setPhone(c1.getPhone());
        outputRecord1.setAbid(1L);

        AddressBookRecord inputRecord2 = new AddressBookRecord();
        inputRecord2.setName(c2.getName());
        inputRecord2.setPhone(c2.getPhone());
        inputRecord2.setAbid(1L);

        AddressBookRecord outputRecord2 = new AddressBookRecord();
        outputRecord2.setId(2L);
        outputRecord2.setName(c2.getName());
        outputRecord2.setPhone(c2.getPhone());
        outputRecord2.setAbid(1L);


        AddressBookRecord inputRecord3 = new AddressBookRecord();
        inputRecord3.setName(c1.getName());
        inputRecord3.setPhone(c1.getPhone());
        inputRecord3.setAbid(2L);

        AddressBookRecord outputRecord3 = new AddressBookRecord();
        outputRecord3.setId(3L);
        outputRecord3.setName(c1.getName());
        outputRecord3.setPhone(c1.getPhone());
        outputRecord3.setAbid(2L);

        AddressBookRecord inputRecord4 = new AddressBookRecord();
        inputRecord4.setName(c2.getName());
        inputRecord4.setPhone(c2.getPhone());
        inputRecord4.setAbid(2L);

        AddressBookRecord outputRecord4 = new AddressBookRecord();
        outputRecord4.setId(4L);
        outputRecord4.setName(c2.getName());
        outputRecord4.setPhone(c2.getPhone());
        outputRecord4.setAbid(2L);

        when(repository.saveRecords(eq(Arrays.asList(inputRecord1, inputRecord2))))
                .thenReturn(Arrays.asList(outputRecord1, outputRecord2));

        when(repository.saveRecords(eq(Arrays.asList(inputRecord3, inputRecord4))))
                .thenReturn(Arrays.asList(outputRecord3, outputRecord4));

        List<Contact> contactList = service.addContacts(Arrays.asList(c1, c2), Arrays.asList(1L, 2L));
        verify(repository, times(1)).saveRecords(Arrays.asList(inputRecord1, inputRecord2));
        verify(repository, times(1)).saveRecords(Arrays.asList(inputRecord3, inputRecord4));

        assertThat(contactList).hasSize(4).extracting("id", "name", "phone")
                .contains(tuple(1L, "peter", "0430111002"),
                        tuple(2L, "donald", "0435495021"),
                        tuple(3L, "peter", "0430111002"),
                        tuple(4L, "donald", "0435495021"));
    }

    @Test
    public void addTwoContactsToTwoAddressBooks_MissingContacts() {

        assertThatThrownBy(() ->
        { service.addContacts(null, Arrays.asList(1L, 2L)); }).hasMessage("contacts is required");
    }

    @Test
    public void addTwoContactsToTwoAddressBooks_MissingAddessBookIds() {

        Contact c1 = new Contact();
        c1.setName("peter");
        c1.setPhone("0430111002");

        Contact c2 = new Contact();
        c2.setName("donald");
        c2.setPhone("0435495021");

        assertThatThrownBy(() ->
        { service.addContacts(Arrays.asList(c1, c2), null); }).hasMessage("address book ids is required");
    }

    @Test
    public void addTwoContactsToTwoAddressBooks_EmptyContacts() {

        List<Contact> contactList = service.addContacts(new ArrayList<>(), Arrays.asList(1L, 2L));
        assertThat(contactList).isEmpty();
    }

    @Test
    public void addTwoContactsToTwoAddressBooks_EmptyAddressIds() {

        Contact c1 = new Contact();
        c1.setName("peter");
        c1.setPhone("0430111002");

        Contact c2 = new Contact();
        c2.setName("donald");
        c2.setPhone("0435495021");

        List<Contact> contactList = service.addContacts(Arrays.asList(c1, c2), new ArrayList<>());
        assertThat(contactList).isEmpty();
    }


    @Test
    public void findContact() {

        Contact c1 = new Contact();
        c1.setId(1L);
        c1.setName("peter");
        c1.setPhone("0430111002");

        when(repository.findRecordById(1L))
                .thenReturn(Optional.of(new AddressBookRecord(1L, "peter", "0430111002", 1L)));
        Optional<Contact> contactOptional = service.findContact(c1);
        assertThat(contactOptional).isNotEmpty().hasValue(c1);
    }

    @Test
    public void findContact_NonExistingContact() {

        Contact c1 = new Contact();
        c1.setId(1L);
        c1.setName("peter");
        c1.setPhone("0430111002");

        when(repository.findRecordById(1L)).thenReturn(Optional.empty());
        Optional<Contact> contactOptional = service.findContact(c1);
        assertThat(contactOptional).isEmpty();
    }

    @Test
    public void findContact_MissingContact() {

        assertThatThrownBy(() ->
        { service.findContact(null); }).hasMessage("contact is required");
    }


    @Test
    public void removeContact() {

        Contact c1 = new Contact();
        c1.setId(1L);
        c1.setName("peter");
        c1.setPhone("0430111002");

        doNothing().when(repository).removeRecord(1L);

        service.removeContact(c1);
        verify(repository, times(1)).removeRecord(1L);
    }

    @Test
    public void removeContact_MissingContact() {

        assertThatThrownBy(() ->
        { service.removeContact(null); }).hasMessage("contact is required");
    }

    @Test
    public void removeContacts() {

        Contact c1 = new Contact();
        c1.setId(1L);
        c1.setName("peter");
        c1.setPhone("0430111002");

        Contact c2 = new Contact();
        c2.setId(2L);
        c2.setName("donald");
        c2.setPhone("0435495021");

        doNothing().when(repository).removeRecords(Arrays.asList(1L, 2L));
        service.removeContacts(Arrays.asList(c1, c2));
        verify(repository, times(1)).removeRecords(Arrays.asList(1L, 2L));
    }

    @Test
    public void removeContacts_MissingContacts() {

        assertThatThrownBy(() ->
        { service.removeContacts(null); }).hasMessage("contacts is required");
    }

}
