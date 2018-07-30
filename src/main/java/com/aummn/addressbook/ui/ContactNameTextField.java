package com.aummn.addressbook.ui;

import com.aummn.addressbook.mediator.AddressBookMediator;
import javax.swing.*;


/**
 * A <code>JTextField</code> for contact name input.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class ContactNameTextField extends JTextField {

    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookMediator addressBookMediator;

    /**
     * Creates a <code>ContactNameTextField</code> object and
     * registers itself with the <code>AddressBookMediator</code>.
     *
     * @param addressBookMediator an object which controls the communication among
     * all user interface elements
     */
    public ContactNameTextField(AddressBookMediator addressBookMediator) {
        
        this.addressBookMediator = addressBookMediator;
        addressBookMediator.registerContactNameTextField(this);
        
    }
    
}