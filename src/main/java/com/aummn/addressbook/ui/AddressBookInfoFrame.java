package com.aummn.addressbook.ui;

import com.aummn.addressbook.action.*;
import com.aummn.addressbook.cmd.*;
import com.aummn.addressbook.mediator.AddressBookInfoMediator;
import com.aummn.addressbook.mediator.AddressBookMediator;
import com.aummn.addressbook.model.AddressBookInfoDataTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * This class returns a <code>JFrame</code> containing the address book UI.
 *
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookInfoFrame extends GeneralFrame {
    
    /**
     * An object used to communicate with all user interface elements on address book info frame
     */
    private AddressBookInfoMediator addressBookInfoMediator;
    
    
    /**
     * Creates a <code>AddressBookInfoFrame</code> object
     *
     * @param title a string to be the title of this <code>JFrame</code>
     *
     */
    public AddressBookInfoFrame(String title) {
        super(title);
        this.addressBookInfoMediator = new AddressBookInfoMediator();
        this.addressBookInfoMediator.setAddressBookMediator(new AddressBookMediator(this.addressBookInfoMediator));
        setupGUI();
    }
    
    /**
     * Lays out the user interface.
     */
    public void setupGUI() {
        
        /* sets up actions to generate buttons */

        AddressBookAddCommand addressBookAddCommand = new AddressBookAddCommand(addressBookInfoMediator);
        AddressBookAddAction addressBookAddAction = new AddressBookAddAction("Add Address Book", null,
        "add an address book", KeyEvent.VK_A, null);
        addressBookAddAction.setCommand(addressBookAddCommand);

        AddressBookRemoveCommand addressBookRemoveCommand = new AddressBookRemoveCommand(addressBookInfoMediator);
        AddressBookRemoveAction addressBookRemoveAction = new AddressBookRemoveAction("Remove Address Book", null,
                "remove address books", KeyEvent.VK_R, null);
        addressBookRemoveAction.setCommand(addressBookRemoveCommand);


        AddressBookShowContactCommand showContactCommand = new AddressBookShowContactCommand(addressBookInfoMediator);
        ShowContactAction showContactAction = new ShowContactAction("Show Contacts", null,
                "show contacts in an address book", KeyEvent.VK_C, null);
        showContactAction.setCommand(showContactCommand);

        AddressBookShowUniqueContactCommand showUniqueContactCommand = new AddressBookShowUniqueContactCommand(addressBookInfoMediator);
        ShowUniqueContactAction showUniqueContactAction = new ShowUniqueContactAction("Show Unique Contacts", null,
                "show unique contacts in multiple address books", KeyEvent.VK_U, null);
        showUniqueContactAction.setCommand(showUniqueContactCommand);
        
        ClearCommand clearCommand = new ClearCommand(addressBookInfoMediator);
        ClearAction clearAction = new ClearAction("Clear", null,
        "clears displayed data on screen", KeyEvent.VK_E, null);
        clearAction.setCommand(clearCommand);

        AddressBookSearchCommand addressBookSearchCommand = new AddressBookSearchCommand(addressBookInfoMediator);
        AddressBookSearchAction addressBookSearchAction = new AddressBookSearchAction("Search", null,
        "search address books", KeyEvent.VK_S, null);

        addressBookSearchAction.setCommand(addressBookSearchCommand);


        AddressBookContactManagementCommand addressBookContactManagementCommand = new AddressBookContactManagementCommand(addressBookInfoMediator);
        AddressBookContactManagementAction addressBookContactManagementAction = new AddressBookContactManagementAction("Contact Management", null,
                "go to contact management window", KeyEvent.VK_M, null);

        addressBookContactManagementAction.setCommand(addressBookContactManagementCommand);

        /* sets up display elements */
        AddressBookInfoDataTable addressBookDataTable = new AddressBookInfoDataTable(addressBookInfoMediator);

        AddressBookInfoDataTableModel addressBookInfoDataTableModel = new AddressBookInfoDataTableModel(null,
                    AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                    AddressBookInfoDataTable.ROW_COUNT,
                    AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length);

        addressBookDataTable.setModel(addressBookInfoDataTableModel);

        NameTextField nameTextField = new NameTextField(addressBookInfoMediator);
        nameTextField.setColumns(20);
        AddressBookNameKeyListener addressBookNameKeyListener = new AddressBookNameKeyListener(addressBookInfoMediator);
        nameTextField.addKeyListener(addressBookNameKeyListener);
        
        JLabel addressBookNameLabel = new JLabel("Address Book Name: ");
        
        JButton addressBookSearchButton = new JButton(addressBookSearchAction);
        addressBookSearchButton.setIcon(null);


        JButton addressBookContactManagementButton = new JButton(addressBookContactManagementAction);
        addressBookContactManagementButton.setIcon(null);
        
        JButton addressBookAddButton = new JButton(addressBookAddAction);
        addressBookAddButton.setIcon(null);

        JButton addressBookRemoveButton = new JButton(addressBookRemoveAction);
        addressBookRemoveButton.setIcon(null);

        JButton clearButton = new JButton(clearAction);
        clearButton.setIcon(null);

        JButton showContactButton = new JButton(showContactAction);
        showContactButton.setIcon(null);

        JButton showUniqueContactButton = new JButton(showUniqueContactAction);
        showUniqueContactButton.setIcon(null);

        /* sets up the overall layout of user interface */
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPane.add(contentPanel, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        JPanel statusPanel = new JPanel();
        
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        contentPanel.add(statusPanel, BorderLayout.SOUTH);
        
        /* fills in the main panel displayed on screen center area */
        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        /* fills in the north panel in main panel */
        GridBagLayout gridBagForNorthPanel = new GridBagLayout();
        GridBagConstraints gridConstrainsForNorthPanel = new GridBagConstraints();
        
        gridBagForNorthPanel.columnWidths = new int [] {1, 1, 1, 1, 1};
        gridBagForNorthPanel.rowHeights = new int [] {1};
        gridBagForNorthPanel.columnWeights = new double [] {20, 20, 20, 20, 20};
        gridBagForNorthPanel.rowWeights = new double [] {100};
        
        northPanel.setLayout(gridBagForNorthPanel);
        
        gridConstrainsForNorthPanel.gridwidth = 1;
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.EAST;
        northPanel.add(addressBookNameLabel, gridConstrainsForNorthPanel);
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;
        
        gridConstrainsForNorthPanel.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(nameTextField, gridConstrainsForNorthPanel);
        gridConstrainsForNorthPanel.fill = GridBagConstraints.NONE;

        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;
        northPanel.add(addressBookSearchButton, gridConstrainsForNorthPanel);

        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;
        northPanel.add(addressBookContactManagementButton, gridConstrainsForNorthPanel);

        northPanel.setBorder(new TitledBorder(" Search "));
        
        /* fills in the center panel in main panel */
        JScrollPane tableScrollPane = new JScrollPane(addressBookDataTable);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        /* fills in the south panel in main panel */
        JPanel centerInSouth = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(centerInSouth, BorderLayout.CENTER);
        southPanel.setBorder(new TitledBorder("Function"));
        
        /* fills in the center panel in south panel */
        GridBagLayout gridBagForCenterInSouthPanel = new GridBagLayout();
        GridBagConstraints gridConstrainsForCenterInSouthPanel = new GridBagConstraints();
        
        gridBagForCenterInSouthPanel.columnWidths = new int [] {1, 1, 1, 1, 1};
        gridBagForCenterInSouthPanel.rowHeights = new int [] {1};
        gridBagForCenterInSouthPanel.columnWeights = new double [] {20, 20, 20, 20, 20};
        gridBagForCenterInSouthPanel.rowWeights = new double [] {100};
        
        centerInSouth.setLayout(gridBagForCenterInSouthPanel);
        
        gridConstrainsForCenterInSouthPanel.gridwidth = 1;
        gridConstrainsForCenterInSouthPanel.gridx = 1;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.EAST;
        centerInSouth.add(addressBookAddButton, gridConstrainsForCenterInSouthPanel);
        addressBookAddButton.setIcon(null);

        gridConstrainsForCenterInSouthPanel.gridx = 2;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        centerInSouth.add(addressBookRemoveButton, gridConstrainsForCenterInSouthPanel);
        addressBookRemoveButton.setIcon(null);

        gridConstrainsForCenterInSouthPanel.gridx = 3;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.WEST;
        centerInSouth.add(showContactButton, gridConstrainsForCenterInSouthPanel);
        showContactButton.setIcon(null);
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;

        gridConstrainsForCenterInSouthPanel.gridx = 4;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.WEST;
        centerInSouth.add(showUniqueContactButton, gridConstrainsForCenterInSouthPanel);
        showUniqueContactButton.setIcon(null);
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;

        gridConstrainsForCenterInSouthPanel.gridx = 5;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.WEST;
        centerInSouth.add(clearButton, gridConstrainsForCenterInSouthPanel);
        clearButton.setIcon(null);
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        
        pack();
        nameTextField.requestFocusInWindow();
        getRootPane().setDefaultButton(addressBookSearchButton);
        this.setSize(new Dimension(750, 400));
        this.setLocationRelativeTo(null);
    }
    
}