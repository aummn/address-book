package com.aummn.addressbook.ui;

import com.aummn.addressbook.action.WindowDisposer;
import com.aummn.addressbook.mediator.AddressBookInfoMediator;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * This class provides a dialog window to add an address book.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookAddDialog extends JDialog {

    /**
     * the name of an address book
     */
    private String addressBookName;

    /**
     * a text field used to input the address book name
     */
    private JTextField addressBookNameTextField;

    /**
     * the communication manager class for UI elements interaction
     */
    private AddressBookInfoMediator addressBookInfoMediator;



    /**
     * Creates a <code>AddressBookAddDialog</code> object
     */
    public AddressBookAddDialog(AddressBookInfoMediator addressBookInfoMediator) {

        super();
        setModal(true);
        setResizable(false);
        this.setTitle("Add Address Book Dialog");
        this.addressBookInfoMediator = addressBookInfoMediator;
        setupGUI();
    }
    
    /**
     * Shows a dialog to add an address book.
     */
    public void setupGUI() {
        
        /* sets up display elements */
        JLabel addressBookNameLabel = new JLabel("Address book name: ");
        
        addressBookNameTextField = new JTextField(addressBookName);
        addressBookNameTextField.setColumns(16);

        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
        
        JButton addressBookAddButton = new JButton("  Add  ");
        addressBookAddButton.setMnemonic(KeyEvent.VK_O);
        AddressBookAddButtonListener addressBookAddButtonListener = new AddressBookAddButtonListener(this);
        addressBookAddButton.addActionListener(addressBookAddButtonListener);
        addressBookAddButton.registerKeyboardAction(addressBookAddButtonListener, keyStroke, JComponent.WHEN_FOCUSED);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setMnemonic(KeyEvent.VK_C);
        WindowDisposer cancelButtonListener = new WindowDisposer(this);
        cancelButton.addActionListener(cancelButtonListener);
        cancelButton.registerKeyboardAction(cancelButtonListener, keyStroke, JComponent.WHEN_FOCUSED);

        /* sets up the overall layout of input dialog */
        Container contentPane = getContentPane();
        
        JPanel contentPanel = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(contentPanel,BorderLayout.CENTER);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new TitledBorder(""));
        JPanel buttonPanel = new JPanel();
        
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        /* fills in the main panel */
        GridBagLayout gridBagForMainPanel = new GridBagLayout();
        GridBagConstraints gridConstrainsForMainPanel = new GridBagConstraints();
        
        gridBagForMainPanel.columnWidths = new int [] {1, 1};
        gridBagForMainPanel.rowHeights = new int [] {1, 1};
        gridBagForMainPanel.columnWeights = new double [] {40, 60};
        gridBagForMainPanel.rowWeights = new double [] {50, 50};
        
        mainPanel.setLayout(gridBagForMainPanel);

        gridConstrainsForMainPanel.gridwidth = 1;
        gridConstrainsForMainPanel.anchor = GridBagConstraints.EAST;
        mainPanel.add(addressBookNameLabel, gridConstrainsForMainPanel);
        gridConstrainsForMainPanel.anchor = GridBagConstraints.WEST;
        mainPanel.add(addressBookNameTextField, gridConstrainsForMainPanel);
        
        /* fills in the button panel */
        buttonPanel.setBorder(new TitledBorder(""));
        buttonPanel.add(addressBookAddButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(cancelButton);
        
        pack();
        setLocationRelativeTo(null);
        
    }

    /**
     * This class used to handle address book creation triggered by Add button clicking.
     *
     */
    public class AddressBookAddButtonListener implements ActionListener {
        
        /* a <code>Window</code> object to be disposed */
        private Window window;
        
        /**
         * Creates a new <code>AddressBookAddButtonListener</code> object
         */
        public AddressBookAddButtonListener(Window window) {
            
            this.window = window;
            
        }
        
        public void actionPerformed(ActionEvent actionEvent) {
            addressBookName = addressBookNameTextField.getText().trim();
            
            /* address book name is empty */
            if (addressBookName.equals("")) {
                
                JOptionPane.showMessageDialog(null,
                "Address book name can't be empty!");
                
            } else {  // add an address book

                try {
                    addressBookInfoMediator.addAddressBookInfo(addressBookName);

                    /* closes this dialog */
                    window.setVisible(false);
                    window.dispose();

                } catch(IllegalArgumentException iae) {

                    JOptionPane.showMessageDialog(null,
                    "application runtime error!");
                }
            }
            addressBookNameTextField.requestFocus();
            addressBookNameTextField.selectAll();
        }
        
    }
    
    
}