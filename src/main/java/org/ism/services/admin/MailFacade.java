/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services.admin;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
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
            Properties props = System.getProperties();
            // -- Attaching to default Session, or we could start a new one --
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtp.starttls.enable", "true");
            //props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.host", mailsender.getMsSmtpsrv());
            props.put("mail.smtp.auth", mailsender.getMsrequiresAuth());
            props.put("mail.smtp.port", mailsender.getMsPort());
            JsfUtil.out("Port : " + mailsender.getMsPort());

            Session session;
            if (mailsender.getMsrequiresAuth()) {
                session = Session.getInstance(props, authenticator);
            } else {
                session = Session.getInstance(props);
            }

            // -- Create a new message --
            Message msg = new MimeMessage(session); 
            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(mailsender.getMsAddress()));                        // From
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo(), false));

            msg.setSubject(mail.getSubject());
            //msg.setText(mail.getMessage());
            msg.setContent(mail.getMessage(), "text/html");
            // -- Set some other header information --
            msg.setHeader("MyMail", "Mr. XYZ");
            msg.setSentDate(new Date());

            // -- Send the message --
            Transport.send(msg);
            JsfUtil.addSuccessMessage("Sending Mail", "Send mail to " + mail.getTo() + " OK.");
            return new AsyncResult<>("Mail Facade : succed >> send !");
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Sending Mail", "exception on sending email " + ex.getMessage());
            return new AsyncResult<>("Mail Facade : error >> with " + ex.getMessage());
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
