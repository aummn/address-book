package com.aummn.addressbook.cmd;

/**
 * This interface provides a container to hold <code>command</code>
 * and retrieve <code>command</code>.
 *
 * @author James Jin
 * @version 1.0 25/07/2018
 * @since 1.0
 */
public interface CommandHolder {
    
    /**
     * Stores a command to a object implementing
     * <code>CommandHolder</code> interface.
     * That command will be invoked by the <code>ActionEvent</code>
     *
     * @param comd a object implementing the <code>Command</code> interface
     */
    void setCommand(Command comd);
    
    /**
     * Gets a <code>Command</code> object.
     *
     * @return a <code>Command</code>object for this object
     */
    Command getCommand();
    
}