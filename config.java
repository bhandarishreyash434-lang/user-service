package com.user.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class config {
  @Bean
  @LoadBalanced // ek peksh jaste instanse asle teva te distribute karto 
  //(jeva konti services konty host var konty port var chalte asle tar tyhost ani post che name dhy che 
  //ty mule phude jaun problem hote nahi)
		    public RestTemplate restTemplate() {
		        return new RestTemplate();
		    }
		
}
