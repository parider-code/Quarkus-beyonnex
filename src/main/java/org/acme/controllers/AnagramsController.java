package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.requests.ListOfWordsPairs;
import org.acme.requests.WordsPairs;
import org.acme.services.AnagramsService;
import org.apache.commons.collections4.CollectionUtils;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.HashSet;
import java.util.Map;


@Path("/anagrams")
public class AnagramsController {

    @Inject
    AnagramsService anagramsService;

    @POST
    @Path("/addEventualAnagrams")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<?> f1(ListOfWordsPairs request) {

        try{

            if(request==null || CollectionUtils.isEmpty(request.getListOfWordsPairs()))
                return RestResponse.status(RestResponse.Status.BAD_REQUEST, "Empty Input!");

            for (WordsPairs eachPair: request.getListOfWordsPairs()){
                anagramsService.checkIfAnagramAndValorizeAnagramsMap(eachPair.getLeftString(), eachPair.getRightString());
            }
            Map<String, HashSet<String>> anagramsMatches = anagramsService.getAnagramsMatches();
            return RestResponse.ok("Processed successfully!");
        } catch (Exception exception){
            return RestResponse.status(RestResponse.Status.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }

    }

    @GET
    @Path("/findMatchingAnagrams/{givenWord}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<?> f2(@PathParam("givenWord") String givenWord) {

        try{

            if(givenWord==null)
                return RestResponse.status(RestResponse.Status.BAD_REQUEST, "Empty Input!");
            return RestResponse.ok(anagramsService.retrievePastAnagramsWithGivenWord(givenWord));
        } catch (Exception exception){
            return RestResponse.status(RestResponse.Status.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }

    }

}
