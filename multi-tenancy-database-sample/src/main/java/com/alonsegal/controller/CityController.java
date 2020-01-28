package com.alonsegal.controller;

import com.alonsegal.model.City;
import com.alonsegal.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<City> getCityById(@PathVariable(value = "id") Long id) {

        Optional<City> city = cityRepository.findById(id);
        return new ResponseEntity<>(city.get(), HttpStatus.OK);
    }
}
