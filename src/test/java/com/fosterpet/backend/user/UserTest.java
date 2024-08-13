package com.fosterpet.backend.user;

import com.fosterpet.backend.imagemetadata.ImageMetadata;
import com.fosterpet.backend.common.Address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserTest {
    private User user;
    private final String userId = "testUserId";
    private final String firstName = "John";
    private final String lastName = "Doe";
    private final String email = "john.doe@example.com";
    private final String password = "password";
    private final String phoneNumber = "1234567890";
    private final ImageMetadata profileImage = mock(ImageMetadata.class);
    private final Address address = mock(Address.class);
    private final Role role = Role.USER;
    private final Boolean isEmailVerified = true;
    private final Boolean isAccountActive = true;
    private final String azureCommunicationId = "azureId";
    private final String stripeCustomerId = "stripeId";
    private final Instant createdAt = Instant.now();

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .profileImage(profileImage)
                .address(address)
                .role(role)
                .isEmailVerified(isEmailVerified)
                .isAccountActive(isAccountActive)
                .azureCommunicationId(azureCommunicationId)
                .stripeCustomerId(stripeCustomerId)
                .createdAt(createdAt)
                .build();
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(role.name())));
    }

    @Test
    void testGetUsername() {
        assertEquals(email, user.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(user.isEnabled());
    }

    @Test
    void testUserDetails() {
        assertEquals(userId, user.getUserId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(profileImage, user.getProfileImage());
        assertEquals(address, user.getAddress());
        assertEquals(role, user.getRole());
        assertEquals(isEmailVerified, user.getIsEmailVerified());
        assertEquals(isAccountActive, user.getIsAccountActive());
        assertEquals(azureCommunicationId, user.getAzureCommunicationId());
        assertEquals(stripeCustomerId, user.getStripeCustomerId());
        assertEquals(createdAt, user.getCreatedAt());
    }

}
