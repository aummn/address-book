package com.aummn.addressbook.ui;

import com.aummn.addressbook.mediator.AddressBookInfoMediator;
import javax.swing.*;


/**
 * A <code>JTextField</code> for address book name input.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class NameTextField extends JTextField {
    
    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookInfoMediator addressBookInfoMediator;
    
    /**
     * Creates a <code>NameTextField</code> object and
     * registers itself with the <code>AddressBookInfoMediator</code>.
     *
     * @param addressBookInfoMediator an object which controls the communication among
     * all user interface elements
     */
    public NameTextField(AddressBookInfoMediator addressBookInfoMediator) {
        
        this.addressBookInfoMediator = addressBookInfoMediator;
        addressBookInfoMediator.registerNameTextField(this);
        
    }
    
}