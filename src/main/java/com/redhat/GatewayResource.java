package com.redhat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

@Path("/gateway")
@Produces(MediaType.APPLICATION_JSON)
public class GatewayResource {
    @RestClient
    MessageClient client;

    @GET
    @Timeout(1000)
    @CircuitBreaker
    (
        requestVolumeThreshold = 4,
        failureRatio = 0.5,
        delay = 5000,
        successThreshold = 4
    )
    @Fallback(fallbackMethod = "movieRecommendationsFallbackMessage")
    public String message() {
        return client.get();
    }

    private String movieRecommendationsFallbackMessage() {
        return "Movie Recommendations Fallback Message";
    }
}