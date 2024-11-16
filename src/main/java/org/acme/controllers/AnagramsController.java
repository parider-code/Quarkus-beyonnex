package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.requests.ListOfWordsPairs;
import org.acme.requests.WordsPairs;
import org.acme.services.AnagramsService;
import org.apache.commons.collections4.CollectionUtils;
import org.jboss.resteasy.reactive.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;


@Path("/anagrams")
public class AnagramsController {

    private static final Logger log = LoggerFactory.getLogger(AnagramsController.class);

    @Inject
    AnagramsService anagramsService;

    @POST
    @Path("/addEventualAnagrams")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<?> f1(ListOfWordsPairs request) {

        try{
            log.info("addEventualAnagrams called with request = {} ", request);
            if(request==null || CollectionUtils.isEmpty(request.getListOfWordsPairs()))
                return RestResponse.status(RestResponse.Status.BAD_REQUEST, "Empty or Malformed Input!");

            for (WordsPairs eachPair: request.getListOfWordsPairs()){
                anagramsService.checkIfAnagramAndValorizeAnagramsMap(eachPair.getLeftString(), eachPair.getRightString());
            }
            return RestResponse.ok("Processed successfully!");
        } catch (Exception exception){
            log.error("addEventualAnagrams triggerd an exception = {} ",  exception);
            return RestResponse.status(RestResponse.Status.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }

    }

    @GET
    @Path("/findMatchingAnagrams/{givenWord}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<?> f2(@PathParam("givenWord") String givenWord) {
        try{
            log.info("findMatchingAnagrams called with givenWord = {} ",  givenWord);
            if(givenWord==null)
                return RestResponse.status(RestResponse.Status.BAD_REQUEST, "Empty Input!");
            HashSet<String> matchingAnagrams = anagramsService.retrievePastAnagramsWithGivenWord(givenWord);
            log.debug("Anagrams found for the word '{}': {}", givenWord, matchingAnagrams);
            return RestResponse.ok(String.format("Words anagrams with '%s': %s", givenWord, matchingAnagrams));
        } catch (Exception exception){
            log.error("findMatchingAnagrams triggerd an exception = {} ",  exception);
            return RestResponse.status(RestResponse.Status.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @DELETE
    @Path("/cleanAnagramsMap")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<?> f3() {
        try{
            log.info("cleanAnagramsMap called");
            anagramsService.emptyMap();
            return RestResponse.ok("Processed successfully!");
        } catch (Exception exception){
            log.error("cleanAnagramsMap triggerd an exception = {} ",  exception);
            return RestResponse.status(RestResponse.Status.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

}
