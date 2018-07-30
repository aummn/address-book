package com.aummn.addressbook.cmd;


import com.aummn.addressbook.mediator.AddressBookMediator;

/**
 * A <code>Command</code> object for showing unique contacts in address books.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class UniqueContactShowCommand implements Command {

    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookMediator addressBookMediator;

    /**
     * Creates an <code>UniqueContactShowCommand</code> object and
     * registers itself with the <code>AddressBookMediator</code>.
     *
     * @param addressBookMediator An object which controls the communication among
     * all elements.
     */
    public UniqueContactShowCommand(AddressBookMediator addressBookMediator) {
        
        this.addressBookMediator = addressBookMediator;
        
    }

    public void execute() {
        addressBookMediator.showUniqueContact();
    }
    
}