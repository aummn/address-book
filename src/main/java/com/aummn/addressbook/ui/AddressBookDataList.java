package com.aummn.addressbook.ui;

import com.aummn.addressbook.mediator.AddressBookMediator;
import javax.swing.*;


/**
 * A <code>JList</code> used to display address books.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookDataList extends JList {

    public static final int ROW_COUNT = 20;

    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookMediator addressBookMediator;

    /**
     * Creates a <code>AddressBookDataList</code> object and
     * registers itself with the <code>AddressBookMediator</code>.
     *
     * @param addressBookMediator an object which controls the communication among
     * all user interface elements
     */
    public AddressBookDataList(AddressBookMediator addressBookMediator) {
        
        this.addressBookMediator = addressBookMediator;
        addressBookMediator.registerAddressBookDataList(this);
        
    }
    
}