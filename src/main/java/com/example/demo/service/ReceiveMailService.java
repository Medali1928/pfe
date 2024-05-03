package com.example.demo.service;




import javax.mail.internet.MimeMessage;

public interface ReceiveMailService {

    void handleReceivedMail(MimeMessage message);

   

}