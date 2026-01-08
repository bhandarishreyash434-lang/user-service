package com.user.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.model.Hotel;

@FeignClient(name="HOTEL-MICRO")
public interface Hotelservice {
	@GetMapping("/hotels/{hotelId}")
 public Hotel getHotel(@PathVariable("hotelId") String hotelId);
 
}
