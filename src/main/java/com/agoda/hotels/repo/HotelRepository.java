package com.agoda.hotels.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.agoda.hotels.Hotel;

@RepositoryRestResource
public interface HotelRepository extends ReadOnlyRepository<Hotel, String> {
  public Iterable<Hotel> findByCity(@Param("city") String city, Sort sort);
}
