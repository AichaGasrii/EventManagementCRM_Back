package com.example.gestionauth.services.Implementation;

import com.example.gestionauth.persistence.entity.User;
import com.example.gestionauth.persistence.entity.UserMail;
import com.example.gestionauth.repositories.IUserEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements IUserEmailRepository {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private VerificationTokenService verificationTokenService;

    @Override
    public void sendCodeByMail(UserMail mail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("saebizmatch@gmail.com");
            helper.setTo(mail.getTo());
            helper.setSubject("Password Reset");

            // Corps HTML de l'e-mail, avec référence à l'image attachée via son Content-ID
            String htmlMsg = "<div style='font-family: Arial, sans-serif; background-color: #6596fc; padding: 50px;'>\n" +
                    "    <div style='background-color: #ffffff; padding: 50px; border-radius: 8px; max-width: 600px; margin: auto; box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);'>\n" +
                    "      <div style='text-align:center; margin-bottom:20px;'>\n" +
                    "        <img src='https://i.postimg.cc/T3jQ5KYF/Fichier-4.png' alt='logo' />\n" +
                    "    </div>\n" +
                    "        <h1 style='color: #333333; font-size: 24px; margin-bottom: 20px; text-align:center;'>Welcome to EVENTFLOW!</h1>\n" +
                    "        <p style='color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; text-align:center;'>We received a request to reset your password for your account associated with this email address. If you made this request, please follow the instructions below.\n</p>\n" +
                    "        <p style='color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; text-align:center;'>copy the code below to reset your password\n</p>\n" +
                    "                    \n</p>\n" +

                    "<p style='background-color: #709fdc; display: inline-block; padding: 10px 20px; color: #ffffff; border-radius: 4px; font-size: 18px; font-weight: bold; text-align:center;'>" + mail.getCode() + "</p>\n" +
                    "        <p style='color: #666666; font-size: 16px; line-height: 1.5; margin-top: 20px; text-align:center;'>If you didn't request to reset your password, please ignore this email or contact us if you have any questions.\n</p>\n" +
                    "        <p style='color: #666666; font-size: 16px; line-height: 1.5; margin-top: 20px; text-align:center;'>Your security is important to us. We recommend that you never share your password with anyone.\n\n</p>\n" +
                    "    </div>\n" +
                    "    </div>\n";

            message.setContent(htmlMsg, "text/html");
            System.out.println("Sending code by mail to: " + mail.getTo());
            System.out.println("Mail object: " + mail);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendVerificationEmail(User user) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getUserEmail());
            user.setVerificationToken(verificationTokenService.generateVerificationToken());
            helper.setSubject("Welcome to EventFlow!");

            String htmlMsg = "<div style='font-family: Arial, sans-serif; background-color: #6596fc; padding: 50px;'>" +
                    "<div style='background-color: #ffffff; padding: 50px; border-radius: 8px; max-width: 600px; margin: auto; box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);'>" +
                    "<div style='text-align:center; margin-bottom:20px;'>" +
                    "<img src='https://i.postimg.cc/T3jQ5KYF/Fichier-4.png' alt='logo'  />" +
                    "</div>" +
                    "<h1 style='color: #000000; font-size: 24px; margin-bottom: 20px; text-align:center;'>Hello " + user.getUserFirstName() + ",</h1>" +
                    "<p style='color: #000000; font-size: 16px; line-height: 1.5; margin-bottom: 20px; text-align:center;'>Congratulations on creating your EventFlow account! We're excited to have you join our community.</p>" +
                    "<p style='color: #000000; font-size: 16px; line-height: 1.5; margin-bottom: 20px; text-align:center;'>Before you can start using your account, please activate it by clicking the button below:</p>" +
                    "<div style='text-align:center; margin-top:20px;'>" +
                    "<a href='http://localhost:8083/auth/activate?token=" + user.getVerificationToken() + "' style='background-color: #6596fc; display: inline-block; padding: 10px 20px; color: #ffffff; border-radius: 4px; font-size: 18px; font-weight: bold; text-decoration: none;'>Activate Account</a>" +
                    "</div>" +
                    "<p style='color: #000000; font-size: 16px; line-height: 1.5; margin-top: 20px; text-align:center;'>If you didn't create an account with EventFlow, please ignore this email or contact us if you have any questions.</p>" +
                    "<p style='color: #000000; font-size: 16px; line-height: 1.5; margin-top: 20px; text-align:center;'>Thank you for choosing EventFlow. We look forward to helping you manage your events more efficiently.</p>" +
                    "</div></div>";

            message.setContent(htmlMsg, "text/html");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

}