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
                service.addContact(null, 1L)).hasMessage("contact is required");
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
                service.findContact((Contact)null)).hasMessage("contact is required");
    }


    @Test
    public void findContact_SearchString() {

        Contact c1 = new Contact();
        c1.setId(1L);
        c1.setName("peter");
        c1.setPhone("0430111002");

        AddressBookRecord record1 = new AddressBookRecord(1, "peter", "0430111002", 1);
        AddressBookRecord record2 = new AddressBookRecord(2, "donald", "0435495021", 1);
        AddressBookRecord record3 = new AddressBookRecord(3, "dick", "0402124587", 1);

        AddressBookRecord record4 = new AddressBookRecord(4, "tom", "0422484920", 2L);
        AddressBookRecord record5 = new AddressBookRecord(5, "sam", "0430823002", 2L);
        AddressBookRecord record6 = new AddressBookRecord(6, "larry", "0435498247", 2L);

        when(repository.findRecord("1"))
                .thenReturn(Arrays.asList(record1, record2, record3));
        List<Contact> contactList = service.findContact("1");
        assertThat(contactList).isNotEmpty().hasSize(3).extracting("id", "name", "phone")
                .contains(tuple(1L, "peter", "0430111002"),
                        tuple(2L, "donald", "0435495021"),
                        tuple(3L, "dick", "0402124587"));

        when(repository.findRecord("7"))
                .thenReturn(Arrays.asList(record3, record6));
        contactList = service.findContact(String.valueOf(7));
        assertThat(contactList).isNotEmpty().hasSize(2).extracting("id", "name", "phone")
                .contains(tuple(3L, "dick", "0402124587"),
                          tuple(6L, "larry", "0435498247")
                        );
    }


    @Test
    public void removeContact() {

        Contact c1 = new Contact();
        c1.setId(1L);
        c1.setName("peter");
        c1.setPhone("0430111002");

        AddressBookRecord record = new AddressBookRecord(1, "peter", "0430111002", 1);
        when(repository.removeRecord(1L)).thenReturn(Optional.of(record));

        Optional<Contact> removedContactOptional = service.removeContact(c1);
        verify(repository, times(1)).removeRecord(1L);
        assertThat(removedContactOptional.get()).isEqualTo(c1);
    }

    @Test
    public void removeContact_MissingContact() {

        assertThatThrownBy(() ->
                service.removeContact(null)).hasMessage("contact is required");
    }

    @Test
    public void printContacts_SingleAddressBook() {

        AddressBookRecord record1 = new AddressBookRecord();
        record1.setId(1L);
        record1.setName("peter");
        record1.setPhone("0430111002");
        record1.setAbid(1L);

        AddressBookRecord record2 = new AddressBookRecord();
        record2.setId(2L);
        record2.setName("donald");
        record2.setPhone("0435495021");
        record2.setAbid(1L);

        AddressBookRecord record3 = new AddressBookRecord();
        record3.setId(3L);
        record3.setName("dick");
        record3.setPhone("0402124587");
        record3.setAbid(1L);

        when(repository.findAllRecordsByAbid(1L)).thenReturn(Arrays.asList(record1, record2, record3));
        List<Contact> contacts = service.printContacts(1L);
        verify(repository, times(1)).findAllRecordsByAbid(1L);
        assertThat(contacts).isNotEmpty().hasSize(3).extracting("id", "name", "phone")
                .contains(tuple(1L, "peter", "0430111002"),
                        tuple(2L, "donald", "0435495021"),
                        tuple(3L, "dick", "0402124587"));
    }


    @Test
    public void printContacts_NonExistingAbid() {

        AddressBookRecord record1 = new AddressBookRecord();
        record1.setId(1L);
        record1.setName("peter");
        record1.setPhone("0430111002");
        record1.setAbid(1L);

        AddressBookRecord record2 = new AddressBookRecord();
        record2.setId(2L);
        record2.setName("donald");
        record2.setPhone("0435495021");
        record2.setAbid(1L);

        AddressBookRecord record3 = new AddressBookRecord();
        record3.setId(3L);
        record3.setName("dick");
        record3.setPhone("0402124587");
        record3.setAbid(1L);

        when(repository.findAllRecordsByAbid(1L)).thenReturn(new ArrayList<>());
        List<Contact> contacts = service.printContacts(1L);
        verify(repository, times(1)).findAllRecordsByAbid(1L);
        assertThat(contacts).isEmpty();
    }


    @Test
    public void printContacts_MultipleAddressBooks() {

        AddressBookRecord record1 = new AddressBookRecord();
        record1.setId(1L);
        record1.setName("peter");
        record1.setPhone("0430111002");
        record1.setAbid(1L);

        AddressBookRecord record2 = new AddressBookRecord();
        record2.setId(2L);
        record2.setName("donald");
        record2.setPhone("0435495021");
        record2.setAbid(2L);

        AddressBookRecord record3 = new AddressBookRecord();
        record3.setId(3L);
        record3.setName("dick");
        record3.setPhone("0402124587");
        record3.setAbid(3L);

        when(repository.findAllRecordsByAbids(Arrays.asList(1L, 2L, 3L))).thenReturn(Arrays.asList(record1, record2, record3));
        List<Contact> contacts = service.printContacts(Arrays.asList(1L, 2L, 3L));
        verify(repository, times(1)).findAllRecordsByAbids(Arrays.asList(1L, 2L, 3L));
        assertThat(contacts).isNotEmpty().hasSize(3).extracting("id", "name", "phone")
                .contains(tuple(1L, "peter", "0430111002"),
                        tuple(2L, "donald", "0435495021"),
                        tuple(3L, "dick", "0402124587"));
    }

    @Test
    public void printContacts_MultipleAddressBooks_NonExistingAbids() {

        when(repository.findAllRecordsByAbids(Arrays.asList(1L, 2L, 3L))).thenReturn(new ArrayList<>());
        List<Contact> contacts = service.printContacts(Arrays.asList(1L, 2L, 3L));
        verify(repository, times(1)).findAllRecordsByAbids(Arrays.asList(1L, 2L, 3L));
        assertThat(contacts).isEmpty();
    }

    @Test
    public void printContacts_MultipleAddressBooks_MissingContacts() {

        assertThatThrownBy(() ->
                service.printContacts((List<Long>)null)).hasMessage("addressBook Ids is required");
    }


    @Test
    public void printUniqueContacts_MultipleAddressBooks() {

        AddressBookRecord record1 = new AddressBookRecord();
        record1.setId(1L);
        record1.setName("peter");
        record1.setPhone("0430111002");
        record1.setAbid(1L);

        AddressBookRecord record2 = new AddressBookRecord();
        record2.setId(2L);
        record2.setName("donald");
        record2.setPhone("0435495021");
        record2.setAbid(1L);

        AddressBookRecord record3 = new AddressBookRecord();
        record3.setId(3L);
        record3.setName("dick");
        record3.setPhone("0402124587");
        record3.setAbid(1L);

        AddressBookRecord record4 = new AddressBookRecord();
        record4.setId(4L);
        record4.setName("donald");
        record4.setPhone("0435495021");
        record4.setAbid(2L);

        AddressBookRecord record5 = new AddressBookRecord();
        record5.setId(5L);
        record5.setName("dick");
        record5.setPhone("0402124587");
        record5.setAbid(3L);

        AddressBookRecord record6 = new AddressBookRecord();
        record6.setId(6L);
        record6.setName("dick");
        record6.setPhone("0402124587");
        record6.setAbid(3L);


        when(repository.findAllRecordsByAbids(Arrays.asList(1L, 2L, 3L)))
                .thenReturn(Arrays.asList(record1, record2, record3, record4, record5, record6));
        List<Contact> contacts = service.printUniqueContacts(Arrays.asList(1L, 2L, 3L));
        verify(repository, times(1)).findAllRecordsByAbids(Arrays.asList(1L, 2L, 3L));
        assertThat(contacts).isNotEmpty().hasSize(3).extracting("id", "name", "phone")
                .contains(tuple(1L, "peter", "0430111002"),
                        tuple(2L, "donald", "0435495021"),
                        tuple(3L, "dick", "0402124587"));
    }

    @Test
    public void printUniqueContacts_MultipleAddressBooks_MissingContacts() {

        assertThatThrownBy(() ->
                service.printUniqueContacts(null)).hasMessage("addressBook Ids is required");
    }

}
