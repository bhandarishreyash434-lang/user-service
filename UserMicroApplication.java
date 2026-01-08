

package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class UserMicroApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMicroApplication.class, args);
    }
}
