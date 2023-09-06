package com.fosterpet.backend.controller;

import com.fosterpet.backend.collection.Kennel;
import com.fosterpet.backend.service.KennelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kennel")
public class KennelController {

    @Autowired
    private KennelService kennelService;

    @PostMapping
    public String save(@RequestBody Kennel kennel){
        return kennelService.save(kennel);
    }

    @GetMapping
    public List<Kennel> getKennelStartWith(@RequestParam("name") String name){
        return kennelService.getKennelStartWith(name);
    }

    @GetMapping("/all")
    public List<Kennel> getAllKennels(){
        return kennelService.getAllKennels();
    }
}
