package com.user.imp.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.exceptions.ResourceNotFoundException;
import com.user.external.service.Hotelservice;
import com.user.external.service.Ratingservice;
import com.user.model.Hotel;
import com.user.model.Rating;
import com.user.model.user_model;
import com.user.repository.User_Repository;
import com.user.service.user_iservice;
@Service
public class user_services implements user_iservice{

    @Autowired
    private User_Repository user_repository;
    
    @Autowired
    private RestTemplate resttemplate;
    
    @Autowired
    private Hotelservice hotelservice;
    
    @Autowired
    private Ratingservice ratingservice;
    
    private  Logger logger=LoggerFactory.getLogger(user_services.class);

    @Override
    public user_model saveuser(user_model user) {
        String randomuserId = UUID.randomUUID().toString();
        user.setUserId(randomuserId);
        return user_repository.save(user);
    }

    @Override
    public List<user_model> getalluser() {
        return user_repository.findAll();
    }

  
      
        @Override
        public user_model getuser(String userId) {
            if (userId == null || userId.trim().isEmpty()) {
                throw new ResourceNotFoundException("User id must not be null or empty");
            }

            user_model user = user_repository.findById(userId.trim())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "User with given id not found: " + userId));

            // Default empty ratings
            List<Rating> enrichedRatings = new ArrayList<>();

            try {
                // 1️⃣ Call Rating service
               // String ratingUrl = "http://RATING-SERVICE-MICRO/Rating/users/" + userId;
               // Rating[] ratingsArray = resttemplate.getForObject(ratingUrl, Rating[].class);
                Rating[] ratingsArray = ratingservice.getRatingsByUserId(userId);

                
                if (ratingsArray != null) {
                    List<Rating> ratings = Arrays.asList(ratingsArray);

                    // 2️⃣ For each rating → call Hotel service
                    enrichedRatings = ratings.stream().map(rating -> {
                        try {
                
                        //	String hotelUrl = "http://HOTEL-MICRO/hotels/" + rating.getHotelId();
                         //   Hotel hotel = resttemplate.getForObject(hotelUrl, Hotel.class);
                        	Hotel hotel=hotelservice.getHotel(rating.getHotelId());
                            rating.setHotel(hotel);
                        } catch (Exception e) {
                            // Hotel service down → ignore hotel info
                            rating.setHotel(null);
                        }
                        return rating;
                    }).toList();
                }
            } catch (Exception e) {
                // Rating service down → ignore ratings
                logger.warn("Rating service is down. Returning user without ratings");
            }

            user.setRatings(enrichedRatings);

            logger.info("User {} ratings with hotels fetched", userId);
            return user;
        }


    @Override
    public user_model updatebyid(String userId, user_model userupdate) {
        user_model user = user_repository.findById(userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found: " + userId));

        user.setName(userupdate.getName());
        user.setEmail(userupdate.getEmail());
        user.setAbout(userupdate.getAbout());
        return user_repository.save(user);
    }

    @Override
    public String deletebyId(String userId) {

        user_model user = user_repository.findById(userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found: " + userId));

        user_repository.delete(user);
        return "User deleted successfully";
    }
}
