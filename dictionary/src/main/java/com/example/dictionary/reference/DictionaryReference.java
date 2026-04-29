package com.example.dictionary.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DictionaryReference {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryReference.class);

    private static Map<String, String> dictionary;

    static {

        try {
            readDictionaryFile();
        } catch (Exception e) {
            logger.error("There was a problem reading the dictionary file.");
        }
    }

    private DictionaryReference() {
        // blocking instantiation
    }

    private static void readDictionaryFile() {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // reading file
        InputStream inputStream = DictionaryReference.class.getClassLoader()
                .getResourceAsStream("dictionary.json");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String json = bufferedReader.lines()
                .collect(Collectors.joining("\n"));

        // putting data into Map
        ObjectMapper objectMapper = new ObjectMapper();
        dictionary = objectMapper.readValue(json, Map.class);

        stopWatch.stop();
        long elapsedTime = stopWatch.getTotalTimeMillis();

        // recording log
        String message = new StringBuilder().append("Dictionary created with ")
                .append(dictionary.size())
                .append(" entries in ")
                .append(elapsedTime)
                .append("ms")
                .toString();

        logger.info(message);

    }

    public static Map<String, String> getDictionary() {
        return DictionaryReference.dictionary;
    }

}
