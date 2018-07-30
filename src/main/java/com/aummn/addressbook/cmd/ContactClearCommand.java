package com.aummn.addressbook.cmd;

import com.aummn.addressbook.mediator.AddressBookMediator;

/**
 * A <code>Command</code> object for clearing the displayed data on screen.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class ContactClearCommand implements Command {

    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookMediator addressBookMediator;


    /**
     * Creates a <code>ContactClearCommand</code> object.
     *
     * @param addressBookMediator an object which controls the communication among
     * all user interface elements
     */
    public ContactClearCommand(AddressBookMediator addressBookMediator) {
        this.addressBookMediator = addressBookMediator;
    }

    public void execute() {
        addressBookMediator.clearDisplayedData();
    }
    
}