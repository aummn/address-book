package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookInfoRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.*;

public class AddressBookInfoRepositoryImplTest {
    private AtomicLong keyGenerator = null;
    private AddressBookInfoRepositoryImpl repo;

    @Before
    public void runBeforeEveryTest() {
        repo = new AddressBookInfoRepositoryImpl();
        keyGenerator = new AtomicLong(1);
    }

    @After
    public void runAfterEveryTest() {
        repo = null;
    }


    @Test
    public void saveAddressBookInfo()
    {
        Long key = keyGenerator.getAndIncrement();
        AddressBookInfoRecord record = new AddressBookInfoRecord(key, "vip");
        AddressBookInfoRecord savedRecord = repo.saveAddressBookInfo(record);
        assertThat(savedRecord).isEqualTo(record);
    }

    @Test
    public void saveAddressBookInfo_MissingRecord() {

        assertThatThrownBy(() ->
        { repo.saveAddressBookInfo(null); }).hasMessage("record is required");
    }

    @Test
    public void removeAddressBookInfo() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");

        AddressBookInfoRecord savedRecord1 = repo.saveAddressBookInfo(record1);
        AddressBookInfoRecord savedRecord2 = repo.saveAddressBookInfo(record2);
        AddressBookInfoRecord savedRecord3 = repo.saveAddressBookInfo(record3);

        repo.removeAddressBookInfo(1L);
        Optional<AddressBookInfoRecord> recordOptional = repo.findAddressBookInfoById(1L);
        assertThat(recordOptional).isEmpty();

        recordOptional = repo.findAddressBookInfoById(2L);
        assertThat(recordOptional).isNotEmpty().hasValue(savedRecord2);

        recordOptional = repo.findAddressBookInfoById(3L);
        assertThat(recordOptional).isNotEmpty().hasValue(savedRecord3);
    }

    @Test
    public void removeAddressBookInfo_NonExistingAddressBookInfo() {
        Optional<AddressBookInfoRecord> record = repo.removeAddressBookInfo(6L);
        assertThat(record).isEmpty();
    }

    @Test
    public void findAddressBookInfoById() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.saveAddressBookInfo(record1);
        repo.saveAddressBookInfo(record2);
        repo.saveAddressBookInfo(record3);

        Optional<AddressBookInfoRecord> recordOptional = repo.findAddressBookInfoById(2L);
        assertThat(recordOptional).isNotEmpty().hasValue(record2);
    }

    @Test
    public void findRecordById_NonExistingRecord() {
        Optional<AddressBookInfoRecord> recordOptional = repo.findAddressBookInfoById(6L);
        assertThat(recordOptional).isEmpty();
    }


    @Test
    public void existsById() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.saveAddressBookInfo(record1);
        repo.saveAddressBookInfo(record2);
        repo.saveAddressBookInfo(record3);

        boolean found = repo.existsById(2L);
        assertThat(found).isTrue();

        found = repo.existsById(3L);
        assertThat(found).isTrue();
    }

    @Test
    public void existsById_NonExistingAddressBookInfo() {
        boolean found = repo.existsById(4L);
        assertThat(found).isFalse();
    }
}
