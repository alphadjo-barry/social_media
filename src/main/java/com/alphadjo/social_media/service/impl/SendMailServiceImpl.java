package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.dto.validation.MessageRabbitDto;
import com.alphadjo.social_media.service.contract.SendMailService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Year;

@Slf4j
@Service
@AllArgsConstructor
public class SendMailServiceImpl implements SendMailService {

    private final JavaMailSender javaMailSender;
    private final MinioClient minioClient;

    @Override
    public void sendMail(MessageRabbitDto messageRabbitDto) {


        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String to = messageRabbitDto.email();
            String firstName = messageRabbitDto.firstName();
            String lastName = messageRabbitDto.lastName();
            String code = messageRabbitDto.code();

            helper.setFrom("alphadjo-tech@social-media.gn", "Social Media Team");
            helper.setTo(to);
            helper.setSubject("Votre code de validation Social Media");

            InputStream fileStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket("publications")
                            .object("eb658b31-f00f-4618-a2f2-801d799cf75d.jpeg")
                            .build());

            helper.addAttachment("eb658b31-f00f-4618-a2f2-801d799cf75d.jpeg", new ByteArrayResource(fileStream.readAllBytes()));

            String html =
                    "<!doctype html>" +
                            "<html lang='fr'>" +
                            "<head>" +
                            "  <meta charset='utf-8'>" +
                            "  <meta name='viewport' content='width=device-width'>" +
                            "  <title>Code de validation</title>" +
                            "</head>" +
                            "<body style='margin:0;padding:0;font-family:Arial,Helvetica,sans-serif;background:#f4f6f8;'>" +
                            "  <table role='presentation' width='100%' cellpadding='0' cellspacing='0' " +
                            "         style='max-width:600px;margin:40px auto;background:#ffffff;border-radius:12px;" +
                            "         box-shadow:0 6px 18px rgba(0,0,0,0.06);overflow:hidden;'>" +
                            "    <tr>" +
                            "      <td style='background:#2563eb;padding:20px 24px;color:#ffffff;'>" +
                            "        <h2 style='margin:0;font-weight:600;'>Social Media</h2>" +
                            "      </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "      <td style='padding:32px 24px;text-align:left;'>" +
                            "        <h3 style='margin:0 0 16px 0;color:#0f172a;'>Bonjour " + firstName + " " + lastName + " 👋</h3>" +
                            "        <p style='margin:0 0 24px 0;color:#475569;font-size:15px;'>" +
                            "          Merci de vous être inscrit sur <strong>Social Media</strong>.<br>" +
                            "          Pour activer votre compte, veuillez saisir le code ci-dessous dans le formulaire de validation :" +
                            "        </p>" +
                            "        <p style='text-align:center;margin:24px 0;'>" +
                            "          <span style='display:inline-block;background:#f1f5f9;padding:16px 32px;border-radius:8px;" +
                            "          font-size:24px;letter-spacing:4px;font-weight:700;color:#1e293b;'>" + code + "</span>" +
                            "        </p>" +
                            "        <p style='margin:24px 0 0;color:#64748b;font-size:13px;'>" +
                            "          Ce code est valable pendant <strong>15 minutes</strong>." +
                            "        </p>" +
                            "      </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "      <td style='background:#f8fafc;padding:16px 24px;text-align:center;font-size:12px;color:#94a3b8;'>" +
                            "        © " + Year.now().getValue() + " Social Media. Tous droits réservés." +
                            "      </td>" +
                            "    </tr>" +
                            "  </table>" +
                            "</body>" +
                            "</html>";

            helper.setText(html, true);

            javaMailSender.send(message);
            log.info("Code de validation envoyé à {}", to);
        } catch (Exception e) {
            log.error("Erreur lors de l’envoi du mail : {}", e.getMessage(), e);
        }
    }
}
