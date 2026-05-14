package com.example.aggregator.client;

import com.example.aggregator.model.Entry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AggregatorRestClient {

    private RestTemplate restTemplate;

    public AggregatorRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Entry getDefinitionFor(String word) {

        String uri = "http://localhost:9091/getWord/" + word;

        Entry result = restTemplate.getForObject(uri, Entry.class);

        return result;
    }

    /**
     * Get Words Starting With
     *
     * @param chars
     * @return
     */
    public List<Entry> getWordsStartingWith(String chars) {

        // Get URL
        String uri = "http://localhost:9091/getWordsStartingWith/" + chars;

        // Call endpoint
        ResponseEntity<Entry[]> result = restTemplate.getForEntity(uri, Entry[].class);

        // Convert body to array
        Entry[] entryArray = result.getBody();

        // Return list of Entries
        return Arrays.stream(entryArray)
                .collect(Collectors.toList());
    }

    /**
     * Get Words Ending With
     *
     * @param chars
     * @return
     */
    public List<Entry> getWordsEndingWith(String chars) {

        // Get URL
        String uri = "http://localhost:9091/getWordsEndingWith/" + chars;

        // Call endpoint
        ResponseEntity<Entry[]> result = restTemplate.getForEntity(uri, Entry[].class);

        // Convert body to array
        Entry[] entryArray = result.getBody();

        // Return list of Entries
        return Arrays.stream(entryArray)
                .collect(Collectors.toList());
    }

    public List<Entry> getWordsThatContain(String chars) {

        // Get URL
        String uri = "http://localhost:9091/getWordsThatContain/" + chars;

        // Call endpoint
        ResponseEntity<Entry[]> result = restTemplate.getForEntity(uri, Entry[].class);

        // Convert body to array
        Entry[] entryArray = result.getBody();

        // Return list of Entries
        return Arrays.stream(entryArray)
                .collect(Collectors.toList());
    }

    public List<Entry> getWordsThatContainConsecutiveLetters() {

        // Get URL
        String uri = "http://localhost:9091/getWordsThatContainConsecutiveLetters";

        // Call endpoint
        ResponseEntity<Entry[]> result = restTemplate.getForEntity(uri, Entry[].class);

        // Convert body to array
        Entry[] entryArray = result.getBody();

        // Return list of Entries
        return Arrays.stream(entryArray)
                .collect(Collectors.toList());
    }
}
