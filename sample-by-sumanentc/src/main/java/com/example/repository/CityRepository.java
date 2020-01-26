package com.example.repository;

import com.example.entity.City;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {

    @Cacheable("city")
    City findById(Long id);

    @Cacheable("city")
    City findByName(String name);

    void deleteByName(String name);
}
