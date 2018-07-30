package com.aummn.addressbook.ui;

import com.aummn.addressbook.action.*;
import com.aummn.addressbook.cmd.*;
import com.aummn.addressbook.mediator.AddressBookMediator;
import com.aummn.addressbook.model.AddressBookDataTableModel;
import com.aummn.addressbook.model.AddressBookInfoItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * This class returns a <code>JFrame</code> object containing the address book UI.
 *
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookDialog extends JDialog {

    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookMediator addressBookMediator;

    /**
     * Creates a <code>AddressBookDialog</code> object
     *
     * @param title a string to be the title of this <code>JFrame</code>
     * @param addressBookMediator the UI elements communication mediator
     *
     */
    public AddressBookDialog(String title, AddressBookMediator addressBookMediator) {
        super();
        setModal(false);
        setResizable(true);
        this.setTitle(title);
        this.addressBookMediator = addressBookMediator;
        setupGUI();
    }

    /**
     * Lays out the user interface.
     */
    public void setupGUI() {
        
        /* sets up actions to generate buttons */
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
        ContactAddCommand contactAddCommand = new ContactAddCommand(addressBookMediator);
        ContactAddAction contactAddAction = new ContactAddAction("Add", null,
        "add a contact", KeyEvent.VK_A, null);

        contactAddAction.setCommand(contactAddCommand);

        ContactRemoveCommand contactRemoveCommand = new ContactRemoveCommand(addressBookMediator);
        ContactRemoveAction contactRemoveAction = new ContactRemoveAction("Remove", null,
                "remove address books", KeyEvent.VK_R, null);

        contactRemoveAction.setCommand(contactRemoveCommand);

        
        ContactClearCommand contactClearCommand = new ContactClearCommand(addressBookMediator);
        ContactClearAction contactClearAction = new ContactClearAction("Clear", null,
                "clears displayed data on screen", KeyEvent.VK_E, null);
        contactClearAction.setCommand(contactClearCommand);


        ContactShowCommand contactShowCommand = new ContactShowCommand(addressBookMediator);
        ShowContactAction showContactAction = new ShowContactAction("Show Contacts", null,
                "show contacts in an address book", KeyEvent.VK_C, null);
        showContactAction.setCommand(contactShowCommand);

        UniqueContactShowCommand uniqueContactShowCommand = new UniqueContactShowCommand(addressBookMediator);
        ShowUniqueContactAction showUniqueContactAction = new ShowUniqueContactAction("Show Unique Contacts", null,
                "show unique contacts in multiple address books", KeyEvent.VK_U, null);
        showUniqueContactAction.setCommand(uniqueContactShowCommand);

        ContactSearchCommand contactSearchCommand = new ContactSearchCommand(addressBookMediator);
        ContactSearchAction contactSearchAction = new ContactSearchAction("Search", null,
        "search contacts", KeyEvent.VK_S, null);

        contactSearchAction.setCommand(contactSearchCommand);
        
        /* sets up display elements */
        AddressBookDataTable addressBookDataTable = new AddressBookDataTable(addressBookMediator);

        AddressBookDataTableModel dataTableModel = new AddressBookDataTableModel(null,
                AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                AddressBookDataTable.ROW_COUNT,
                AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES.length);

        addressBookDataTable.setModel(dataTableModel);

        ContactNameTextField contactNameTextField = new ContactNameTextField(addressBookMediator);
        contactNameTextField.setColumns(20);
        ContactNameKeyListener contactNameKeyListener = new ContactNameKeyListener(addressBookMediator);
        contactNameTextField.addKeyListener(contactNameKeyListener);

        JList addressBookDataList = new JList();
        Object[] data = this.addressBookMediator.getAddressBookInfoMediator().searchAddressBook(null).stream()
                .map(AddressBookInfoItem::new).toArray();
        addressBookDataList.setListData(data);
        addressBookDataList.setVisibleRowCount(3);

        this.addressBookMediator.registerAddressBookDataList(addressBookDataList);
        JScrollPane addressBookDataListScrollPane = new JScrollPane(addressBookDataList);

        JLabel contactNameLabel = new JLabel("Contact Name: ");
        JLabel addressBookDataListLabel = new JLabel("Address Book: ");
        
        JButton contactSearchButton = new JButton(contactSearchAction);
        contactSearchButton.setIcon(null);
        
        JButton contactAddButton = new JButton(contactAddAction);
        contactAddButton.setIcon(null);

        JButton contactRemoveButton = new JButton(contactRemoveAction);
        contactRemoveButton.setIcon(null);
        
        JButton clearButton = new JButton(contactClearAction);
        clearButton.setIcon(null);

        WindowDisposer closeButtonListener = new WindowDisposer(this);
        JButton closeButton = new JButton("Close");
        closeButton.setMnemonic(KeyEvent.VK_O);
        closeButton.addActionListener(closeButtonListener);
        closeButton.registerKeyboardAction(closeButtonListener, keyStroke, JComponent.WHEN_FOCUSED);

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

        gridBagForNorthPanel.columnWidths = new int [] {1, 1,1,1};
        gridBagForNorthPanel.rowHeights = new int [] {1, 1};
        gridBagForNorthPanel.columnWeights = new double [] {25, 25, 25, 25};
        gridBagForNorthPanel.rowWeights = new double [] {50, 50};
        
        northPanel.setLayout(gridBagForNorthPanel);

        gridConstrainsForNorthPanel.gridwidth = 1;
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.EAST;
        northPanel.add(contactNameLabel, gridConstrainsForNorthPanel);

        gridConstrainsForNorthPanel.anchor = GridBagConstraints.WEST;
        northPanel.add(contactNameTextField, gridConstrainsForNorthPanel);

        gridConstrainsForNorthPanel.gridwidth = GridBagConstraints.REMAINDER;
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.WEST;
        northPanel.add(contactSearchButton, gridConstrainsForNorthPanel);

        gridConstrainsForNorthPanel.gridwidth = 1;
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.EAST;
        northPanel.add(addressBookDataListLabel, gridConstrainsForNorthPanel);
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;

        gridConstrainsForNorthPanel.anchor = GridBagConstraints.WEST;
        northPanel.add(addressBookDataListScrollPane, gridConstrainsForNorthPanel);
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;
        gridConstrainsForNorthPanel.fill = GridBagConstraints.NONE;

        gridConstrainsForNorthPanel.gridwidth = 1;
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.WEST;
        northPanel.add(showContactButton, gridConstrainsForNorthPanel);

        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;
        gridConstrainsForNorthPanel.gridwidth = GridBagConstraints.REMAINDER;
        northPanel.add(showUniqueContactButton, gridConstrainsForNorthPanel);
        gridConstrainsForNorthPanel.fill = GridBagConstraints.NONE;


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
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        centerInSouth.add(contactAddButton, gridConstrainsForCenterInSouthPanel);
        contactAddButton.setIcon(null);

        gridConstrainsForCenterInSouthPanel.gridx = 2;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        centerInSouth.add(contactRemoveButton, gridConstrainsForCenterInSouthPanel);
        contactRemoveButton.setIcon(null);

        gridConstrainsForCenterInSouthPanel.gridx = 3;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        centerInSouth.add(clearButton, gridConstrainsForCenterInSouthPanel);
        clearButton.setIcon(null);

        gridConstrainsForCenterInSouthPanel.gridx = 4;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        centerInSouth.add(closeButton, gridConstrainsForCenterInSouthPanel);
        clearButton.setIcon(null);

        pack();
        contactNameTextField.requestFocusInWindow();
        getRootPane().setDefaultButton(contactSearchButton);
        this.setSize(new Dimension(700, 400));
        this.setLocationRelativeTo(null);
    }
    
}