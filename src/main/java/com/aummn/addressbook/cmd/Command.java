package com.aummn.addressbook.cmd;


/**
 * This interface is implemented by individual <code>Command</code> object.
 * It has an <code>execute</code> method that is called
 * when an action occurs on that object.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public interface Command {
    
    /**
     * Executes the actual task specified by <code>Command</code> object.
     */
    public void execute();
    
}