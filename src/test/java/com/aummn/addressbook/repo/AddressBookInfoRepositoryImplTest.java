package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookInfoRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.*;

public class AddressBookInfoRepositoryImplTest {
    private AtomicLong keyGenerator;
    private AddressBookInfoRepository repo;

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
        AddressBookInfoRecord record = new AddressBookInfoRecord(1, "vip");
        AddressBookInfoRecord inputRecord = new AddressBookInfoRecord(6, "vip");
        AddressBookInfoRecord savedRecord = repo.saveAddressBookInfo(inputRecord);
        assertThat(savedRecord).isEqualTo(record);
    }

    @Test
    public void saveAddressBookInfo_MissingRecord() {

        assertThatThrownBy(() ->
                repo.saveAddressBookInfo(null)).hasMessage("record is required");
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

    @Test
    public void findAllAddressBookInfo() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.saveAddressBookInfo(record1);
        repo.saveAddressBookInfo(record2);
        repo.saveAddressBookInfo(record3);

        List<AddressBookInfoRecord> records = repo.findAllAddressBookInfo();
        assertThat(records).isNotEmpty().hasSize(3).extracting("id", "name")
                .contains(tuple(1L, "vip"),
                        tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAllAddressBookInfo_EmptyAddressBookInfo() {
        List<AddressBookInfoRecord> records = repo.findAllAddressBookInfo();
        assertThat(records).isEmpty();
    }


    @Test
    public void findAddressBookInfoByName_PartialMatch() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.saveAddressBookInfo(record1);
        repo.saveAddressBookInfo(record2);
        repo.saveAddressBookInfo(record3);

        List<AddressBookInfoRecord> records = repo.findAddressBookInfoByName("ne");
        assertThat(records).isNotEmpty().hasSize(2).extracting("id", "name")
                .contains(tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAddressBookInfoByName_ExactMatch() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.saveAddressBookInfo(record1);
        repo.saveAddressBookInfo(record2);
        repo.saveAddressBookInfo(record3);

        List<AddressBookInfoRecord> records = repo.findAddressBookInfoByName("vip");
        assertThat(records).isNotEmpty().hasSize(1).extracting("id", "name")
                .contains(tuple(1L, "vip"));
    }

    @Test
    public void findAddressBookInfoByName_AllMatch() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.saveAddressBookInfo(record1);
        repo.saveAddressBookInfo(record2);
        repo.saveAddressBookInfo(record3);

        List<AddressBookInfoRecord> records = repo.findAddressBookInfoByName("");
        assertThat(records).isNotEmpty().hasSize(3).extracting("id", "name")
                .contains(tuple(1L, "vip"),
                        tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAddressBookInfoByName_NoMatchingRecords() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.saveAddressBookInfo(record1);
        repo.saveAddressBookInfo(record2);
        repo.saveAddressBookInfo(record3);

        List<AddressBookInfoRecord> records = repo.findAddressBookInfoByName("tom");
        assertThat(records).isEmpty();
    }

    @Test
    public void findAddressBookInfoByName_NullName() {

        assertThatThrownBy(() ->
                repo.findAddressBookInfoByName(null)).hasMessage("name is required");
    }


}
