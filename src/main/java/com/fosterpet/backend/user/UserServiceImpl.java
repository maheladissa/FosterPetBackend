package com.fosterpet.backend.user;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.imagemetadata.ImageMetadataService;
import com.fosterpet.backend.session.Session;
import com.fosterpet.backend.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageMetadataService imageMetadataService;

    @Autowired
    private SessionService sessionService;

    @Override
    public UserResponse save(UserRequest userRequest){
        try {
            User user = User.builder()
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .email(userRequest.getEmail())
                    .phoneNumber(userRequest.getPhoneNumber())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .address(Address.builder()
                            .address1(userRequest.getUserAddress1())
                            .address2(userRequest.getUserAddress2())
                            .city(userRequest.getUserCity())
                            .zipCode(Integer.parseInt(userRequest.getUserZip()))
                            .build())
                    .role(Role.USER)
                    .createdAt(Instant.now())
                    .profileImage(imageMetadataService.save(userRequest.getProfileImage()))
                    .build();
            var saved = userRepository.save(user);
            return UserResponseBuilder(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserResponse> getUserFirstNameStartWith(String name){
        var users = userRepository.findByFirstNameStartsWith(name);
        return createUserResponsesFromUsers(users);
    }

    @Override
    public List<UserResponse> getAllUsers(){
        var users = userRepository.findAll();
        return createUserResponsesFromUsers(users);
    }

    @Override
    public UserResponse getUserById(String userId){
        var user = userRepository.findByUserId(userId);
        return UserResponseBuilder(user);
    }

    @Override
    public UserResponse update(UserRequest userRequest){
        try {
            var user = userRepository.findByUserId(userRequest.getUserId());

            if (userRequest.getFirstName() != null) {
                user.setFirstName(userRequest.getFirstName());
            }
            if (userRequest.getLastName() != null) {
                user.setLastName(userRequest.getLastName());
            }
            if (userRequest.getEmail() != null) {
                user.setEmail(userRequest.getEmail());
            }
            if (userRequest.getPhoneNumber() != null) {
                user.setPhoneNumber(userRequest.getPhoneNumber());
            }
            if (userRequest.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }
            if (userRequest.getUserAddress1() != null || userRequest.getUserAddress2() != null || userRequest.getUserCity() != null || userRequest.getUserZip() != null) {
                user.setAddress(Address.builder()
                        .address1(userRequest.getUserAddress1())
                        .address2(userRequest.getUserAddress2())
                        .city(userRequest.getUserCity())
                        .zipCode(Integer.parseInt(userRequest.getUserZip()))
                        .build());
            }
            if (userRequest.getProfileImage() != null && !userRequest.getProfileImage().isEmpty()) {
                user.setProfileImage(imageMetadataService.save(userRequest.getProfileImage()));
            }
            var saved = userRepository.save(user);

            return UserResponseBuilder(saved);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getExpoTokensByUserId(String userId) {
        List<Session> sessions = sessionService.findByUserId(userId);

        if (sessions.isEmpty()) {
            return new ArrayList<>();
        }
        else if (sessions.size() == 1) {
            return List.of(sessions.get(0).getExpoDeviceToken());
        }
        else {
            Set<String> expoTokens = new HashSet<>();

            for (Session session : sessions) {
                expoTokens.add(session.getExpoDeviceToken());
            }

            return new ArrayList<>(expoTokens);
        }
    }

    @Override
    public UserResponse deleteUser(String userId) {
        var user = userRepository.findByUserId(userId);
        user.setIsAccountActive(false);
        var saved = userRepository.save(user);
        return UserResponseBuilder(saved);
    }

    @Override
    public Long countUsersByTimePeriod(Instant startDate, Instant endDate) {
        return userRepository.countUsersByTimePeriod(startDate, endDate);
    }

    @Override
    public List<UserResponse> findUsersByTimePeriod(String startDate, String endDate) {
        return createUserResponsesFromUsers(userRepository.findUsersByTimePeriod(Instant.parse(startDate), Instant.parse(endDate)));
    }

    private UserResponse UserResponseBuilder(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .profileImage(user.getProfileImage() != null ? user.getProfileImage().getImageUrl() : null)
                .createdAt(user.getCreatedAt())
                .build();
    }


    private List<UserResponse> createUserResponsesFromUsers(List<User> users) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = UserResponseBuilder(user);
            userResponses.add(userResponse);
        }

        return userResponses;
    }
}
