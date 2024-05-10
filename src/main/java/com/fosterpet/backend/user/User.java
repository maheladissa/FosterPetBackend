package com.fosterpet.backend.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.imagemetadata.ImageMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    @DBRef
    private ImageMetadata profileImage;
    private Address address;
    private Role role;
    private Boolean isEmailVerified;
    private String azureCommunicationId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
