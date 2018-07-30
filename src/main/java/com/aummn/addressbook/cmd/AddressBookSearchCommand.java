package com.aummn.addressbook.cmd;

import com.aummn.addressbook.mediator.AddressBookInfoMediator;

/**
 * A <code>Command</code> object for searching address books.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookSearchCommand implements Command {

    /**
     * The object used to communicate with all UI elements.
     * Every UI element registers with it and delegates operations to it.
     */
    private AddressBookInfoMediator addressBookInfoMediator;

    /**
     * Creates a <code>AddressBookSearchCommand</code> object and
     * registers itself with the <code>AddressBookInfoMediator</code>.
     *
     * @param addressBookInfoMediator A object which controls the communication among
     * all UI elements.
     */
    public AddressBookSearchCommand(AddressBookInfoMediator addressBookInfoMediator) {
        
        this.addressBookInfoMediator = addressBookInfoMediator;
    }

    public void execute() {

        addressBookInfoMediator.searchAddressBook();
        
    }
    
}