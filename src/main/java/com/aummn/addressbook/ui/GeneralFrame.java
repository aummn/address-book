package com.aummn.addressbook.ui;

import javax.swing.*;

/**
 * This class aims to provide the ability that releases system resources
 * associated with a frame to a <code>JFrame</code> object when clicking the
 * close button of that frame. A <code>WindowCloser</code> object is
 * attached with the frame and catches the <code>WindowClosing</code>event.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public class GeneralFrame extends JFrame {
    
    /**
     * Creates a new <code>GeneralFrame</code> with the specified title.
     * This frame is attached with a <code>WindowCloser</code> which will
     * relese system resources associated with the frame when detecting
     * closing of that frame.
     *
     * @param title a <code>String</code> will be displayed on the title bar of
     * a <code>JFrame</code> object
     */
    public GeneralFrame(String title) {
        
        super(title);
        addWindowListener(new WindowCloser());
        
    }
    
}