package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnagramsService {

    static Map<String, HashSet<String>> anagramsMatches = new HashMap<>();

    public void emptyMap (){
        anagramsMatches.clear();
    }

    public void checkIfAnagramAndValorizeAnagramsMap(String leftString, String rightString){
        if(areAnagramsCheck(leftString, rightString)){
            anagramsMatches.computeIfAbsent(leftString, k -> new HashSet<>()).add(rightString);
            anagramsMatches.computeIfAbsent(rightString, k -> new HashSet<>()).add(leftString);

            for (String eachAnagram : anagramsMatches.get(leftString)) {
                anagramsMatches.get(leftString).stream()
                        .filter(element -> !element.equals(eachAnagram))
                        .forEach(anagramsMatches.get(eachAnagram)::add);
            }
            for (String eachAnagram : anagramsMatches.get(rightString)) {
                anagramsMatches.get(rightString).stream()
                        .filter(element -> !element.equals(eachAnagram))
                        .forEach(anagramsMatches.get(eachAnagram)::add);
            }

        }
    }

    public HashSet<String> retrievePastAnagramsWithGivenWord(String givenWord){
        return anagramsMatches.get(givenWord);
    }

    public boolean areAnagramsCheck(String leftString, String rightString){

        // Null checks
        if (StringUtils.isEmpty(leftString) || StringUtils.isEmpty(rightString)) {
            return false;
        }

        // Remove whitespace and convert to lowercase for case-insensitive comparison
        leftString = leftString.replaceAll("\\s", "").toLowerCase();
        rightString = rightString.replaceAll("\\s", "").toLowerCase();

        // If lengths are not the same, they can't be anagrams
        if (leftString.length() != rightString.length()) {
            return false;
        }

        List<Character> fistCharctersList = leftString.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        List<Character> secondCharctersList = rightString.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        Collections.sort(fistCharctersList);
        Collections.sort(secondCharctersList);


        // Compare sorted arrays
        boolean match = fistCharctersList.equals(secondCharctersList);
        return match;

    }

    public static Map<String, HashSet<String>> getAnagramsMatches() {
        return anagramsMatches;
    }

    public static void setAnagramsMatches(Map<String, HashSet<String>> anagramsMatches) {
        AnagramsService.anagramsMatches = anagramsMatches;
    }
}
