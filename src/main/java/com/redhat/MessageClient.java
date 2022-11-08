package com.redhat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/recommendations")
@RegisterRestClient
public interface MessageClient {

    @GET
    String get();
    
}
