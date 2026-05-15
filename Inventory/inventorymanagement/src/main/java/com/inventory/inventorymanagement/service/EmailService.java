package com.inventory.inventorymanagement.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmailWithAttachment(

            String toEmail,
            File file

    ) throws Exception {

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(
                        message,
                        true
                );

        helper.setTo(toEmail);

        helper.setSubject(
                "Inventory Report"
        );

        helper.setText(
                "Please find attached inventory report."
        );



        FileSystemResource resource =
                new FileSystemResource(file);

        helper.addAttachment(
                file.getName(),
                resource
        );



        mailSender.send(message);
    }



}
