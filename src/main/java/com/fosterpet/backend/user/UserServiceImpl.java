package com.fosterpet.backend.user;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.ImageToBase64Converter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse save(UserRequest userRequest){
        try {
            MultipartFile profileImage = userRequest.getProfileImage();
            InputStream inputStream = profileImage.getInputStream();

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
                    .profileImage("image/"+ FilenameUtils.getExtension(profileImage.getOriginalFilename()) +";base64,"+ ImageToBase64Converter.convert(inputStream))
                    .build();
            userRepository.save(user);
            return UserResponse.builder()
                    .userId(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .address(user.getAddress())
                    .profileImage(user.getProfileImage())
                    .build();
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
        return UserResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .profileImage(user.getProfileImage())
                .build();
    }

    @Override
    public UserResponse update(UserRequest userRequest){
        try {
            var user = userRepository.findByUserId(userRequest.getUserId());
            MultipartFile profileImage = userRequest.getProfileImage();
            InputStream inputStream = profileImage.getInputStream();

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
                user.setProfileImage("image/"+ FilenameUtils.getExtension(profileImage.getOriginalFilename()) +";base64,"+ ImageToBase64Converter.convert(inputStream));
            }
            var saved = userRepository.save(user);

            return UserResponse.builder()
                    .userId(saved.getUserId())
                    .firstName(saved.getFirstName())
                    .lastName(saved.getLastName())
                    .email(saved.getEmail())
                    .phoneNumber(saved.getPhoneNumber())
                    .address(saved.getAddress())
                    .profileImage(saved.getProfileImage())
                    .build();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private List<UserResponse> createUserResponsesFromUsers(List<User> users) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = UserResponse.builder()
                    .userId(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .address(user.getAddress())
                    .profileImage(user.getProfileImage())
                    .build();

            userResponses.add(userResponse);
        }

        return userResponses;
    }
}
