package com.aummn.addressbook.service;

import com.aummn.addressbook.model.AddressBookInfo;
import com.aummn.addressbook.model.AddressBookInfoRecord;
import com.aummn.addressbook.repo.AddressBookInfoRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressBookInfoServiceImplTest {

    @Mock
    private AddressBookInfoRepositoryImpl repository;

    @InjectMocks
    private AddressBookInfoServiceImpl service;


    @Test
    public void addAddressBookInfo() {
        AddressBookInfo info = new AddressBookInfo();
        info.setName("vip");

        AddressBookInfoRecord record = new AddressBookInfoRecord();
        record.setName("vip");

        AddressBookInfoRecord outputRecord = new AddressBookInfoRecord();
        outputRecord.setId(1L);
        outputRecord.setName("vip");

        when(repository.saveAddressBookInfo(eq(record))).thenReturn(outputRecord);
        AddressBookInfo savedInfo = service.addAddressBookInfo(info);
        verify(repository).saveAddressBookInfo(eq(record));

        assertThat(savedInfo.getId()).isEqualTo(1L);
        assertThat(savedInfo.getName()).isEqualTo("vip");
    }

    @Test
    public void addAddressBookInfo_MissingAddressBookInfo() {

        assertThatThrownBy(() ->
          { service.addAddressBookInfo(null); }).hasMessage("address book info is required");
    }


    @Test
    public void findAddressBookInfo() {
        AddressBookInfo info = new AddressBookInfo();
        info.setId(1L);
        info.setName("vip");

        when(repository.findAddressBookInfoById(1L))
                .thenReturn(Optional.of(new AddressBookInfoRecord(1L, "vip")));
        Optional<AddressBookInfo> infoOptional = service.findAddressBookInfo(1L);
        assertThat(infoOptional).isNotEmpty().hasValue(info);
    }

    @Test
    public void findAddressBookInfo_NonExistingAddressBookInfo() {
        when(repository.findAddressBookInfoById(6L)).thenReturn(Optional.empty());
        Optional<AddressBookInfo> infoOptional = service.findAddressBookInfo(6L);
        assertThat(infoOptional).isEmpty();
    }

    @Test
    public void removeAddressBookInfo() {
        AddressBookInfo info = new AddressBookInfo();
        info.setId(1L);
        info.setName("vip");

        AddressBookInfoRecord record = new AddressBookInfoRecord();
        record.setId(1L);
        record.setName("vip");

        when(repository.removeAddressBookInfo(1L)).thenReturn(Optional.of(record));
        Optional<AddressBookInfo> infoOptional = service.removeAddressBookInfo(info);
        verify(repository, times(1)).removeAddressBookInfo(1L);
        assertThat(infoOptional).isNotEmpty().hasValue(info);
    }

    @Test
    public void removeAddressBookInfo_MissingAddressBookInfo() {
        assertThatThrownBy(() ->
        { service.removeAddressBookInfo(null); }).hasMessage("address book info is required");
    }

}
