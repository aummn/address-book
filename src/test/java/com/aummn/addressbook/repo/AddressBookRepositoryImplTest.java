package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;
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
        AddressBookRecord record = new AddressBookRecord(1, "peter", "0430111002", 1);
        AddressBookRecord inputRecord = new AddressBookRecord(6, "peter", "0430111002", 1);
        AddressBookRecord savedRecord = repo.saveRecord(inputRecord);
        assertThat(savedRecord).isEqualTo(record);
    }

    @Test
    public void removeRecord() {

        Long key = keyGenerator.get();
        AddressBookRecord record1 = new AddressBookRecord(key, "peter", "0430111002", 1);
        repo.saveRecord(record1);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record2 = new AddressBookRecord(key, "donald", "0435495021", 1);
        repo.saveRecord(record2);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record3 = new AddressBookRecord(key, "dick", "0402124587", 1);
        repo.saveRecord(record3);

        repo.removeRecord(1L);
        Optional<AddressBookRecord> recordOptional = repo.findRecordById(1L);

        assertThat(recordOptional).isEmpty();
    }

    @Test
    public void findRecordById() {
        Long key = keyGenerator.get();
        AddressBookRecord record1 = new AddressBookRecord(key, "peter", "0430111002", 1);
        repo.saveRecord(record1);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record2 = new AddressBookRecord(key, "donald", "0435495021", 1);
        repo.saveRecord(record2);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record3 = new AddressBookRecord(key, "dick", "0402124587", 1);
        repo.saveRecord(record3);

        Optional<AddressBookRecord> recordOptional = repo.findRecordById(2L);
        assertThat(recordOptional).isNotEmpty().hasValue(record2);
    }

    @Test
    public void findRecordById_NonExistingRecord() {
        Long key = keyGenerator.get();
        AddressBookRecord record1 = new AddressBookRecord(key, "peter", "0430111002", 1);
        repo.saveRecord(record1);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record2 = new AddressBookRecord(key, "donald", "0435495021", 1);
        repo.saveRecord(record2);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record3 = new AddressBookRecord(key, "dick", "0402124587", 1);
        repo.saveRecord(record3);

        Optional<AddressBookRecord> recordOptional = repo.findRecordById(6L);
        assertThat(recordOptional).isEmpty();
    }


    @Test
    public void findAllRecordsByAbid() {
        Long key = keyGenerator.get();
        AddressBookRecord record1 = new AddressBookRecord(key, "peter", "0430111002", 1L);
        repo.saveRecord(record1);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record2 = new AddressBookRecord(key, "donald", "0435495021", 1L);
        repo.saveRecord(record2);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record3 = new AddressBookRecord(key, "dick", "0402124587", 1L);
        repo.saveRecord(record3);

        List<AddressBookRecord> records = repo.findAllRecordsByAbid(1L);
        assertThat(records).isNotEmpty().hasSize(3).extracting("id", "name", "phone", "abid")
                .contains(tuple(1L, "peter", "0430111002", 1L),
                        tuple(2L, "donald", "0435495021", 1L),
                        tuple(3L, "dick", "0402124587", 1L));
    }

    @Test
    public void findAllRecordsByAbid_NonExistingAbid() {
        List<AddressBookRecord> records = repo.findAllRecordsByAbid(6L);
        assertThat(records).isEmpty();
    }


    @Test
    public void findAllRecordsByAbids() {
        Long key = keyGenerator.get();
        AddressBookRecord record1 = new AddressBookRecord(key, "peter", "0430111002", 1L);
        repo.saveRecord(record1);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record2 = new AddressBookRecord(key, "donald", "0435495021", 1L);
        repo.saveRecord(record2);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record3 = new AddressBookRecord(key, "dick", "0402124587", 1L);
        repo.saveRecord(record3);

        key = keyGenerator.incrementAndGet();
        AddressBookRecord record4 = new AddressBookRecord(key, "tom", "0422484920", 2L);
        repo.saveRecord(record4);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record5 = new AddressBookRecord(key, "sam", "0430823002", 2L);
        repo.saveRecord(record5);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record6 = new AddressBookRecord(key, "larry", "0435498247", 2L);
        repo.saveRecord(record6);

        List<AddressBookRecord> records = repo.findAllRecordsByAbids(Arrays.asList(1L, 2L));
        assertThat(records).isNotEmpty().hasSize(6).extracting("id", "name", "phone", "abid")
                .contains(tuple(1L, "peter", "0430111002", 1L),
                        tuple(2L, "donald", "0435495021", 1L),
                        tuple(3L, "dick", "0402124587", 1L),
                        tuple(4L, "tom", "0422484920", 2L),
                        tuple(5L, "sam", "0430823002", 2L),
                        tuple(6L, "larry", "0435498247", 2L));
    }


    @Test
    public void findAllRecordsByAbids_EmptyAbids() {
        assertThatThrownBy(() ->
                repo.findAllRecordsByAbids(null)).hasMessage("addressBook Ids is required");
    }

    @Test
    public void findAllRecordsByAbids_NonExistingAbids() {
        List<AddressBookRecord> records = repo.findAllRecordsByAbids(Arrays.asList(1L, 2L));
        assertThat(records).isEmpty();
    }


    @Test
    public void findRecord() {
        Long key = keyGenerator.get();
        AddressBookRecord record1 = new AddressBookRecord(key, "peter", "0430111002", 1L);
        repo.saveRecord(record1);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record2 = new AddressBookRecord(key, "donald", "0435495021", 1L);
        repo.saveRecord(record2);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record3 = new AddressBookRecord(key, "dick", "0402124587", 1L);
        repo.saveRecord(record3);

        key = keyGenerator.incrementAndGet();
        AddressBookRecord record4 = new AddressBookRecord(key, "tom", "0422484920", 2L);
        repo.saveRecord(record4);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record5 = new AddressBookRecord(key, "sam", "0430823002", 2L);
        repo.saveRecord(record5);
        key = keyGenerator.incrementAndGet();
        AddressBookRecord record6 = new AddressBookRecord(key, "larry", "0435498247", 2L);
        repo.saveRecord(record6);

        List<AddressBookRecord> records = repo.findRecord(String.valueOf(1));
        assertThat(records).isNotEmpty().hasSize(3).extracting("id", "name", "phone", "abid")
                .contains(tuple(1L, "peter", "0430111002", 1L),
                        tuple(2L, "donald", "0435495021", 1L),
                        tuple(3L, "dick", "0402124587", 1L));

        records = repo.findRecord("d");
        assertThat(records).isNotEmpty().hasSize(2).extracting("id", "name", "phone", "abid")
                .contains(tuple(2L, "donald", "0435495021", 1L),
                        tuple(3L, "dick", "0402124587", 1L));

        records = repo.findRecord("02");
        assertThat(records).isNotEmpty().hasSize(4).extracting("id", "name", "phone", "abid")
                .contains(tuple(1L, "peter", "0430111002", 1L),
                        tuple(2L, "donald", "0435495021", 1L),
                        tuple(3L, "dick", "0402124587", 1L),
                        tuple(5L, "sam", "0430823002", 2L));

        records = repo.findRecord("7");
        assertThat(records).isNotEmpty().hasSize(2).extracting("id", "name", "phone", "abid")
                .contains(tuple(3L, "dick", "0402124587", 1L),
                        tuple(6L, "larry", "0435498247", 2L));
    }
}
