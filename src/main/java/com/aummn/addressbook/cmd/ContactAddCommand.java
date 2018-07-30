package com.aummn.addressbook.cmd;

import com.aummn.addressbook.mediator.AddressBookMediator;

/**
 * A <code>Command</code> object for adding a contact.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class ContactAddCommand implements Command {

    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookMediator addressBookMediator;


    /**
     * Creates an <code>ContactAddCommand</code> object.
     *
     * @param addressBookMediator An object which controls the communication among
     * all elements.
     */
    public ContactAddCommand(AddressBookMediator addressBookMediator) {
        
        this.addressBookMediator = addressBookMediator;
        
    }

    public void execute() {
        addressBookMediator.addContact();
    }
    
}