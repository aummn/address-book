package com.aummn.addressbook.mediator;

import com.aummn.addressbook.model.AddressBookDataTableModel;
import com.aummn.addressbook.model.AddressBookInfoItem;
import com.aummn.addressbook.model.Contact;
import com.aummn.addressbook.service.AddressBookService;
import com.aummn.addressbook.service.AddressBookServiceImpl;
import com.aummn.addressbook.ui.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * This class controls the communication among all elements on the address book dialog.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookMediator {

    private AddressBookDataTable addressBookDataTable;
    private ContactNameTextField contactNameTextField;
    private AddressBookDataTableModel dataTableModel;
    private AddressBookService addressBookService;
    private AddressBookInfoMediator addressBookInfoMediator;
    private AddressBookDialog addressBookDialog;
    private JList addressBookDataList;

    /**
     * Creates a <code>AddressBookMediator</code> object.
     *
     */
    public AddressBookMediator(AddressBookInfoMediator addressBookInfoMediator) {
        this.addressBookService = new AddressBookServiceImpl();
        this.addressBookInfoMediator = addressBookInfoMediator;
        dataTableModel = new AddressBookDataTableModel(null, AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                AddressBookDataTable.ROW_COUNT, AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES.length);
    }

    /**
     * Search contacts in address books.
     *
     */
    public void searchContact() {

        String contactName = contactNameTextField.getText().trim();
        List<Contact> contactList = addressBookService.findContact(contactName);
        int rowCount = contactList.size();
        int columnCount = AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;

        dataTableModel = new AddressBookDataTableModel(contactList, AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                rowCount, columnCount);

        /* display returned results to the table in user interface */
        addressBookDataTable.setModel(dataTableModel);
        contactNameTextField.requestFocusInWindow();
    }

    /**
     * Show contacts in selected address books.
     *
     */
    public void showContact() {
        List<AddressBookInfoItem> selectedAddressBookInfoList = (List<AddressBookInfoItem>)addressBookDataList.getSelectedValuesList();
        int[] rowNumbers = addressBookDataList.getSelectedIndices();
        if (selectedAddressBookInfoList == null || selectedAddressBookInfoList.size() < 1) {
            JOptionPane.showMessageDialog(null, "Please select an address book!");
        } else {
            List<Long> selectedAddressBookInfoIdList = new ArrayList<>();
            selectedAddressBookInfoList
                    .forEach(item -> selectedAddressBookInfoIdList.add(item.getId()));

            this.showContact(selectedAddressBookInfoIdList, rowNumbers);
        }
    }

    /**
     * Search contacts and populate address book table.
     *
     */
    public void showContact(List<Long> addressBookIds, int[] rowNumbers) {

        List<Contact> contactList = addressBookService.printContacts(addressBookIds);

        /* constructs a <code>AddressBookInfoDataTableModel</code> object */
        int rowCount = (contactList == null)? 0 : contactList.size();
        int columnCount = AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;

        dataTableModel = new AddressBookDataTableModel(contactList, AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                rowCount, columnCount);

        /* display returned results to the table in user interface */
        addressBookDataTable.setModel(dataTableModel);
        addressBookDataList.setSelectedIndices(rowNumbers);
        contactNameTextField.setText("");
        contactNameTextField.requestFocusInWindow();
    }

    /**
     * Handle searching unique contacts in selected address books.
     *
     */
    public void showUniqueContact() {
        List<AddressBookInfoItem> selectedAddressBookInfoList = (List<AddressBookInfoItem>)addressBookDataList.getSelectedValuesList();
        int[] rowNumbers = addressBookDataList.getSelectedIndices();
        if (selectedAddressBookInfoList == null || selectedAddressBookInfoList.size() < 1) {
            JOptionPane.showMessageDialog(null, "Please select an address book!");
        } else {
            List<Long> selectedAddressBookInfoIdList = new ArrayList<>();
            selectedAddressBookInfoList
                    .forEach(item -> selectedAddressBookInfoIdList.add(item.getId()));

            this.showUniqueContact(selectedAddressBookInfoIdList, rowNumbers);
        }
    }

    /**
     * Search unique contacts in selected address books.
     *
     */
    public void showUniqueContact(List<Long> addressBookIds, int[] rowNumbers) {

        List<Contact> contactList = addressBookService.printUniqueContacts(addressBookIds);

        /* constructs a <code>AddressBookInfoDataTableModel</code> object */
        int rowCount = (contactList == null)? 0 : contactList.size();
        int columnCount = AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;

        dataTableModel = new AddressBookDataTableModel(contactList, AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                rowCount, columnCount);

        /* display returned results to the table in user interface */
        addressBookDataTable.setModel(dataTableModel);
        addressBookDataList.setSelectedIndices(rowNumbers);
        contactNameTextField.setText("");
        contactNameTextField.requestFocusInWindow();
    }

    /**
     * Clear displayed data on screen.
     *
     */
    public void clearDisplayedData() {
        int columnCount = AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;
        dataTableModel = new AddressBookDataTableModel(null, AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                AddressBookDataTable.ROW_COUNT, columnCount);

        /* clears the displayed records in a table */
        addressBookDataTable.setModel(dataTableModel);
        addressBookDataList.clearSelection();
        /* clears the content of text fields */
        contactNameTextField.setText("");
        contactNameTextField.requestFocusInWindow();
    }

    /**
     * Present the address book dialog.
     *
     */
    public void showDialog(AddressBookDialog addressBookDialog) {
        this.setAddressBookDialog(addressBookDialog);
        this.addressBookDialog.setVisible(true);
    }

    /**
     * Present the adding contact dialog.
     *
     */
    public void addContact() {
        ContactAddDialog dialog = new ContactAddDialog(this, this.addressBookInfoMediator);
        dialog.setVisible(true);
    }

    /**
     * Add the contact into an address book.
     *
     */
    public void addContact(Contact contact, long addressBookId) {
        Contact addedContact = addressBookService.addContact(contact, addressBookId);
        List dataList =  (dataTableModel.getList() == null) ? new ArrayList() : dataTableModel.getList();
        dataList.add(addedContact);

        int rowCount = (dataList == null)? 0 : dataList.size();
        int columnCount = AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;
        dataTableModel = new AddressBookDataTableModel(dataList, AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                rowCount, columnCount);
        addressBookDataTable.setModel(dataTableModel);
    }

    /**
     * Remove contacts in address books.
     *
     */
    public void removeContact() {
        List<Contact> selectedContactList = new ArrayList<>();
        List<Contact> successfullyRemovedContactList = new ArrayList<>();
        int[] rowNumbers = addressBookDataTable.getSelectedRows();
        List<Contact> dataModelList = (List<Contact>)dataTableModel.getList();

        if (rowNumbers.length == 0) {
            JOptionPane.showMessageDialog(null, "Please select a row in a table!");
        } else {
            // gets the selected contacts
            Arrays.stream(rowNumbers)
                    .forEach(rowNumber -> selectedContactList.add(dataModelList.get(rowNumber)));

            // remove contacts from the ui and data store
            selectedContactList
                    .forEach(contact -> {
                        dataModelList.remove(contact);
                        Optional<Contact> removedContact = addressBookService.removeContact(contact);
                        removedContact.ifPresent(successfullyRemovedContactList::add);
                    });

            // refresh the data table to reflect changes
            int rowCount = (dataModelList == null)? 0 : dataModelList.size();
            int columnCount = AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;
            dataTableModel = new AddressBookDataTableModel(dataModelList, AddressBookDataTable.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                    rowCount, columnCount);
            addressBookDataTable.setModel(dataTableModel);

            if(selectedContactList.size() == successfullyRemovedContactList.size())
                JOptionPane.showMessageDialog(null,"Selected contact has been removed!");

        }
        contactNameTextField.requestFocusInWindow();
    }

    /**
     * Update the address book list data.
     *
     */
    public void updateAddressBookDataList() {
        if(this.addressBookDialog != null) {
            Object[] data = this.getAddressBookInfoMediator().searchAddressBook(null).stream()
                    .map(AddressBookInfoItem::new).toArray();
            this.getAddressBookDataList().setListData(data);
        }
    }

    /**
     * Registers the <code>DataTable</code> object with this mediator.
     *
     * @param addressBookDataTable a <code>DataTable</code> object used to display contacts
     */
    public void registerAddressBookDataTable(AddressBookDataTable addressBookDataTable) {
        
        this.addressBookDataTable = addressBookDataTable;
        
    }

    /**
     * Registers the <code>ContactNameTextField</code> object with this mediator.
     *
     * @param contactNameTextField a <code>ContactNameTextField</code> object used to accept the name of a contact
     */
    public void registerContactNameTextField(ContactNameTextField contactNameTextField) {
        
        this.contactNameTextField = contactNameTextField;
        
    }

    /**
     * Registers the address book list with this mediator.
     *
     * @param addressBookDataList a list containing address books
     */
    public void registerAddressBookDataList(JList addressBookDataList) {
        this.addressBookDataList = addressBookDataList;
    }

    public void setAddressBookDialog(AddressBookDialog addressBookDialog) {
        this.addressBookDialog = addressBookDialog;
    }

    public AddressBookInfoMediator getAddressBookInfoMediator() {
        return addressBookInfoMediator;
    }

    public void setAddressBookInfoMediator(AddressBookInfoMediator addressBookInfoMediator) {
        this.addressBookInfoMediator = addressBookInfoMediator;
    }

    public JList getAddressBookDataList() {
        return addressBookDataList;
    }

    public void setAddressBookDataList(JList addressBookDataList) {
        this.addressBookDataList = addressBookDataList;
    }
}