package com.aummn.addressbook.ui;

import com.aummn.addressbook.mediator.AddressBookMediator;
import javax.swing.*;


/**
 * A <code>JTable</code> used to display contacts.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookDataTable extends JTable {

    public static String[] ADDRESS_BOOK_RECORD_FIELD_NAMES = {"id", "name", "phone"};
    public static final int ROW_COUNT = 20;


    /**
     * Creates a <code>AddressBookDataTable</code> object and
     * registers itself with the <code>AddressBookMediator</code>.
     *
     * @param addressBookMediator an object which controls the communication among
     * all user interface elements
     */
    public AddressBookDataTable(AddressBookMediator addressBookMediator) {

        addressBookMediator.registerAddressBookDataTable(this);
        
    }
    
}