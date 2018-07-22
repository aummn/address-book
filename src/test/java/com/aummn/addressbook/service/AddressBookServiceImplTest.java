package com.aummn.addressbook.service;

import com.aummn.addressbook.model.AddressBookRecord;
import com.aummn.addressbook.model.Contact;
import com.aummn.addressbook.repo.AddressBookRepositoryImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressBookServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private AddressBookRepositoryImpl repository;

    @InjectMocks
    private AddressBookServiceImpl service;


    @Test
    public void addOneContactToSingleAddressBook() {
        Contact c = new Contact();
        c.setName("peter");
        c.setPhone("0430111002");

        AddressBookRecord record = new AddressBookRecord();
        record.setId(1L);
        record.setName(c.getName());
        record.setPhone(c.getPhone());
        record.setAbid(1L);

        when(repository.save(any(AddressBookRecord.class))).thenReturn(record);
        Contact savedContact = service.addContact(c, 1L);
        verify(repository).save(any(AddressBookRecord.class));

        assertThat(savedContact.getName()).isEqualTo("peter");
        assertThat(savedContact.getPhone()).isEqualTo("0430111002");
        assertThat(savedContact.getId()).isEqualTo(1L);
    }

    @Test
    public void addOneEmptyContactToSingleAddressBook() {

        assertThatThrownBy(() ->
          { service.addContact(null, 1L); }).hasMessage("contact is required");
    }


}
