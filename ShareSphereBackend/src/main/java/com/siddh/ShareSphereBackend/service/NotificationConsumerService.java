package com.siddh.ShareSphereBackend.service;

import com.siddh.ShareSphereBackend.payload.EmailNotificationPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumerService {

    private final JavaMailSender mailSender;

    @KafkaListener(topics = "email-notifications", groupId = "notification-group")
    public void handleEmailNotification(EmailNotificationPayload payload) {

        System.out.println("--- KAFKA CONSUMER: Received email request for: " + payload.getToEmail() + " ---");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(payload.getToEmail());
            message.setSubject("Thank you for your ShareSphere Purchase!");

            String text = String.format(
                    "Hi %s,\n\nThank you for purchasing the %s plan for ₹%.2f.\n\nYour credits have been added.\nWe appreciate your business!\n\nThe ShareSphere Team",
                    payload.getCustomerName(),
                    payload.getPlanName(),
                    payload.getAmount()
            );
            message.setText(text);

            mailSender.send(message);
            System.out.println("--- KAFKA CONSUMER: Email sent successfully. ---");

        }
        catch (Exception e) {
            System.err.println("--- KAFKA CONSUMER: ERROR sending email: " + e.getMessage() + " ---");
        }
    }
}