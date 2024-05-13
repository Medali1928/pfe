package com.example.demo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.Account;
import com.example.demo.entitys.Email;
import com.example.demo.repository.EmailRepository;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Service
public class EmailService1 {
    
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private AccountService emailAccountService;
   

    
    public void fetchAndSaveEmails(Long emailAccountId) {
        // Récupérer les informations de connexion à partir de la base de données
     
        Account emailAccount = emailAccountService.findById(emailAccountId);
        if (emailAccount == null) {
            throw new IllegalArgumentException("Email account not found with ID: " + emailAccountId);
        }
       // Configurer les propriétés pour l'accès IMAP
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", emailAccount.getServeur());
        properties.put("mail.imaps.port", emailAccount.getPort());
        //properties.put("mail.imaps.host", "imap.gmail.com");
       // properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.starttls.enable", "true");
        //String email = "alouloumed21@gmail.com";
        //String password = "vssd tysc xlcg mabg";
        try {
            // Créer une session avec les propriétés configurées
            Session session = Session.getInstance(properties);
            Store store = session.getStore("imaps");
           store.connect(emailAccount.getEmail(), emailAccount.getPassword());
    //store.connect(email, password);

            // Ouvrir le dossier de la boîte de réception en lecture seule
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
   // Date de référence : 08/05/2024
   LocalDate referenceDate = LocalDate.of(2024, 5, 8);
   Date fromDate = Date.from(referenceDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

   // Rechercher les messages non lus après la date de référence
   FlagTerm unreadFlagTerm = new FlagTerm(new javax.mail.Flags(javax.mail.Flags.Flag.SEEN), false);
   ReceivedDateTerm receivedDateTerm = new ReceivedDateTerm(javax.mail.search.ComparisonTerm.GT, fromDate);
   AndTerm searchTerm = new AndTerm(unreadFlagTerm, receivedDateTerm);
   Message[] messages = inbox.search(searchTerm);


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
    private String extractTextFromHtml(String htmlContent) {
        Document doc = Jsoup.parseBodyFragment(htmlContent);
        return doc.text();
    }
    private String extractTextWithCss(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
    
        // Sélectionnez les éléments de texte (en excluant les balises de script et de style)
        Elements textElements = doc.select(":not(script):not(style)");
    
        StringBuilder sb = new StringBuilder();
        for (Element element : textElements) {
            // Obtenez le texte de cet élément et ajoutez-le au StringBuilder
            sb.append(element.text()).append(" ");
        }
    
        return sb.toString().trim(); // Supprimer les espaces blancs inutiles à la fin
    }
    
    
    
    private void saveMessageToDatabase(Message message) {
        try {
            // Extraire les informations de l'e-mail
            String subject = message.getSubject();
            String sender = InternetAddress.toString(message.getFrom());

            // Extraire la date du message
            LocalDate date = null;
            Date sentDate = message.getSentDate();
            if (sentDate != null) {
                date = sentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            // Vérifier si un e-mail avec le même sujet et la même date existe déjà dans la base de données
            Email existingEmail = emailRepository.findBySubjectAndDate(subject, date);
            if (existingEmail != null) {
                // Si un e-mail existe déjà avec le même sujet et la même date, ne rien faire
                return;
            }

           // Extraire le corps du message
        /*String body = "";
        Object content = message.getContent();
        if (content instanceof String) {
            body = (String) content;
        } else if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getContentType().startsWith("text/plain")) {
                    body += bodyPart.getContent().toString();
                } else if (bodyPart.getContentType().startsWith("text/html")) {
                    // Si le contenu est HTML, extrayez uniquement le texte
                    body += extractTextFromHtml(bodyPart.getContent().toString());
                }
            }
        }*/
       
        // Extraire le corps du message
        StringBuilder bodyBuilder = new StringBuilder();
        StringBuilder attachmentsBuilder = new StringBuilder();
        Object content = message.getContent();
        if (content instanceof String) {
            bodyBuilder.append((String) content);
        } else if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                    String fileName = bodyPart.getFileName();
                    attachmentsBuilder.append(fileName).append(", ");
                } else {
                    // Ajouter le contenu textuel au corps du message
                    bodyBuilder.append(bodyPart.getContent().toString());
                }
            }
        }

        // Convertir les builders en chaînes de caractères
        String body = bodyBuilder.toString();
        String attachments = attachmentsBuilder.toString();


            // Extraire les destinataires
            String recipients = getAddressString(message.getAllRecipients());

            // Extraire les pièces jointes
           /*  String attachments = "";
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
            }*/

            // Créer une entité Email et la sauvegarder dans la base de données
            Email email = new Email();
            email.setSubject(subject);
            email.setSender(sender);
            email.setBody(body);
            email.setDate(date);
            email.setRecipients(recipients);
           // email.setAttachments(attachments);
           if (!attachments.isEmpty()) {
            // Supprimer la virgule et l'espace à la fin
            attachments = attachments.substring(0, attachments.length() - 2);
            email.setAttachments(attachments);
        }
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
    public List<Email> searchEmails(String sender, String subject, LocalDate startDate, LocalDate endDate) {
        // Vérifiez si les paramètres sont nuls et ajustez-les si nécessaire
        if (sender == null) sender = "";
        if (subject == null) subject = "";
        if (startDate == null) startDate = LocalDate.MIN;
        if (endDate == null) endDate = LocalDate.MAX;
    
        // Appelez la méthode de recherche dans le repository avec les paramètres ajustés
        return emailRepository.findBySenderContainingIgnoreCaseAndSubjectContainingIgnoreCaseAndDateBetween(
                sender, subject, startDate, endDate);
    }
    public Email searchByEmail(String email) {
        return emailRepository.findBySender(email);
    }
    public void deleteEmail(Long emailId) {
        // Recherche de l'e-mail dans la base de données par son ID
        Optional<Email> optionalEmail = emailRepository.findById(emailId);
        if (optionalEmail.isPresent()) {
            Email email = optionalEmail.get();
            // Supprimer l'e-mail de la base de données
            emailRepository.delete(email);
        } else {
            // Gérer le cas où l'e-mail avec cet ID n'existe pas
            throw new IllegalArgumentException("Email not found with ID: " + emailId);
        }
    }

    
   
  
    
}
