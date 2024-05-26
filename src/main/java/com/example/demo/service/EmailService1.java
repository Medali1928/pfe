package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.Account;
import com.example.demo.entitys.ArchivedEmail;
import com.example.demo.entitys.Attachment;
import com.example.demo.entitys.DomainEntity;
import com.example.demo.entitys.Email;
import com.example.demo.repository.ArchivedEmailRepository;
import com.example.demo.repository.DomainEntityRepository;
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
    @Autowired
    private ArchivedEmailRepository archivedEmailRepository;
    @Autowired
    private DomainEntityRepository domainEntityRepository;

    public List<Email> getEmailsByDomain(String domainName,Long accountId) {
        Account account = emailAccountService.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Email account not found with ID: " + accountId);
        }
        return emailRepository.findBySenderContainingIgnoreCaseAndAccountId(domainName,accountId);
    }
    
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
                saveMessageToDatabase(message, emailAccount);
                saveDomaineToDatabase(message, emailAccount);
            }

            // Fermer le dossier de la boîte de réception et le magasin
            inbox.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void saveDomaineToDatabase(Message message, Account emailAccount) {
        try {
            DomainEntity domainEntity = new DomainEntity();
            String sender = ((InternetAddress) message.getFrom()[0]).getAddress();
            String domainName = extractDomain(sender);
            domainEntity.setDomainName(domainName);
           domainEntityRepository.save(domainEntity);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private String extractDomain(String email) {
        // Extract the domain from the email address
        String[] parts = email.split("@");
        return parts.length == 2 ? parts[1] : null;
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
    
    private void saveMessageToDatabase(Message message, Account account) {
        try {
            // Extraire les informations de l'e-mail
            String subject = message.getSubject();
            String sender = ((InternetAddress) message.getFrom()[0]).getAddress();

            // Extraire la date du message
            LocalDate date = null;
            Date sentDate = message.getSentDate();
            if (sentDate != null) {
                date = sentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            // Vérifier si un e-mail avec le même sujet et la même date existe déjà dans la base de données
            List<Email> existingEmail = emailRepository.findBySubjectAndDate(subject, date);
            if (existingEmail != null && !existingEmail.isEmpty()) {
                // Si un e-mail existe déjà avec le même sujet et la même date, ne rien faire
                return;
            }
       
        // Extraire le corps du message
        StringBuilder bodyBuilder = new StringBuilder();
        List<Attachment> attachments = new ArrayList<>();
        Object content = message.getContent();
        if (content instanceof String) {
            bodyBuilder.append((String) content);
        } else if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                   try {
                    String folderPath = subject + date.toString();
                    String path = saveAttachment(bodyPart, folderPath);
                    attachments.add(new Attachment(path));
                   }catch(IOException e) {

                   }catch(MessagingException ef){

                   }
                }
            }
        }

        // Convertir les builders en chaînes de caractères
        String body = bodyBuilder.toString();

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
            email.setAccount(account);
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

    private String saveAttachment(BodyPart bodyPart, String folderPath) throws IOException, MessagingException {
        String destDir = "attachments/"+folderPath+"/";
        File dir = new File(destDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = bodyPart.getFileName();
        File file = new File(destDir + File.separator + fileName);
        try (FileOutputStream output = new FileOutputStream(file)) {
            ((MimeBodyPart) bodyPart).saveFile(file);
        }
        System.out.println("Saved attachment: " + file.getAbsolutePath());
        return file.getAbsolutePath();
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
                stringBuilder.append(internetAddress.getAddress()).append(", ");
            }
        }
        String result = stringBuilder.toString();
        // Supprimer la virgule et l'espace à la fin
        return result.substring(0, result.length() - 2);
    }
    
    public List<Email> searchEmails(Long accountId, String sender, String subject, LocalDate startDate, LocalDate endDate) {
        Account account = emailAccountService.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Email account not found with ID: " + accountId);
        }
        // Vérifiez si les paramètres sont nuls et ajustez-les si nécessaire
        if (sender == null) sender = "";
        if (subject == null) subject = "";
        if (startDate == null) startDate = LocalDate.MIN;
        if (endDate == null) endDate = LocalDate.MAX;
    
        // Appelez la méthode de recherche dans le repository avec les paramètres ajustés
        return emailRepository.findBySenderContainingIgnoreCaseAndSubjectContainingIgnoreCaseAndDateBetweenAndAccount(
                sender, subject, startDate, endDate,account);
    }
    public List<Email> searchByEmail(String email, Long accountId) {
        Account account = emailAccountService.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Email account not found with ID: " + accountId);
        }
        return emailRepository.findBySenderAndAccountId(email,accountId);
    }
    public void deleteEmail(Long emailId, Long accountId) {
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
    public List<Email> getEmailsByAccountId(Long accountId) {
        return emailRepository.findByAccountId(accountId);
    }
    
    public void archiveEmail(Long emailId, Long accountId) {
        // Rechercher l'email dans la base de données
        Email email = emailRepository.findByIdAndAccountId(emailId, accountId);
        if (email != null) {
            // Créer une instance d'ArchivedEmail à partir de l'email à archiver
            ArchivedEmail archivedEmail = new ArchivedEmail(
                email.getSender(),
                email.getRecipients(),
                email.getSubject(),
                email.getBody(),
                email.getDate(),
                email.getAttachments(),
                email.getAccount()
            );
            // Enregistrer l'ArchivedEmail dans la table correspondante
            archivedEmailRepository.save(archivedEmail);
            // Supprimer l'email de la table Email
            emailRepository.delete(email);
        } else {
            throw new IllegalArgumentException("Email not found for id: " + emailId);
        }
    }

    
   
  
    
}
