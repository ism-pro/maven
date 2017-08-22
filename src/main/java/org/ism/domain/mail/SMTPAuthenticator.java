/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.domain.mail;

import javax.mail.PasswordAuthentication;
import org.ism.entities.admin.Mailsender;

/**
 * <h1>SMTPAuthenticator</h1>
 * <p>
 * This class is used for authentication purposes
 * </p>
 *
 *
 * @author r.hendrick
 */
public class SMTPAuthenticator extends javax.mail.Authenticator {

    private String username;// = "raphaelhendrick@gmail.com";
    private String password;// = "lshi170786;"
    
    
    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

    /**
     * Convenient method that allow to take user and password directly form mail
     * sender.
     * @param mailsender variable from which to take user and password
     */
    public void fromMailsender(Mailsender mailsender){
        setUsername(mailsender.getMsUsername());
        setPassword(mailsender.getMsPassword());
    }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
