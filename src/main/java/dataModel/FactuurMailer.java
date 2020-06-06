package dataModel;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.Message;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class FactuurMailer {
    private static FactuurMailer instance;

    public static FactuurMailer getInstance(){
        if (instance == null) {
            instance = new FactuurMailer();
        }
        return instance;
    }

    private FactuurMailer() {
    }

    public MimeMessage createEmailWithAttachment(Factuur factuur) throws javax.mail.MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(factuur.getDocent().getBedrijfsGegevens().getEmailAdres()));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(factuur.getLEERLING().getEmailAdres()));
        email.setSubject("Factuur " + factuur.getMUZIEK_SCHOOL().getNAME() + " " + factuur.getLesBlok());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(getBodyText(factuur), "text/plain");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        mimeBodyPart = new MimeBodyPart();

        File file = new File("src/main/resources/facturen/factuur nr." + factuur.getFactuurNummer() +
                " " + factuur.getLEERLING().getNAME() + ".pdf");
        DataSource source = new FileDataSource(file);

        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName(file.getName());

        multipart.addBodyPart(mimeBodyPart);
        email.setContent(multipart);

        return email;
    }

    private String getBodyText(Factuur factuur) {
        StringBuilder sb = new StringBuilder();
        sb.append("Beste ").append(factuur.getLEERLING().getNAME()).append(",\n\n");
        sb.append("In de bijlage vind je de factuur voor ").append(factuur.getLesBlok())
                .append(" van de baslessen bij Ceres.\n");
        sb.append("Wil je zo vriendelijk zijn het lesgeld binnen 3 weken over te maken?\n\n");
        sb.append("Vriendelijke groeten,\n");
        sb.append("-- -- -- -- -- -- -- --\n");
        sb.append("Maarten Bakker\n\n");
        sb.append("maarten@maartenmusic.com\n");
        sb.append("www.maartenmusic.com\n");


        return sb.toString();
    }

    public Message sendMessage(Gmail service, String userId, MimeMessage emailContent)
            throws IOException, javax.mail.MessagingException {

        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
        return message;
    }

    public Message createMessageWithEmail(MimeMessage emailContent)
            throws IOException, javax.mail.MessagingException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }


}
