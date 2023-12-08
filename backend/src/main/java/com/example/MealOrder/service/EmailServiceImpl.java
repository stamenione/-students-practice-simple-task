package com.example.MealOrder.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration configuration;

    public void sendEmail(String to, String title, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        try {
            Template template = configuration
                    .getTemplate("email-template.ftl");

            MimeMessageHelper helper = new MimeMessageHelper(message
                    , MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED
                    , StandardCharsets.UTF_8.name());

            model.put("text", text);

            String html = FreeMarkerTemplateUtils
                    .processTemplateIntoString(template, model);

            helper.setFrom("zubarskaordinacija.v@gmail.com");
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(html, true);

            mailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            e.getMessage();
        }
    }
}
