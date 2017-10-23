package io.pivotalservices.coverclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * This method offers an endpoint called '/mydestinations' that will accept an empty GET
 * request. It then uses the `covers-service` to get the latest types of cover available
 * before returining this list to the user.
 *
 * If the covers service is unavailable, a [Circuit Breaker] kicks in which returns a single
 * choice of `No Cover`.
 */
@SpringBootApplication  // Identified this application as a Spring Boot application
@EnableCircuitBreaker // Turns on the Hystrix [Circuit Breaker] features for this application
@EnableDiscoveryClient // Allows this microservice to register itself with the [Registry]
public class CoverClientApplication {

    private static final Logger LOG = LoggerFactory.getLogger(CoverClientApplication.class);

    @Bean
    @LoadBalanced // Tell the RestTemplate to use a load balancer like Ribbon
    public RestTemplate rest(RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * This bean definition tells Sleuth to establish the 'Always[On]Sampler` as the
     * defaultSampler. This results in _all_ requests, responses and callouts being logged
     * to [Zipkin].
     * @return
     */
    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }

    /**
     * Used to start the application when packaged as a Java JAR. Uses String Boot's default web configuration
     * which includes the embedded Tomcat webserver.
     * @param args
     */
	public static void main(String[] args) {
		SpringApplication.run(CoverClientApplication.class, args);
	}
}
