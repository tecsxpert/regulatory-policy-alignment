package com.internship.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/send")
    public String sendEmail(@RequestParam String toEmail) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@example.com");
        message.setTo(toEmail);
        message.setSubject("Test Email");
        message.setText("This is a test email from Regulatory Policy Alignment project.");

        mailSender.send(message);

        return "Email sent successfully";
    }
}