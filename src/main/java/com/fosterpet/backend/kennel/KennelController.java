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
@CrossOrigin
public class KennelController {

    @Autowired
    private KennelService kennelService;

    @PostMapping
    public ResponseEntity<KennelResponse> save(@ModelAttribute KennelRequest request){
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

    @GetMapping("/near")
    public ResponseEntity<List<KennelResponse>> getKennelsNear(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double maxDistance){
        return ResponseEntity.ok(kennelService.getKennelsNear(longitude, latitude, maxDistance));
    }

    @PostMapping("/update")
    public ResponseEntity<KennelResponse> updateKennel(@RequestParam String kennelId,
                                                       @RequestParam String kennelName,
                                                       @RequestParam String kennelAddress1,
                                                       @RequestParam String kennelAddress2,
                                                       @RequestParam String kennelCity,
                                                       @RequestParam String kennelZip,
                                                       @RequestParam Double kennelLongitude,
                                                       @RequestParam Double kennelLatitude,
                                                       @RequestParam MultipartFile image){
        return null;
    }
}
