package com.aummn.addressbook.ui;

import com.aummn.addressbook.mediator.AddressBookInfoMediator;
import javax.swing.*;


/**
 * A <code>JTable</code> used to display address books.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookInfoDataTable extends JTable {

    public static String[] ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES = {"id", "name"};
    public static final int ROW_COUNT = 20;

    /**
     * Creates a <code>AddressBookInfoDataTable</code> object and
     * registers itself with the <code>AddressBookInfoMediator</code>.
     *
     * @param addressBookInfoMediator an object which controls the communication among
     * all user interface elements
     */
    public AddressBookInfoDataTable(AddressBookInfoMediator addressBookInfoMediator) {

        addressBookInfoMediator.registerAddressBookInfoDataTable(this);
        
    }
    
}