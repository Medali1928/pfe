package com.example.demo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.Email;
import com.example.demo.repository.EmailRepository;
import javax.mail.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.mail.internet.MimeMessage;
@Service
public class EmailService1 {
    
    @Autowired
    private EmailRepository emailRepository;

    public void fetchAndSaveEmails() {
        // Configurer les propriétés pour l'accès IMAP
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.starttls.enable", "true");

        // Adresse e-mail et mot de passe
        String email = "alouloumed21@gmail.com";
        String password = "vssd tysc xlcg mabg";

        try {
            // Créer une session avec les propriétés configurées
            Session session = Session.getInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(email, password);

            // Ouvrir le dossier de la boîte de réception en lecture seule
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Récupérer les messages de la boîte de réception
            Message[] messages = inbox.getMessages();

            // Parcourir les messages et les enregistrer dans la base de données
            for (Message message : messages) {
                saveMessageToDatabase(message);
            }

            // Fermer le dossier de la boîte de réception et le magasin
            inbox.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 private void saveMessageToDatabase(Message message) {
        try {
            // Extraire les informations de l'e-mail
            String subject = message.getSubject();
            String sender = InternetAddress.toString(message.getFrom());

            // Extraire le corps du message
            String body = "";
            Object content = message.getContent();
            if (content instanceof String) {
                body = (String) content;
            } else if (content instanceof Multipart) {
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    if (bodyPart.getContentType().startsWith("text/plain")) {
                        body += bodyPart.getContent().toString();
                    }
                }
            }

            // Extraire les destinataires
            String recipients = "";
            if (message.getAllRecipients() != null) {
                for (Address recipient : message.getAllRecipients()) {
                    recipients += recipient.toString() + ", ";
                }
                // Supprimer la virgule et l'espace à la fin
                recipients = recipients.substring(0, recipients.length() - 2);
            }
            

            // Extraire la date du message
           LocalDate date = null;
           Date sentDate = message.getSentDate();
              if (sentDate != null) {
                  date = sentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
}

            // Extraire les pièces jointes
            String attachments = "";
            if (message instanceof MimeMessage) {
                MimeMessage mimeMessage = (MimeMessage) message;
                if (mimeMessage.getContent() instanceof Multipart) {
                    Multipart multipart = (Multipart) mimeMessage.getContent();
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);
                        if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                            attachments += bodyPart.getFileName() + ", ";
                        }
                    }
                    // Supprimer la virgule et l'espace à la fin
                    attachments = attachments.substring(0, attachments.length() - 2);
                }
            }

            // Créer une entité Email et la sauvegarder dans la base de données
            Email email = new Email();
            email.setSubject(subject);
            email.setSender(sender);
            email.setBody(body);
            email.setDate(date);
            email.setRecipients(recipients);
            email.setAttachments(attachments);
            // Enregistrez maintenant l'e-mail dans votre base de données
            emailRepository.save(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Méthode utilitaire pour convertir Address[] en String
    private String getAddressString(Address[] addresses) {
        if (addresses == null || addresses.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Address address : addresses) {
            if (address instanceof InternetAddress) {
                InternetAddress internetAddress = (InternetAddress) address;
                stringBuilder.append(internetAddress.toString()).append(", ");
            }
        }
        String result = stringBuilder.toString();
        // Supprimer la virgule et l'espace à la fin
        return result.substring(0, result.length() - 2);
    }
    
   

}
