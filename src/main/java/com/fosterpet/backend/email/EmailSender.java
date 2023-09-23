package com.fosterpet.backend.email;

import com.azure.communication.email.*;
import com.azure.communication.email.models.*;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;

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
        String connectionString = "endpoint=https://emailservice-fosterpet.asiapacific.communication.azure.com/;accesskey=kGWX6HrNsQkU/SG6N941cXwTym8IEMJw2YeqZji0n23bM95IHvHPYyzV3cWyy7+WpGzK+g4ct2EPGbXIF1N8hw==";
        EmailClient emailClient = new EmailClientBuilder().connectionString(connectionString).buildClient();

        EmailAddress toAddress = new EmailAddress(recipient);

        EmailMessage emailMessage = new EmailMessage()
                .setSenderAddress("DoNotReply@43613967-9c29-42d2-b751-4069642bbe18.azurecomm.net")
                .setToRecipients(toAddress)
                .setSubject(subject)
                .setBodyHtml(body);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(emailMessage, null);
        PollResponse<EmailSendResult> result = poller.waitForCompletion();

        return result;
    }
}
