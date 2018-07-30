package com.aummn.addressbook.action;

import javax.swing.*;


/**
 * An <code>Action</code> object used to search address books.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookSearchAction extends CommandAction {

    /**
     * Creates a <code>AddressBookSearchAction</code> object with specified name, image,
     * description, mnemonic key, accelerator key.
     *
     * @param name the name of the action
     * @param image the icon for the action
     * @param description the short description of the action
     * @param mnemonic the mnemonic key for the action
     * @param acceleratorKey the <code>KeyStroke</code> to be used as
     * the accelerator key for the action
     */
    public AddressBookSearchAction(String name, Icon image, String description,
                                   Integer mnemonic, KeyStroke acceleratorKey) {
        
        super(name, image, description, mnemonic, acceleratorKey);
    }
    
}