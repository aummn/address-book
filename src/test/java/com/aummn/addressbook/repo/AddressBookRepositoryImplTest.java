package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;
import com.aummn.addressbook.model.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import static org.assertj.core.api.Assertions.*;

public class AddressBookRepositoryImplTest {
    private AtomicLong keyGenerator = null;
    private AddressBookRepositoryImpl repo;

    @Before
    public void runBeforeEveryTest() {
        repo = new AddressBookRepositoryImpl();
        keyGenerator = new AtomicLong(1);
    }

    @After
    public void runAfterEveryTest() {
        repo = null;
    }


    @Test
    public void saveRecord()
    {
        Long key = keyGenerator.getAndIncrement();
        AddressBookRecord record = new AddressBookRecord(key, "peter", "0430111002", 1);
        AddressBookRecord savedRecord = repo.saveRecord(record);
        assertThat(savedRecord).isEqualTo(record);
    }

    @Test
    public void saveRecords()
    {
        Long key = keyGenerator.getAndIncrement();
        AddressBookRecord record1 = new AddressBookRecord(key, "peter", "0430111002", 1);
        key = keyGenerator.getAndIncrement();
        AddressBookRecord record2 = new AddressBookRecord(key, "donald", "0435495021", 1);
        key = keyGenerator.getAndIncrement();
        AddressBookRecord record3 = new AddressBookRecord(key, "dick", "0402124587", 1);

        List<AddressBookRecord> savedRecords = repo.saveRecords(Arrays.asList(record1, record2, record3));

        assertThat(savedRecords).hasSize(3).extracting("id", "name", "phone", "abid")
                .contains(tuple(1L, "peter", "0430111002", 1L),
                        tuple(2L, "donald", "0435495021", 1L),
                        tuple(3L, "dick", "0402124587", 1L));
    }

    @Test
    public void saveRecords_MissingRecords() {

        assertThatThrownBy(() ->
        { repo.saveRecords(null); }).hasMessage("records is required");
    }

    @Test
    public void removeRecord() {

        Long key = keyGenerator.get();
        AddressBookRecord record1 = new AddressBookRecord(key, "peter", "0430111002", 1);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record2 = new AddressBookRecord(key, "donald", "0435495021", 1);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record3 = new AddressBookRecord(key, "dick", "0402124587", 1);

        List<AddressBookRecord> savedRecords = repo.saveRecords(Arrays.asList(record1, record2, record3));

        repo.removeRecord(1L);
        Optional<AddressBookRecord> recordOptional = repo.findRecordById(1L);

        assertThat(recordOptional).isEmpty();
    }

    @Test
    public void findRecordById() {
        Long key = keyGenerator.get();
        AddressBookRecord record1 = new AddressBookRecord(key, "peter", "0430111002", 1);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record2 = new AddressBookRecord(key, "donald", "0435495021", 1);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record3 = new AddressBookRecord(key, "dick", "0402124587", 1);
        repo.saveRecords(Arrays.asList(record1, record2, record3));

        Optional<AddressBookRecord> recordOptional = repo.findRecordById(2L);
        assertThat(recordOptional).isNotEmpty().hasValue(record2);
    }

}
