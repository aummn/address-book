package com.aummn.addressbook.cmd;

import com.aummn.addressbook.mediator.AddressBookInfoMediator;

/**
 * A <code>Command</code> object for clearing the displayed data on screen.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class ClearCommand implements Command {
    
    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookInfoMediator addressBookInfoMediator;
    
    
    /**
     * Creates a <code>ClearCommand</code> object.
     *
     * @param addressBookInfoMediator an object which controls the communication among
     * all user interface elements
     */
    public ClearCommand(AddressBookInfoMediator addressBookInfoMediator) {
        this.addressBookInfoMediator = addressBookInfoMediator;
    }

    public void execute() {
        addressBookInfoMediator.clearDisplayedData();
    }
    
}