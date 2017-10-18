/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import org.ism.jsf.util.JsfUtil;
import org.ism.services.Util;

/**
 * Mail class provide the way to save informations of email to be send.<br>
 *
 * It is taking care of destination
 *
 * @author r.hendrick
 *
 */
public class Mail {

    /**
     * List of receivers email
     */
    private List<String> tos = new ArrayList<>();
    /**
     * List of receivers copy email
     */
    private List<String> ccs = new ArrayList<>();
    /**
     * List of receivers hiddens copy email
     */
    private List<String> ccis = new ArrayList<>();

    /**
     * Special composition email containing only text which is prefered message
     */
    private String htmlText = null;

    /**
     * Special composition email containing images come after htmlText in
     * managing order
     */
    private MimeMultipart htmlMultipart = null;

    /**
     * Subject email message which is empty string by default
     */
    private String subject = "";

    /**
     * Specify smtp server with default value empty string
     */
    private String smtp = ""; //smtp.gmail.com";

    /**
     * Specified default port which is 25 by default
     */
    private int port = 25;

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Constructors
    //
    //
    // /////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor Mail
     */
    public Mail() {
    }

    /**
     * Basic Mail constructor
     *
     * @param receiverEmailAddress email receiver
     * @param subject of the email
     * @param htmlText is a html string message
     */
    public Mail(String receiverEmailAddress, String subject, String htmlText) {
        tos.add(receiverEmailAddress.replace(";", ",").trim());
        this.subject = subject;
        this.htmlText = htmlText;
    }

