package com.alonsegal.repository;

import com.alonsegal.model.City;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Cacheable("city")
    Optional<City> findById(Long id);
}
