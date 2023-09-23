package com.fosterpet.backend.user;

import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.PollResponse;
import com.fosterpet.backend.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class EmailVerificationService {

    private EmailSender emailSender = new EmailSender();
    @Autowired
    private CacheManager cacheManager;

    // Generate a 6-digit random verification code
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generate a 6-digit code
        return String.valueOf(code);
    }

    public String sendVerificationCode(String userEmail) {
        Cache cache = cacheManager.getCache("VerificationCode");
        String verificationCode = generateVerificationCode();
        String subject = "Email Verification Code";
        String body = "Your verification code is: " + verificationCode;

        // Use custom EmailSender to send the email
        PollResponse<EmailSendResult> result = emailSender.send(userEmail, subject, body);

        // Store the verification code in the cache with a TTL of 15 minutes (900 seconds)
        cache.put(userEmail, verificationCode);

        return result.getStatus().toString();
    }


    public boolean verifyCode(String userEmail, String verificationCode) {
        Cache cache = cacheManager.getCache("VerificationCode");
        String cachedCode = cache.get(userEmail, String.class);
        if (cachedCode != null && cachedCode.equals(verificationCode)) {
            cache.evict(userEmail);
            return true;
        }
        return false;
    }
}

