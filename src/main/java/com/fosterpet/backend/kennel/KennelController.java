package com.fosterpet.backend.kennel;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserRepository;
import com.fosterpet.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/kennel")
public class KennelController {

    @Autowired
    private KennelService kennelService;

//    @PostMapping
//    public ResponseEntity<KennelResponse> save(@RequestBody KennelRequest request){
//
//        return ResponseEntity.ok(kennelService.save(request));
//    }

    @PostMapping
    public ResponseEntity<KennelResponse> save(@RequestParam String ownerId,
                                               @RequestParam String kennelName,
                                               @RequestParam String kennelAddress1,
                                               @RequestParam String kennelAddress2,
                                               @RequestParam String kennelCity,
                                               @RequestParam String kennelZip,
                                               @RequestParam String kennelLongitude,
                                               @RequestParam String kennelLatitude,
                                               @RequestParam MultipartFile image){
        Address kennelAddress = Address.builder()
                .address1(kennelAddress1)
                .address2(kennelAddress2)
                .city(kennelCity)
                .zipCode(Integer.parseInt(kennelZip))
                .build();

        Location kennelLocation = Location.builder()
                .longitude(kennelLongitude)
                .latitude(kennelLatitude)
                .build();

        KennelRequest request = KennelRequest.builder()
                .ownerId(ownerId)
                .kennelName(kennelName)
                .kennelAddress(kennelAddress)
                .kennelLocation(kennelLocation)
                .image(image)
                .build();
        return ResponseEntity.ok(kennelService.save(request));
    }

    @GetMapping("/name")
    public ResponseEntity<List<KennelResponse>> getKennelStartWith(@RequestParam("name") String name){
        return ResponseEntity.ok(kennelService.getKennelStartWith(name));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<KennelResponse>> getKennelsByOwnerId(@RequestParam String ownerId) {
        return ResponseEntity.ok(kennelService.getKennelsByOwner(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<KennelResponse>> getAllKennels(){
        return ResponseEntity.ok(kennelService.getAllKennels());
    }
}
