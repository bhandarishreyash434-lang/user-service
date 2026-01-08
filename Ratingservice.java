package com.user.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.model.Rating;

@FeignClient(name = "RATING-SERVICE-MICRO")
public interface Ratingservice {

    @GetMapping("/rating/users/{userId}")
 public Rating[] getRatingsByUserId(@PathVariable String userId);
}