    public Mail(String receiverEmailAddress, String subject, MimeMultipart htmlMultipart) {
        tos.add(receiverEmailAddress.replace(";", ",").trim());
        this.subject = subject;
        this.htmlMultipart = htmlMultipart;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    //
    //
    // /////////////////////////////////////////////////////////////////////////
    /**
     * add To allow to insert new email address mailAddressOfTo to the current
     * list of receiver email
     *
     * @param mailAddressOfTo one email address
     */
    public void addTo(String mailAddressOfTo) {
        if (tos == null) {
            tos = new ArrayList<>();
        }
        tos.add(mailAddressOfTo.replace(";", ",").trim());
    }

    /**
     * add To allow to insert new email address mailAddressOfTo to the current
     * list of receiver email
     *
     * @param mailAddressTos multiple email addresses
     */
    public void addTos(List<String> mailAddressTos) {
        if (tos == null) {
            tos = new ArrayList<>();
        }
        tos.addAll(mailAddressTos);
    }

    /**
     * add CC allow to insert new email address mailAddressOfCarbonCopy to the
     * current list of receiver email
     *
     * @param mailAddressOfCarbonCopy one email address
     */
    public void addCC(String mailAddressOfCarbonCopy) {
        if (ccs == null) {
            ccs = new ArrayList<>();
        }
        ccs.add(mailAddressOfCarbonCopy.replace(";", ",").trim());
    }

    /**
     * add CC allow to insert new email address mailAddressOfCarbonCopy to the
     * current list of receiver email
     *
     * @param mailAddressOfCarbonCopys multiple email address
     */
    public void addCCs(List<String> mailAddressOfCarbonCopys) {
        if (ccs == null) {
            ccs = new ArrayList<>();
        }
        ccs.addAll(mailAddressOfCarbonCopys);
    }

    /**
     * add CC allow to insert new email address mailAddressOfCarbonCopyInvisible
     * to the current list of receiver email
     *
     * @param mailAddressOfCarbonCopyInvisible one email address
     */
    public void addCCI(String mailAddressOfCarbonCopyInvisible) {
        if (ccs == null) {
            ccs = new ArrayList<>();
        }
        ccis.add(mailAddressOfCarbonCopyInvisible.replace(";", ",").trim());
    }

    /**
     * add CCIs allow to insert new email addresses
     * mailAddressOfCarbonCopyInvisibles to the current list of receiver email
     *
     * @param mailAddressOfCarbonCopyInvisibles multiple emails addresses
     */
    public void addCCIs(String mailAddressOfCarbonCopyInvisibles) {
        if (ccs == null) {
            ccs = new ArrayList<>();
        }
        ccis.add(mailAddressOfCarbonCopyInvisibles);
    }

    /**
     *
     * @return
     */
    public Multipart getContent() {
        if (htmlText != null) {
            try {
                // This mail has 2 part, the BODY and the embedded image
                MimeMultipart multipart = new MimeMultipart("related");

                // first part (the html)
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(htmlText, "text/html");
                // add it
                multipart.addBodyPart(messageBodyPart);
                return multipart;
            } catch (MessagingException ex) {
                Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (htmlMultipart != null) {
            return htmlMultipart;
        }

        return null;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    //
    //
    // /////////////////////////////////////////////////////////////////////////
    public List<String> getTos() {
        return tos;
    }

    public void setTos(List<String> tos) {
        this.tos = tos;
    }

    public List<String> getCcs() {
        return ccs;
    }

    public void setCcs(List<String> ccs) {
        this.ccs = ccs;
    }

    public List<String> getCcis() {
        return ccis;
    }

    public void setCcis(List<String> ccis) {
        this.ccis = ccis;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }

    public MimeMultipart getHtmlMultipart() {
        return htmlMultipart;
    }

    public void setHtmlMultipart(MimeMultipart htmlMultipart) {
        this.htmlMultipart = htmlMultipart;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    //
    //
    // /////////////////////////////////////////////////////////////////////////
    public static MimeMultipart msgTest() {
        try {
            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = msgStandard();
            htmlText = htmlText.replace("%Title%", "Message de Test")
                    .replace("%Content%", "La messagerie est correctement configurée");
            messageBodyPart.setContent(htmlText, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(Util.writeResourceToFileSystem("img/ism/ism.png"));
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);

            return multipart;
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String msgStandard() {
        return "<head>\n"
                + "<!-- Copyright 2005 Macromedia, Inc. All rights reserved. -->\n"
                + "<title>Text</title>\n"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
                + "<style>\n"
                + "/* Global Styles */\n"
                + "\n"
                + "body {\n"
                + "	margin:0px;\n"
                + "	}\n"
                + "	\n"
                + "td {\n"
                + "	font:11px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color:#003366;\n"
                + "	}\n"
                + "	\n"
                + "a {\n"
                + "	color: #FF6600;\n"
                + "	font-weight:bold;\n"
                + "	}\n"
                + "	\n"
                + "a:hover {\n"
                + "	color: #3366CC;\n"
                + "	}\n"
                + "\n"
                + "/* ID Styles */\n"
                + "\n"
                + "#navigation td {\n"
                + "	border-bottom: 2px solid #C0DFFD;\n"
                + "	}\n"
                + "	\n"
                + "#navigation a {\n"
                + "	font: 11px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color: #003366;\n"
                + "	line-height:16px;\n"
                + "	letter-spacing:.1em;\n"
                + "	text-decoration: none;\n"
                + "	display:block;\n"
                + "	padding:8px 6px 10px 26px;\n"
                + "	background: url(\"mm_arrow.gif\") 14px 45% no-repeat;\n"
                + "	}\n"
                + "	\n"
                + "#navigation a:hover {\n"
                + "	background: #ffffff url(\"mm_arrow.gif\") 14px 45% no-repeat;\n"
                + "	color:#FF6600;\n"
                + "	}\n"
                + "	\n"
                + "#logo 	{\n"
                + "	font:24px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color: #CCFF99;\n"
                + "	letter-spacing:.2em;\n"
                + "	line-height:30px;\n"
                + "	}\n"
                + "\n"
                + "#tagline 	{	\n"
                + "	font:12px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color: #FF9933;\n"
                + "	letter-spacing:.4em;\n"
                + "	line-height:18px;\n"
                + "	}\n"
                + "\n"
                + "#monthformat {\n"
                + "	border-bottom: 2px solid #E6F3FF;\n"
                + "		}\n"
                + "		\n"
                + "#dateformat {\n"
                + "	font:11px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color: #003366;\n"
                + "	letter-spacing:.2em;\n"
                + "	}\n"
                + "	\n"
                + "#dateformat a {\n"
                + "	font:11px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color: #003366;\n"
                + "	font-weight:bold;\n"
                + "	letter-spacing:.1em;\n"
                + "	}\n"
                + "	\n"
                + "#dateformat a:hover {\n"
                + "	color: #FF6600;\n"
                + "	letter-spacing:.1em;\n"
                + "	}\n"
                + "	\n"
                + "/* Class Styles */\n"
                + "	\n"
                + ".bodyText {\n"
                + "	font:11px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color:#003366;\n"
                + "	line-height:20px;\n"
                + "	margin-top:0px;\n"
                + "	}\n"
                + "	\n"
                + ".pageName{\n"
                + "	font: 18px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color: #3366CC;\n"
                + "	line-height:24px;\n"
                + "	letter-spacing:.2em;\n"
                + "	}\n"
                + "	\n"
                + ".subHeader {\n"
                + "	font:bold 10px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color: #3366CC;\n"
                + "	line-height:16px;\n"
                + "	letter-spacing:.2em;\n"
                + "	}\n"
                + "\n"
                + ".quote {\n"
                + "	font: 20px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color: #759DA1;\n"
                + "	line-height:30px;\n"
                + "	}\n"
                + "	\n"
                + ".smallText {\n"
                + "	font: 10px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color: #003366;\n"
                + "	}\n"
                + "	\n"
                + ".navText {\n"
                + "	font: 11px Verdana, Arial, Helvetica, sans-serif;\n"
                + "	color: #003366;\n"
                + "	line-height:16px;\n"
                + "	letter-spacing:.1em;\n"
                + "	text-decoration: none;\n"
                + "	}\n"
                + "	\n"
                + "\n"
                + "</style>\n"
                + "</head>\n"
                + "<body bgcolor=\"#C0DFFD\">\n"
                + "\n"
                + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                + "  <tr bgcolor=\"#3366CC\">\n"
                + "    <td colspan=\"2\" rowspan=\"2\" nowrap=\"nowrap\"><img src=\"cid:image\" alt=\"Header image\" width=\"382\" height=\"127\" border=\"0\" /></td>\n"
                + "    <td width=\"262\" height=\"63\" id=\"logo\" valign=\"bottom\" align=\"center\" nowrap=\"nowrap\">ISM MESSAGER</td>\n"
                + "    <td width=\"854\">&nbsp;</td>\n"
                + "  </tr>\n"
                + "  <tr bgcolor=\"#3366CC\">\n"
                + "    <td height=\"64\" id=\"tagline\" valign=\"top\" align=\"center\">RESTER INFORMER</td>\n"
                + "	<td width=\"854\">&nbsp;</td>\n"
                + "  </tr>\n"
                + "  <tr>\n"
                + "    <td colspan=\"4\" bgcolor=\"#003366\"><img src=\"mm_spacer.gif\" alt=\"\" width=\"1\" height=\"1\" border=\"0\" /></td>\n"
                + "  </tr>\n"
                + "\n"
                + "  <tr bgcolor=\"#CCFF99\">\n"
                + "  	<td>&nbsp;</td>\n"
                + "  	<td colspan=\"3\" id=\"dateformat\" height=\"25\"><a href=\"javascript:;\">Rapport </a> :</td>\n"
                + "  </tr>\n"
                + " <tr>\n"
                + "    <td colspan=\"4\" bgcolor=\"#003366\"><img src=\"mm_spacer.gif\" alt=\"\" width=\"1\" height=\"1\" border=\"0\" /></td>\n"
                + "  </tr>\n"
                + " <tr>\n"
                + "    <td width=\"21\">&nbsp;</td>\n"
                + "    <td colspan=\"2\" valign=\"top\">&nbsp;<br />\n"
                + "    &nbsp;<br />\n"
                + "    <table border=\"0\" cellspacing=\"0\" cellpadding=\"2\" width=\"573\">\n"
                + "        <tr>\n"
                + "          <td width=\"569\" class=\"pageName\">%Title%</td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "          <td class=\"bodyText\"><p>%Content%</p>			</td>\n"
                + "		</tr>\n"
                + "      </table>	  </td>\n"
                + "	<td width=\"854\">&nbsp;</td>\n"
                + "  </tr>\n"
                + "\n"
                + " <tr>\n"
                + "    <td width=\"21\">&nbsp;</td>\n"
                + "    <td width=\"361\">&nbsp;</td>\n"
                + "    <td width=\"262\">&nbsp;</td>\n"
                + "	<td width=\"854\">&nbsp;</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "\n"
                + "</body>";
    }


}
