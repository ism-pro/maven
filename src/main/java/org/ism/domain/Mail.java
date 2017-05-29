/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.domain;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.ism.jsf.util.JsfUtil;

/**
 * <h1>Mail</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
@ManagedBean(name = "mailController")
@SessionScoped
public class Mail {

    private String to="raphaelhendrick@gmail.com";
    private String from="raphaelhendrick@gmail.com";
    private String message="Hello Java Mail Message";
    private String subject="HELLO JavaMail API Subject ";
    private String smtpServ="smtp.gmail.com";
/*
     <f:param name="#{mailController.to}" value="to" />
                                <f:param name="#{mailController.from}" value="raphaelhendrick@gmail.com" />
                                <f:param name="#{mailController.smtpServ}" value="smtp.gmail.com" />
                                <f:param name="#{mailController.subject}" value="Java Mail" />
                                <f:param name="#{mailController.message}" value="Un Message" />
    */
    public int sendMail() {
        try {
            Properties props = System.getProperties();
            // -- Attaching to default Session, or we could start a new one --
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtpServ);
            props.put("mail.smtp.auth", "true");
            Authenticator auth = new SMTPAuthenticator();
            
            Session session = Session.getInstance(props, auth);
            // -- Create a new message --
            Message msg = new MimeMessage(session);
            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            msg.setText(message);
            // -- Set some other header information --
            msg.setHeader("MyMail", "Mr. XYZ");
            msg.setSentDate(new Date());
            // -- Send the message --
            Transport.send(msg);
            System.out.println("Message sent to" + to + " OK.");
            JsfUtil.addSuccessMessage("Sending Mail", "Send mail to " + to + " OK.");
            return 0;
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Sending Mail", "exception on sending email "  + ex);
            ex.printStackTrace();
            System.out.println("Exception " + ex);
            return -1;
        }
    }

// Also include an inner class that is used for authentication purposes
    private class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "raphaelhendrick@gmail.com";           // specify your email id here (sender's email id)
            String password = "lshi170786";                                      // specify your password here
            return new PasswordAuthentication(username, password);
        }
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the smtpServ
     */
    public String getSmtpServ() {
        return smtpServ;
    }

    /**
     * @param smtpServ the smtpServ to set
     */
    public void setSmtpServ(String smtpServ) {
        this.smtpServ = smtpServ;
    }

}
