package com.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user.imp.services.user_services;
import com.user.model.user_model;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class user_controller {

    private static final Logger logger = LoggerFactory.getLogger(user_controller.class);

    @Autowired
    private user_services us;

    @PostMapping
    public ResponseEntity<user_model> createuser(@RequestBody user_model user) {
        user_model um = us.saveuser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(um);
    }

    @GetMapping("/{userId}")
    @Retry(
            name = "ratingHotelRetry",
            fallbackMethod = "ratingHotelFallback"
        )
        @CircuitBreaker(
            name = "ratingHotelBreaker",
            fallbackMethod = "ratingHotelFallback"
        )
        public ResponseEntity<user_model> getbyid(@PathVariable String userId) {

            logger.info("Fetching user with id: {}", userId);
            user_model user = us.getuser(userId);
            return ResponseEntity.ok(user);
        }

        // ✅ Common fallback for Retry + CircuitBreaker
        public ResponseEntity<user_model> ratingHotelFallback(
                String userId,
                Throwable ex) {

            logger.error(
                "Fallback executed for userId={} due to exception: {}",
                userId,
                ex.getMessage()
            );

            user_model user = user_model.builder()
                    .userId(userId)
                    .name("Dummy User")
                    .email("dummy@gmail.com")
                    .about("Fallback response: Rating/Hotel service is down")
                    .build();

            return ResponseEntity.ok(user);
        }

         

    @GetMapping
    public ResponseEntity<List<user_model>> getAlluser() {
        List<user_model> alluser = us.getalluser();
        return ResponseEntity.ok(alluser);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<user_model> updatebyid(
            @PathVariable String userId,
            @RequestBody user_model user) {

        user_model upuser = us.updatebyid(userId, user);
        return ResponseEntity.ok(upuser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {

        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("UserId cannot be null or blank");
        }

        us.deletebyId(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}
