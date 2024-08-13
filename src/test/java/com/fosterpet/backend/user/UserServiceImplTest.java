package com.fosterpet.backend.user;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.imagemetadata.ImageMetadataService;
import com.fosterpet.backend.session.Session;
import com.fosterpet.backend.session.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ImageMetadataService imageMetadataService;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = User.builder()
                .userId("123")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .password("encodedPassword")
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
    void testSave() throws Exception {
        UserRequest userRequest = new UserRequest();
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

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(imageMetadataService.save(any())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserResponse userResponse = userService.save(userRequest);

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getEmail()).isEqualTo("john.doe@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserFirstNameStartWith() {
        List<User> users = List.of(testUser);
        when(userRepository.findByFirstNameStartsWith("Jo")).thenReturn(users);

        List<UserResponse> userResponses = userService.getUserFirstNameStartWith("Jo");

        assertThat(userResponses).isNotEmpty();
        assertThat(userResponses.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<UserResponse> userResponses = userService.getAllUsers();

        assertThat(userResponses).isNotEmpty();
        assertThat(userResponses.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testGetUserById() {
        when(userRepository.findByUserId("123")).thenReturn(testUser);

        UserResponse userResponse = userService.getUserById("123");

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testUpdate() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId("123");
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setEmail("john.doe@example.com");
        userRequest.setPhoneNumber("0987654321");
        userRequest.setPassword("newPassword");

        when(userRepository.findByUserId("123")).thenReturn(testUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserResponse userResponse = userService.update(userRequest);

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getPhoneNumber()).isEqualTo("0987654321");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetExpoTokensByUserId() {
        Session session = new Session();
        session.setExpoDeviceToken("expoToken123");
        List<Session> sessions = List.of(session);
        when(sessionService.findByUserId("123")).thenReturn(sessions);

        List<String> expoTokens = userService.getExpoTokensByUserId("123");

        assertThat(expoTokens).contains("expoToken123");
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findByUserId("123")).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserResponse userResponse = userService.deleteUser("123");

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getEmail()).isEqualTo("john.doe@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCountUsersByTimePeriod() {
        Instant startDate = Instant.now().minusSeconds(86400);
        Instant endDate = Instant.now();
        when(userRepository.countUsersByTimePeriod(startDate, endDate)).thenReturn(1L);

        Long count = userService.countUsersByTimePeriod(startDate, endDate);

        assertThat(count).isEqualTo(1L);
    }

    @Test
    void testFindUsersByTimePeriod() {
        Instant startDate = Instant.now().minusSeconds(86400);
        Instant endDate = Instant.now();
        List<User> users = List.of(testUser);
        when(userRepository.findUsersByTimePeriod(startDate, endDate)).thenReturn(users);

        List<UserResponse> userResponses = userService.findUsersByTimePeriod(startDate.toString(), endDate.toString());

        assertThat(userResponses).isNotEmpty();
        assertThat(userResponses.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }
}
