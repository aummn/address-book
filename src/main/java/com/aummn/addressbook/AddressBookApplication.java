package com.aummn.addressbook;

import com.aummn.addressbook.ui.AddressBookInfoFrame;

import javax.swing.*;

/**
 * This class runs the address book app and presents the user interface.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookApplication {
    
    public static void main(String argv[]) {
        /* gets the user interface */
        JFrame addressBookInfoFrame = new AddressBookInfoFrame("Address Book Info");
        addressBookInfoFrame.setVisible(true);
        
    }
    
}
