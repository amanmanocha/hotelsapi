package com.agoda.hotels.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.agoda.hotels.Hotel;

@RepositoryRestResource
interface HotelRepository extends PagingAndSortingRepository<Hotel, String> {
  public Iterable<Hotel> findByCity(@Param("city") String city);
}
