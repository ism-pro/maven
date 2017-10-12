/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.services.admin;

/**
 * <h1>Mail</h1><br>
 * Mail class  
 *
 * @author r.hendrick
 *
 */
public class Mail {

    
    private String to = "raphaelhendrick@gmail.com";
    private String from = "raphaelhendrick@gmail.com";
    private String message = "Hello Java Mail Message";
    private String subject = "HELLO JavaMail API Subject ";
    private String smtpServ = "smtp.gmail.com";
    private int port = 25;  // Port 25 par défaut

    public Mail() {
    }

    public Mail (String to, String subject, String message){
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
    
    
    
    
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSmtpServ() {
        return smtpServ;
    }

    public void setSmtpServ(String smtpServ) {
        this.smtpServ = smtpServ;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    
    
    
}
