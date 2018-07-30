package com.aummn.addressbook.mediator;

import com.aummn.addressbook.model.AddressBookInfo;
import com.aummn.addressbook.model.AddressBookInfoDataTableModel;
import com.aummn.addressbook.model.AddressBookInfoItem;
import com.aummn.addressbook.service.AddressBookInfoService;
import com.aummn.addressbook.service.AddressBookInfoServiceImpl;
import com.aummn.addressbook.ui.*;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * This class controls the communication among all elements on the address book info frame.
 * Every element registers with it and delegates operations to it. This class receives the
 * change messages occurred in other classes and notifies the appropriate classes to process them.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class AddressBookInfoMediator {

    private AddressBookInfoDataTable addressBookInfoDataTable;
    private AddressBookInfoDataTableModel dataTableModel;
    private NameTextField nameTextField;
    private AddressBookInfoService addressBookInfoService;
    private AddressBookMediator addressBookMediator;
    private AddressBookDialog addressBookDialog;

    /**
     * Creates a <code>AddressBookInfoMediator</code> object.
     *
     */
    public AddressBookInfoMediator() {
        this.addressBookInfoService = new AddressBookInfoServiceImpl();
        this.dataTableModel = new AddressBookInfoDataTableModel(new ArrayList(),
                AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                AddressBookInfoDataTable.ROW_COUNT, AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length);
    }

    /**
     *  Handles the address book searching triggered by key events or button clicking.
     *
     */
    public void searchAddressBook() {

        String addressBookName = nameTextField.getText();
        List<AddressBookInfo> addressBookInfoList = addressBookInfoService.findAddressBookInfoByName(addressBookName);

        /* constructs a <code>AddressBookInfoDataTableModel</code> object */
        int rowCount = (addressBookInfoList == null)? 0 : addressBookInfoList.size();
        int columnCount = AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length;
        dataTableModel = new AddressBookInfoDataTableModel(addressBookInfoList, AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                rowCount, columnCount);

        /* displayes returned results to the table in user interface */
        addressBookInfoDataTable.setModel(dataTableModel);
        nameTextField.requestFocusInWindow();
    }

    /**
     * Search the address books by name.
     *
     * @param  addressBookName  the name of an address book
     */
    public List<AddressBookInfo> searchAddressBook(String addressBookName) {
        if (addressBookName == null) addressBookName = "";
        return addressBookInfoService.findAddressBookInfoByName(addressBookName);
    }

    /**
     * clear the shown data on screen.
     *
     */
    public void clearDisplayedData() {
        int columnCount = AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length;
        dataTableModel = new AddressBookInfoDataTableModel(new ArrayList(),
                AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                AddressBookInfoDataTable.ROW_COUNT, columnCount);

        /* clears the displayed records in a table */
        addressBookInfoDataTable.setModel(dataTableModel);

        /* clears the content of text fields */
        nameTextField.setText("");
        nameTextField.requestFocusInWindow();
    }

    /**
     * Present the contact management dialog.
     *
     */
    public void showContactDialog() {
        if(this.addressBookDialog == null) {
            this.addressBookDialog = new AddressBookDialog("Address Book", this.getAddressBookMediator());
        }
        addressBookMediator.clearDisplayedData();
        addressBookMediator.showDialog(this.addressBookDialog);
    }

    /**
     * Show contacts in selected address books.
     *
     */
    public void showContact() {
        List<Long> selectedAddressBookInfoIdList = new ArrayList<>();
        int[] rowNumbers = addressBookInfoDataTable.getSelectedRows();

        if (rowNumbers.length == 0) {
            JOptionPane.showMessageDialog(null, "Please select a row in a table!");
        } else {
            List<AddressBookInfo> dataModelList = dataTableModel.getList();
            Arrays.stream(rowNumbers)
                    .forEach(rowNumber -> selectedAddressBookInfoIdList.add(dataModelList.get(rowNumber).getId()));

            if(this.addressBookDialog == null) {
                this.addressBookDialog = new AddressBookDialog("Address Book", this.getAddressBookMediator());
            }

            // show contacts in address books
            addressBookMediator.showContact(selectedAddressBookInfoIdList, rowNumbers);
            addressBookMediator.showDialog(this.addressBookDialog);
        }
    }

    /**
     * Show unique contacts in selected address books.
     *
     */
    public void showUniqueContact() {
        List<Long> selectedAddressBookInfoIdList = new ArrayList<>();
        int[] rowNumbers = addressBookInfoDataTable.getSelectedRows();

        if (rowNumbers.length == 0) {
            JOptionPane.showMessageDialog(null, "Please select a row in a table!");
        } else {
            List<AddressBookInfo> dataModelList = dataTableModel.getList();
            Arrays.stream(rowNumbers)
                    .forEach(rowNumber -> selectedAddressBookInfoIdList.add(dataModelList.get(rowNumber).getId()));

            if(this.addressBookDialog == null) {
                this.addressBookDialog = new AddressBookDialog("Address Book", this.getAddressBookMediator());
            }

            // show unique contacts in address books
            addressBookMediator.showUniqueContact(selectedAddressBookInfoIdList, rowNumbers);
            addressBookMediator.showDialog(this.addressBookDialog);
        }
    }

    /**
     * Presents the adding address dialog.
     *
     */
    public void addAddressBook() {
        AddressBookAddDialog dialog = new AddressBookAddDialog(this);
        dialog.setVisible(true);
    }

    /**
     * Add an address book.
     *
     */
    public void addAddressBookInfo(String addressBookName) {
        AddressBookInfo addressBookInfo = addressBookInfoService.addAddressBookInfo(new AddressBookInfo(addressBookName));
        dataTableModel.getList().add(addressBookInfo);

        int rowCount = (dataTableModel.getList() == null)? 0 : dataTableModel.getList().size();
        int columnCount = AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length;

        dataTableModel = new AddressBookInfoDataTableModel(dataTableModel.getList(),
                AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                rowCount, columnCount);
        addressBookInfoDataTable.setModel(dataTableModel);
        this.getAddressBookMediator().updateAddressBookDataList();
    }

    /**
     * Remove selected address books.
     *
     */
    public void removeAddressBook() {
        List<AddressBookInfo> selectedAddressBookInfoList = new ArrayList<>();
        List<AddressBookInfo> successfullyRemovedAddressBookInfoList = new ArrayList<>();

        int[] rowNumbers = addressBookInfoDataTable.getSelectedRows();
        List<AddressBookInfo> dataModelList = dataTableModel.getList();

        if (rowNumbers.length == 0) {
            JOptionPane.showMessageDialog(null, "Please select a row in a table!");
        } else {
            Arrays.stream(rowNumbers)
                    .forEach(rowNumber -> selectedAddressBookInfoList.add(dataModelList.get(rowNumber)));

            // remove address book info item from the UI and data store
            selectedAddressBookInfoList.stream()
                    .forEach(info -> {
                        dataModelList.remove(info);
                        Optional<AddressBookInfo> removedAddressBookInfo = addressBookInfoService.removeAddressBookInfo(info);
                        removedAddressBookInfo.ifPresent(i -> successfullyRemovedAddressBookInfoList.add(i));
                    });

            int rowCount = (dataModelList == null)? 0 : dataModelList.size();
            int columnCount = AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length;
            dataTableModel = new AddressBookInfoDataTableModel(dataModelList,
                    AddressBookInfoDataTable.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                    rowCount, columnCount);
            addressBookInfoDataTable.setModel(dataTableModel);
            this.getAddressBookMediator().updateAddressBookDataList();
        }
        nameTextField.requestFocusInWindow();
    }
    
    /**
     * Registers the <code>DataTable</code> object with this mediator.
     *
     * @param addressBookDataTable a <code>DataTable</code> object used to display address books
     *
     */
    public void registerAddressBookInfoDataTable(AddressBookInfoDataTable addressBookDataTable) {
        
        this.addressBookInfoDataTable = addressBookDataTable;
        
    }
    
    /**
     * Registers the <code>NameTextField</code> object with this mediator.
     *
     * @param nameTextField a <code>NameTextField</code> object used to accept an address book name
     */
    public void registerNameTextField(NameTextField nameTextField) {
        
        this.nameTextField = nameTextField;
        
    }


    public AddressBookMediator getAddressBookMediator() {
        return addressBookMediator;
    }

    public void setAddressBookMediator(AddressBookMediator addressBookMediator) {
        this.addressBookMediator = addressBookMediator;
    }
}