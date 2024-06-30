package com.fosterpet.backend.volunteer;

import com.fosterpet.backend.common.PaymentRates;
import com.fosterpet.backend.imagemetadata.ImageMetadata;
import com.fosterpet.backend.imagemetadata.ImageMetadataService;
import com.fosterpet.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolunteerServiceImpl implements VolunteerService{

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private ImageMetadataService imageMetadataService;

    @Override
    public VolunteerResponse save(VolunteerRequest volunteerRequest) {
        try {
            User user = new User();
            user.setUserId(volunteerRequest.getUserId());

            ArrayList<ImageMetadata> images = new ArrayList();
            if(volunteerRequest.getImages() != null) {
                for (MultipartFile image : volunteerRequest.getImages()) {
                    ImageMetadata imageMetadata = imageMetadataService.save(image);
                    images.add(imageMetadata);
                }
            }


            Volunteer volunteer = Volunteer.builder()
                    .nicNumber(volunteerRequest.getNicNumber())
                    .user(user)
                    .isActive(true)
                    .build();

            if (!images.isEmpty()) {
                volunteer.setImages(images);
            }

            var saved = volunteerRepository.save(volunteer);

            return volunteerResponseBuilder(saved);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VolunteerResponse update(VolunteerRequest volunteerRequest) {
        try {
            Volunteer volunteer = volunteerRepository.findByVolunteerId(volunteerRequest.getVolunteerId());
            User user = new User();
            user.setUserId(volunteerRequest.getUserId());
            volunteer.setUser(user);
            volunteer.setNicNumber(volunteerRequest.getNicNumber());
            volunteer.setImages(new ArrayList<>());
            for (MultipartFile image : volunteerRequest.getImages()) {
                ImageMetadata imageMetadata = imageMetadataService.save(image);
                volunteer.getImages().add(imageMetadata);
            }
            var saved = volunteerRepository.save(volunteer);
            return volunteerResponseBuilder(saved);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<VolunteerResponse> findAll() {
        return createVolunteerResponsesFromVolunteers(volunteerRepository.findAll());
    }

    @Override
    public VolunteerResponse findById(String volunteerId) {
        return volunteerResponseBuilder(volunteerRepository.findByVolunteerId(volunteerId));
    }

    @Override
    public VolunteerResponse findByUserId(String userId) {
        return volunteerResponseBuilder(volunteerRepository.findByUserUserId(userId).get(0));
    }

    @Override
    public List<VolunteerResponse> getVolunteersNear(double longitude, double latitude, double maxDistance) {
        return createVolunteerResponsesFromVolunteers(volunteerRepository.findByLocationNear(longitude, latitude, maxDistance));
    }

    @Override
    public VolunteerResponse updatePaymentRate(VolunteerPaymentRateRequest request) {
        List<PaymentRates> paymentRates = new ArrayList<>();
        var volunteer = volunteerRepository.findByVolunteerId(request.getVolunteerId());
        for (PaymentRates paymentRate : request.getPaymentRates()) {
            paymentRates.add(paymentRate);
        }

        volunteer.setPaymentRates(paymentRates);

        var saved = volunteerRepository.save(volunteer);

        return volunteerResponseBuilder(saved);
    }

    @Override
    public VolunteerResponse deleteVolunteer(String volunteerId) {
        var volunteer = volunteerRepository.findByVolunteerId(volunteerId);
        volunteer.setIsActive(false);
        var saved = volunteerRepository.save(volunteer);
        return volunteerResponseBuilder(saved);
    }


    private VolunteerResponse volunteerResponseBuilder(Volunteer volunteer) {
        return VolunteerResponse.builder()
                .volunteerId(volunteer.getVolunteerId())
                .nicNumber(volunteer.getNicNumber())
                .userId(volunteer.getUser().getUserId())
                .volunteerName(volunteer.getUser().getFirstName() + " " + volunteer.getUser().getLastName())
                .volunteerAddress(volunteer.getUser().getAddress())
                .paymentRates(Optional.ofNullable(volunteer.getPaymentRates())
                        .orElse(null))
                .images(Optional.ofNullable(volunteer.getImages())
                        .map(images -> images.stream()
                                .map(ImageMetadata::getImageUrl)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .build();
    }

    private List<VolunteerResponse> createVolunteerResponsesFromVolunteers(List<Volunteer> volunteers) {
        List<VolunteerResponse> volunteerResponses = new ArrayList<>();
        for (Volunteer volunteer : volunteers) {
            volunteerResponses.add(volunteerResponseBuilder(volunteer));
        }
        return volunteerResponses;
    }
}
