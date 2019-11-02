package com.qv.learn.scrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrappedDataService {

    @Autowired
    private ScrappedDataRepository scrappedDataRepository;

    public List<ScrappedData> findAll() {

        return scrappedDataRepository.findAll();
    }
}
