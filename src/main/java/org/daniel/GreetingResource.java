package org.daniel;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@Path("/hello")
public class GreetingResource {

    @Inject
    @Channel("my-in-memory")
    Emitter<Integer> emitter;

    @GET
    @Path("/emit/{dato}")
    public void emit(@PathParam("dato") Integer dato){
        emitter.send(dato);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> hello() {
        return ReactiveStreams.of("h", "e", "l", "l", "o")
                .map(String::toUpperCase)
                .toList()
                .run().thenApply(list -> list.toString());
    }


}