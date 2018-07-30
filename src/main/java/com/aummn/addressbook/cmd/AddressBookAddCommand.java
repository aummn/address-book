package com.aummn.addressbook.cmd;


import com.aummn.addressbook.mediator.AddressBookInfoMediator;

/**
 * A <code>Command</code> object for adding address books.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookAddCommand implements Command {

    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookInfoMediator addressBookInfoMediator;


    /**
     * Creates an <code>AddressBookAddCommand</code> object and
     * registers itself with the <code>AddressBookInfoMediator</code>.
     *
     * @param addressBookInfoMediator An object which controls the communication among
     * all elements.
     */
    public AddressBookAddCommand(AddressBookInfoMediator addressBookInfoMediator) {
        
        this.addressBookInfoMediator = addressBookInfoMediator;
        
    }

    public void execute() {
        addressBookInfoMediator.addAddressBook();
    }
    
}