package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import static org.assertj.core.api.Assertions.*;

public class AddressBookRepositoryImplTest {

    private AtomicLong keyGenerator = new AtomicLong(1);
    private AddressBookRepositoryImpl repo;

    @Before
    public void runBeforeEveryTest() {
        repo = new AddressBookRepositoryImpl();
    }

    @After
    public void runAfterEveryTest() {
        repo = null;
    }


    @Test
    public void save()
    {
        Long key = keyGenerator.getAndIncrement();
        AddressBookRecord record = new AddressBookRecord(key, "peter", "0430111002", 1);
        AddressBookRecord savedRecord = repo.save(record);
        assertThat(savedRecord).isEqualTo(record);
    }

    @Test
    public void saveAll()
    {
        Long key = keyGenerator.getAndIncrement();
        AddressBookRecord record1 = new AddressBookRecord(key, "peter", "0430111002", 1);
        key = keyGenerator.getAndIncrement();
        AddressBookRecord record2 = new AddressBookRecord(key, "donald", "0435495021", 1);
        key = keyGenerator.getAndIncrement();
        AddressBookRecord record3 = new AddressBookRecord(key, "dick", "0402124587", 1);

        List<AddressBookRecord> savedRecords = repo.saveAll(Arrays.asList(record1, record2, record3));

        assertThat(savedRecords).hasSize(3).extracting("id", "name", "phone", "abid")
                .contains(tuple(1L, "peter", "0430111002", 1L),
                        tuple(2L, "donald", "0435495021", 1L),
                        tuple(3L, "dick", "0402124587", 1L));
    }

    @Test
    public void saveAll_MissingRecords() {

        assertThatThrownBy(() ->
        { repo.saveAll(null); }).hasMessage("records is required");
    }

}
