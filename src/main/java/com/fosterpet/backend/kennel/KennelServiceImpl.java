package com.fosterpet.backend.kennel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KennelServiceImpl implements KennelService {

    @Autowired
    private KennelRepository kennelRepository;

    @Override
    public String save(Kennel kennel){
        return kennelRepository.save(kennel).getKennelID();
    }

    @Override
    public List<Kennel> getKennelStartWith(String name){
        return kennelRepository.findByKennelNameStartsWith(name);
    }

    @Override
    public List<Kennel> getAllKennels(){
        return kennelRepository.findAll();
    }
}
