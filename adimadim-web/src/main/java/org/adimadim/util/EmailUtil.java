/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.util;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author Adem
 */
public class EmailUtil {

    public static final String SENDER_INFO = "ADIMADIM KOSU <aakosu@adimadim.org>";
    
    public static void sendMail(String sender, String receiver, String subject, String content) throws NoSuchProviderException, MessagingException {
        Transport transport = null;
        try {
            Session mailSession = Session.getInstance(getMailSettings(), getMailAuthenticatior());
            transport = mailSession.getTransport();

            MimeMessage mimeMessage = new MimeMessage(mailSession);
            mimeMessage.setFrom(new InternetAddress(sender));
            mimeMessage.setSubject(subject, "UTF-8");
            mimeMessage.setContent(content, "text/html; charset=UTF-8");
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            mimeMessage.setSentDate(new Date());
            
            transport.connect();
            transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
        } finally {
            if (transport != null) {
                if (transport.isConnected()) {
                    transport.close();
                }
            }
        }
    }

    public static void sendMailWithAttachment(String sender, String receiver, String subject, String content, String attachmentFileName, String attachmentFormat, byte[] attachment) throws NoSuchProviderException, MessagingException {
        Transport transport = null;
        try {
            Session mailSession = Session.getInstance(getMailSettings(), getMailAuthenticatior());
            transport = mailSession.getTransport();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);

            DataSource dataSource = new ByteArrayDataSource(attachment, attachmentFormat);
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName(attachmentFileName);

            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);

            MimeMessage mimeMessage = new MimeMessage(mailSession);
            mimeMessage.setFrom(new InternetAddress(sender));
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            mimeMessage.setContent(mimeMultipart);
            mimeMessage.setSentDate(new Date());
            
            transport.connect();
            transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
        } finally {
            if (transport != null) {
                if (transport.isConnected()) {
                    transport.close();
                }
            }
        }

    }

    private static Properties getMailSettings() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.host", "mail.sadecehosting.com");
        props.put("mail.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        //props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.SocketFactory");
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        return props;
    }

    private static Authenticator getMailAuthenticatior() {
        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("aakosu@adimadim.org", "kosu!78");
            }
        };
        return authenticator;
    }

}
