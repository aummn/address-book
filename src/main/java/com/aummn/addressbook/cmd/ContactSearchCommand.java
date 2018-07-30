package com.aummn.addressbook.cmd;

import com.aummn.addressbook.mediator.AddressBookMediator;

/**
 * A <code>Command</code> object for searching contacts.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class ContactSearchCommand implements Command {

    /**
     * The object used to communicate with all UI elements.
     * Every UI element registers with it and delegates operations to it.
     */
    private AddressBookMediator addressBookMediator;

    /**
     * Creates a <code>ContactSearchCommand</code> object and
     * registers itself with the <code>AddressBookMediator</code>.
     *
     * @param addressBookMediator A object which controls the communication among
     * all UI elements.
     */
    public ContactSearchCommand(AddressBookMediator addressBookMediator) {
        
        this.addressBookMediator = addressBookMediator;
    }

    public void execute() {

        addressBookMediator.searchContact();
        
    }
    
}