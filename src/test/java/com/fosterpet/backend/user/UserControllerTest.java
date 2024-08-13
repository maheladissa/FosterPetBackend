package com.fosterpet.backend.user;

import com.fosterpet.backend.common.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRequest = new UserRequest();
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setEmail("john.doe@example.com");
        userRequest.setPhoneNumber("1234567890");
        userRequest.setPassword("password");
        userRequest.setUserAddress1("123 Main St");
        userRequest.setUserAddress2("Apt 4B");
        userRequest.setUserCity("Somewhere");
        userRequest.setUserZip("12345");
        userRequest.setProfileImage(null); // Assuming profileImage is null for simplicity

        userResponse = UserResponse.builder()
                .userId("123")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .address(Address.builder()
                        .address1("123 Main St")
                        .address2("Apt 4B")
                        .city("Somewhere")
                        .zipCode(12345)
                        .build())
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void testSave() {
        when(userService.save(any(UserRequest.class))).thenReturn(userResponse);

        ResponseEntity<?> responseEntity = userController.save(userRequest);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(userResponse);
        verify(userService, times(1)).save(any(UserRequest.class));
    }

    @Test
    void testGetUsersStartWith() {
        List<UserResponse> userResponses = List.of(userResponse);
        when(userService.getUserFirstNameStartWith(anyString())).thenReturn(userResponses);

        ResponseEntity<?> responseEntity = userController.getUsersStartWith("Jo");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(userResponses);
        verify(userService, times(1)).getUserFirstNameStartWith(anyString());
    }

    @Test
    void testGetAllUsers() {
        List<UserResponse> userResponses = List.of(userResponse);
        when(userService.getAllUsers()).thenReturn(userResponses);

        ResponseEntity<?> responseEntity = userController.getAllUsers();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(userResponses);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(anyString())).thenReturn(userResponse);

        ResponseEntity<?> responseEntity = userController.getUserById("123");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(userResponse);
        verify(userService, times(1)).getUserById(anyString());
    }

    @Test
    void testUpdateUser() {
        when(userService.update(any(UserRequest.class))).thenReturn(userResponse);

        ResponseEntity<?> responseEntity = userController.updateUser(userRequest);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(userResponse);
        verify(userService, times(1)).update(any(UserRequest.class));
    }

    @Test
    void testDeleteUser() {
        when(userService.deleteUser(anyString())).thenReturn(userResponse);

        ResponseEntity<?> responseEntity = userController.deleteUser("123");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(userResponse);
        verify(userService, times(1)).deleteUser(anyString());
    }

    @Test
    void testGetUsersByTimePeriod() {
        List<UserResponse> userResponses = List.of(userResponse);
        when(userService.findUsersByTimePeriod(anyString(), anyString())).thenReturn(userResponses);

        ResponseEntity<?> responseEntity = userController.getUsersByTimePeriod("2024-01-01T00:00:00Z", "2024-01-31T23:59:59Z");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(userResponses);
        verify(userService, times(1)).findUsersByTimePeriod(anyString(), anyString());
    }
}