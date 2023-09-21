package com.fosterpet.backend.kennel;

import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserRepository;
import com.fosterpet.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kennel")
public class KennelController {

    @Autowired
    private KennelService kennelService;

    @PostMapping
    public ResponseEntity<KennelResponse> save(@RequestBody KennelRequest request){
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
