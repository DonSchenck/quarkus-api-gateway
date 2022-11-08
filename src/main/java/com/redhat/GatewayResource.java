package com.redhat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

@Path("/gateway")
public class GatewayResource {
    @RestClient
    MessageClient client;

    @GET
    @Timeout(1000)
    @CircuitBreaker(
        requestVolumeThreshold = 4,
        failureRatio = 0.5,
        delay = 500,
        successThreshold = 2
    )
    @Fallback(fallbackMethod = "movieRecommendationsFallbackMessage")
    public String message() {
        return client.get();
    }

    private String movieRecommendationsFallbackMessage() {
        return "Movie Recommendations Fallback Message";
    }
}