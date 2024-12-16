package com.udemy.star_d_link.user.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String toEmail, String title, String content) throws MessagingException;
}
