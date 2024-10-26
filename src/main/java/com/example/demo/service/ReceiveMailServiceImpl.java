package com.example.demo.service;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import com.example.demo.entitys.Email;
import com.example.demo.repository.EmailRepository;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
/* 
@Service
public class ReceiveMailServiceImpl implements ReceiveMailService {

    private final EmailRepository emailRepository;
    private static final Logger log = LoggerFactory.getLogger(ReceiveMailServiceImpl.class);
    private static final String DOWNLOAD_FOLDER = "data";
    private static final String DOWNLOADED_MAIL_FOLDER = "DOWNLOADED";

    public ReceiveMailServiceImpl(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public void handleReceivedMail(MimeMessage receivedMessage) {
        log.info("toto: handle");
        try {
            Folder folder = receivedMessage.getFolder();
            folder.open(Folder.READ_WRITE);

            Message[] messages = folder.getMessages();
            fetchMessagesInFolder(folder, messages);

            Arrays.asList(messages).stream().filter(message -> {
                log.info("toto: " + message);
                MimeMessage currentMessage = (MimeMessage) message;
                try {
                    return currentMessage.getMessageID().equalsIgnoreCase(receivedMessage.getMessageID());
                } catch (MessagingException e) {
                    log.error("Error occurred during process message", e);
                    return false;
                }
            }).forEach(this::extractMail);

            log.info("toto: download");
            copyMailToDownloadedFolder(receivedMessage, folder);

            log.info("toto: closed");
            folder.close(true);

        } catch (Exception e) {
            log.error("Error: " + e.getMessage(), e);
        }
    }

    private void fetchMessagesInFolder(Folder folder, Message[] messages) throws MessagingException {
        FetchProfile contentsProfile = new FetchProfile();
        contentsProfile.add(FetchProfile.Item.ENVELOPE);
        contentsProfile.add(FetchProfile.Item.CONTENT_INFO);
        contentsProfile.add(FetchProfile.Item.FLAGS);
        contentsProfile.add(FetchProfile.Item.SIZE);
        folder.fetch(messages, contentsProfile);
    }

    private void copyMailToDownloadedFolder(MimeMessage mimeMessage, Folder folder) throws MessagingException {
        Store store = folder.getStore();
        Folder downloadedMailFolder = store.getFolder(DOWNLOADED_MAIL_FOLDER);
        if (downloadedMailFolder.exists()) {
            downloadedMailFolder.open(Folder.READ_WRITE);
            downloadedMailFolder.appendMessages(new MimeMessage[]{mimeMessage});
            downloadedMailFolder.close();
        }
    }

    private void extractMail(Message message) {
        try {
            final MimeMessage messageToExtract = (MimeMessage) message;
            final MimeMessageParser mimeMessageParser = new MimeMessageParser(messageToExtract).parse();

            Email email = new Email();
            email.setSender(mimeMessageParser.getFrom());
            email.setRecipients(new HashSet<>(mimeMessageParser.getTo()));
            email.setSubject(mimeMessageParser.getSubject());
            email.setBody(mimeMessageParser.getPlainContent());
            email.setDate(LocalDate.now());
            emailRepository.save(email);
            showMailContent(mimeMessageParser);
            downloadAttachmentFiles(mimeMessageParser);

        } catch (Exception e) {
            log.error("Error: " + e.getMessage(), e);
        }
    }

    private void showMailContent(MimeMessageParser mimeMessageParser) throws Exception {
        log.info("From: {} to: {} | Subject: {}", mimeMessageParser.getFrom(), mimeMessageParser.getTo(), mimeMessageParser.getSubject());
        log.info("Mail content: {}", mimeMessageParser.getPlainContent());
        log.info("Received Date: {}", mimeMessageParser.getMimeMessage().getReceivedDate());
    }

    private void downloadAttachmentFiles(MimeMessageParser mimeMessageParser) {
        log.info("Email has {} attachment files", mimeMessageParser.getAttachmentList().size());
        mimeMessageParser.getAttachmentList().forEach(dataSource -> {
            if (StringUtils.isNotBlank(dataSource.getName())) {
                String rootDirectoryPath = new FileSystemResource("").getFile().getAbsolutePath();
                String dataFolderPath = rootDirectoryPath + File.separator + DOWNLOAD_FOLDER;
                createDirectoryIfNotExists(dataFolderPath);

                String downloadedAttachmentFilePath = rootDirectoryPath + File.separator + DOWNLOAD_FOLDER + File.separator + dataSource.getName();
                File downloadedAttachmentFile = new File(downloadedAttachmentFilePath);

                log.info("Save attachment file to: {}", downloadedAttachmentFilePath);

                try (OutputStream out = new FileOutputStream(downloadedAttachmentFile)) {
                    InputStream in = dataSource.getInputStream();
                    IOUtils.copy(in, out);
                } catch (IOException e) {
                    log.error("Failed to save file.", e);
                }
            }
        });
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        if (!Files.exists(Paths.get(directoryPath))) {
            try {
                Files.createDirectories(Paths.get(directoryPath));
            } catch (IOException e) {
                log.error("An error occurred during create folder: {}", directoryPath, e);
            }
        }
    }

    @Override
    public void save(Email email) {
        emailRepository.save(email);
        
    }
}
*/