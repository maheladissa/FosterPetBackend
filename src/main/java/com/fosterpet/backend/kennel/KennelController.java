package com.fosterpet.backend.kennel;

import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserRepository;
import com.fosterpet.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kennel")
public class KennelController {

    @Autowired
    private KennelService kennelService;

    @Autowired
    private UserService userService;

//    @PostMapping
//    public String save(@RequestBody Kennel kennel){
//        User owner = userService.getUserById(kennel.getOwner());
//        kennel.setOwner(owner);
//        return kennelService.save(kennel);
//    }

    @GetMapping
    public List<Kennel> getKennelStartWith(@RequestParam("name") String name){
        return kennelService.getKennelStartWith(name);
    }

    @GetMapping("/all")
    public List<Kennel> getAllKennels(){
        return kennelService.getAllKennels();
    }
}
