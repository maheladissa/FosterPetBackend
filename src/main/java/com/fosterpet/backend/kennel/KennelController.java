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
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/kennel")
@CrossOrigin
public class KennelController {

    @Autowired
    private KennelService kennelService;

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute KennelRequest request){
        try {
            return ResponseEntity.ok(kennelService.save(request));
        }
        catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
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
        try {
            return ResponseEntity.ok(kennelService.getAllKennels());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/near")
    public ResponseEntity<List<KennelResponse>> getKennelsNear(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double maxDistance){
        return ResponseEntity.ok(kennelService.getKennelsNear(longitude, latitude, maxDistance));
    }

    @PostMapping("/update")
    public ResponseEntity<KennelResponse> updateKennel(@ModelAttribute KennelRequest request){
        return ResponseEntity.ok(kennelService.update(request));
    }

    @GetMapping("/id")
    public ResponseEntity<KennelResponse> getKennelById(@RequestParam String kennelId){
        return ResponseEntity.ok(kennelService.getKennelById(kennelId));
    }
}
