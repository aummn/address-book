package com.aummn.addressbook.action;

import com.aummn.addressbook.mediator.AddressBookInfoMediator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * An <code>KeyListener</code> object detects key events and is used for searching address books.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookNameKeyListener implements KeyListener {

    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookInfoMediator addressBookMediator;

    /**
     * Creates a <code>AddressBookNameKeyListener</code> object.
     *
     * @param addressBookMediator an object which controls the communication among
     * all user interface elements
     */
    public AddressBookNameKeyListener(AddressBookInfoMediator addressBookMediator) {
        this.addressBookMediator = addressBookMediator;
    }

    public void keyPressed(KeyEvent keyEvent) {}

    public void keyReleased(KeyEvent keyEvent) {
        addressBookMediator.searchAddressBook();
    }

    public void keyTyped(KeyEvent keyEvent) {}
    
}