package com.aummn.addressbook.action;

import com.aummn.addressbook.mediator.AddressBookMediator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * An <code>KeyListener</code> object used to search contacts.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class ContactNameKeyListener implements KeyListener {

    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookMediator addressBookMediator;

    /**
     * Creates a <code>ContactNameKeyListener</code> object.
     *
     * @param addressBookMediator an object which controls the communication among
     * all user interface elements
     */
    public ContactNameKeyListener(AddressBookMediator addressBookMediator) {
        this.addressBookMediator = addressBookMediator;
    }

    public void keyPressed(KeyEvent keyEvent) {}

    public void keyReleased(KeyEvent keyEvent) {
        addressBookMediator.searchContact();
    }

    public void keyTyped(KeyEvent keyEvent) {}
    
}