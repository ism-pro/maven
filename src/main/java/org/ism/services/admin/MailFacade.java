/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services.admin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.ism.domain.mail.SMTPAuthenticator;
import org.ism.entities.admin.Mailsender;
import org.ism.jsf.util.JsfUtil;

/**
 *
 * @author r.hendrick
 */
@Stateless
@LocalBean
public class MailFacade {

    /**
     * Authenticator used to check email
     */
    private SMTPAuthenticator authenticator = new SMTPAuthenticator();
    
    

    public MailFacade() {
    }

    @Asynchronous
    public Future<String> send(final Mailsender mailsender, final Mail mail) {
        long elapsetime = System.currentTimeMillis();
        if (mailsender != null) {
            if (mailsender.getMsrequiresAuth()) { // If authentication is required
                authenticator.fromMailsender(mailsender); // create a authentication parameter
            }
        } else {
            JsfUtil.out("MailFacade : Aucun server SMTP n'est définit !!!");
            JsfUtil.addErrorMessage("MailFacade : Aucun server SMTP n'est définit !!!");
            return new AsyncResult<>("Mail Facade : error >> mailsender is null !");
        }

        try {
            //Properties props = System.getProperties();
            Properties props = new Properties();
            // -- Attaching to default Session, or we could start a new one --
            props.put("mail.transport.protocol", "smtps");
            //props.put("mail.smtp.starttls.enable", "true");
            //props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.host", mailsender.getMsSmtpsrv());
            props.put("mail.smtp.auth", mailsender.getMsrequiresAuth());
            props.put("mail.smtp.port", mailsender.getMsPort());
            //JsfUtil.out("Port : " + mailsender.getMsPort());

            
            
            
            
            
            
            Session session;
            if (mailsender.getMsrequiresAuth()) {
                session = Session.getInstance(props, authenticator);
            } else {
                session = Session.getInstance(props);
            }

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            InternetAddress from = new InternetAddress(mailsender.getMsAddress());
            from.setPersonal("ISM [" + mailsender.getMsCompany().getCDesignation() + "]");
            msg.setFrom(from);

            // Adresse To
            if (!mail.getTos().isEmpty()) {
                msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toCommaAddress(mail.getTos()), true));
            }
            // Adresse CC.
            if (!mail.getCcs().isEmpty()) {
                msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(toCommaAddress(mail.getCcs()), true));
            }
            // Adresse CCi
            if (!mail.getCcis().isEmpty()) {
                msg.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(toCommaAddress(mail.getCcis()), true));
            }

            //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo(), false));
            msg.setSubject(mail.getSubject());

            msg.setContent(mail.getContent());

            // -- Send the message --
            Transport.send(msg);
            elapsetime = System.currentTimeMillis() - elapsetime;
            JsfUtil.out("Send mail to " + toCommaAddress(mail.getTos()) + " in " + elapsetime/1000 + " s");
            return new AsyncResult<>("Mail Facade : succed >> send !");

        } catch (UnsupportedEncodingException | MessagingException ex) {
            elapsetime = System.currentTimeMillis() - elapsetime;
            JsfUtil.out("Error while sending mail to " + toCommaAddress(mail.getTos()) + " after " + elapsetime/1000 + " s due to " + ex.getMessage());
            return new AsyncResult<>("Mail Facade : error >> with " + ex.getMessage());
        }
    }

    /**
     * Convert a list of email address to a unique string with address separate
     * by comma
     *
     * @param addresses liste of email address
     * @return a unique string of email address separate by comma
     */
    private String toCommaAddress(List<String> addresses) {
        String commaAddress = "";
        for (String address : addresses) {
            if (commaAddress.trim().length() > 0) {
                commaAddress += ",";
            }
            commaAddress += address;
        }
        return commaAddress;
    }
}
