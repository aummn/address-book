package com.aummn.addressbook.repo;

import com.aummn.addressbook.model.AddressBookRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicLong;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
        assertThat(savedRecord, is(record));
    }
}
