/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.Destination;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import org.ism.entities.admin.Mailsender;
import org.ism.jsf.util.JsfUtil;
import org.ism.domain.mail.SMTPAuthenticator;

/**
 * <h1>MailService</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
@ManagedBean(name = "mailServiceController", eager = true)
@SessionScoped
public class MailServiceController {

    private String to = "raphaelhendrick@gmail.com";
    //private String from = "raphaelhendrick@gmail.com";
    private String message = "Hello Java Mail Message";
    private String subject = "HELLO JavaMail API Subject ";
    //private String smtpServ = "smtp.gmail.com";
    //private int port = 25;  // Port 25 par défaut

    private SMTPAuthenticator authenticator = new SMTPAuthenticator();

    /*
     <f:param name="#{mailController.to}" value="to" />
                                <f:param name="#{mailController.from}" value="raphaelhendrick@gmail.com" />
                                <f:param name="#{mailController.smtpServ}" value="smtp.gmail.com" />
                                <f:param name="#{mailController.subject}" value="Java MailService" />
                                <f:param name="#{mailController.message}" value="Un Message" />
     */
    //inject connection factory and destination  
    @Resource(name = "connFactory", mappedName = "jms/MailFactory")
    private QueueConnectionFactory qFactory;
    @Resource(name = "jmsQueue", mappedName = "mailQueue")
    private Queue queue;

    @PostConstruct
    protected void initialize() {

    }

    private void initializeMail() {

    }

    /*public void sendMail() {
        try {
            //And asynchronous call is done by sending message that will be asynchronously processed by message driven bean.
            QueueConnection qConn = (QueueConnection) qFactory.createConnection();
            // Get session  
            javax.jms.Session session = qConn.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            // Create the JMS message  
            ObjectMessage msg = session.createObjectMessage();
            // set payload  
            MailData data = new MailData("reciever@mail.com", "message text");

            msg.setObject(data);
            // Send JMS message  

            session.createProducer((Destination) queue)
                    .send(msg);
            //release resources  
            session.close();

            qConn.close();

        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Sending Mail", "exception on sending email " + ex);
            ex.printStackTrace();
            JsfUtil.out("Exception " + ex);
        }
    }*/

    /**
     * Convenient method used to send email
     *
     * @return
     */
    public void sendMail(Mailsender mailsender) {
        //mailsender = mailsenderController.getItemsByCompany(company);
        if (mailsender != null) {
            if (mailsender.getMsrequiresAuth()) { // If authentication is required
                authenticator.fromMailsender(mailsender); // create a authentication parameter
            }
        } else {
            JsfUtil.out("Aucun server SMTP n'est définit !!!");
            JsfUtil.addErrorMessage("Aucun server SMTP n'est définit !!!");
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
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            msg.setSubject(subject);
            msg.setText(message);
            // -- Set some other header information --
            msg.setHeader("MyMail", "Mr. XYZ");
            msg.setSentDate(new Date());

            // -- Send the message --
            Transport.send(msg);
            System.out.println("Message sent to " + to + " OK.");
            JsfUtil.addSuccessMessage("Sending Mail", "Send mail to " + to + " OK.");
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Sending Mail", "exception on sending email " + ex);
            ex.printStackTrace();
            System.out.println("Exception " + ex);
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

}
