package com.fosterpet.backend.email;

import com.azure.communication.email.*;
import com.azure.communication.email.models.*;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class EmailSender {

    private String recipient;
    private String subject;
    private String body;

    public EmailSender() {
    }
    public EmailSender(String recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public PollResponse<EmailSendResult> send(String recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;

        // You can get your connection string from your resource in the Azure portal.
        String connectionString = System.getenv("EMAIL_CONNECTION");
        EmailClient emailClient = new EmailClientBuilder().connectionString(connectionString).buildClient();

        EmailAddress toAddress = new EmailAddress(recipient);

        EmailMessage emailMessage = new EmailMessage()
                .setSenderAddress("DoNotReply@fosterpet.tech")
                .setToRecipients(toAddress)
                .setSubject(subject)
                .setBodyHtml(body);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(emailMessage, null);
        PollResponse<EmailSendResult> result = poller.waitForCompletion();

        return result;
    }
}
